package condominiosDemo.utils;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Servicio para la gestión de Google Sheets y Google Drive.
 * Permite localizar archivos por nombre y realizar inserciones inteligentes en filas vacías.
 */
public class GoogleSheetsService {

    // Ruta al archivo JSON de credenciales descargado desde Google Cloud Console
    private static final String CREDENTIALS_PATH = "src/test/resources/automationcf-credential-db.json";

    // Identificador de la aplicación para las peticiones a la API de Google
    private static final String APP_NAME = "HappyCommunity-Automation";

    /**
     * Método para leer la propiedad. Si falla, detiene la ejecución.
     */
    private static String loadDatabaseNameFromProperties() {
        Properties props = new Properties();
        String path = "src/test/resources/application.properties";

        try (FileInputStream fis = new FileInputStream(path)) {
            props.load(fis);
            String dbName = props.getProperty("google.sheets.database.name");

            // VALIDACIÓN: Si la llave no existe o está vacía en el archivo
            if (dbName == null || dbName.trim().isEmpty()) {
                throw new RuntimeException("ERROR CRÍTICO: La propiedad 'google.sheets.database.name' no está definida en " + path);
            }

            return dbName;

        } catch (IOException e) {
            // ERROR: Si el archivo físico no existe en la ruta especificada
            throw new RuntimeException("ERROR CRÍTICO: No se pudo localizar el archivo de configuración en: " + path
                    + ". La ejecución se detendrá porque el nombre de la BD es obligatorio.");
        }
    }

    /**
     * Utiliza Google Drive API para buscar el ID de un archivo Spreadsheet usando su nombre.
     * Esto emula el comportamiento de 'client.open(fileName)' en Python.
     * @param fileName Nombre exacto del archivo en Google Drive.
     * @return El ID único del archivo (SpreadsheetId).
     */
    public static String getFileIdByName(String fileName) throws Exception {
        // Configuración de credenciales solo lectura para Drive
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(CREDENTIALS_PATH))
                .createScoped(Collections.singleton("https://www.googleapis.com/auth/drive.readonly"));

        // Inicialización del servicio de Drive
        Drive driveService = new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials))
                .setApplicationName(APP_NAME)
                .build();

        // Consulta: Busca por nombre, tipo spreadsheet y que no esté en la papelera
        String query = "name = '" + fileName + "' and mimeType = 'application/vnd.google-apps.spreadsheet' and trashed = false";
        FileList result = driveService.files().list().setQ(query).setFields("files(id)").execute();

        if (result.getFiles().isEmpty()) {
            throw new IOException("Archivo no encontrado en Google Drive: " + fileName);
        }

        return result.getFiles().get(0).getId();
    }

    /**
     * Guarda datos en la primera fila totalmente vacía después de la última fila con datos.
     * Mapea los valores automáticamente buscando el nombre de la columna en el encabezado (fila 1).
     * @param sheetName Nombre de la pestaña (Hoja) donde se guardarán los datos.
     * @param dataToSave Mapa con <NombreColumna, Valor>, ej: <"NameCommunity", "Comunidad Pedro Perez">.
     */
    public static void saveDataInLastEmptyRow(String sheetName, Map<String, String> dataToSave) throws Exception {

        // 1. Invocamos directamente el nombre desde el archivo de configuración
        String fileName = loadDatabaseNameFromProperties();

        // 2. Validación de seguridad solicitada: Detener ejecución si no hay nombre
        if (fileName.trim().isEmpty()) {
            throw new RuntimeException("ERROR CRÍTICO: El nombre del archivo de la BD no existe o no se puede localizar en el archivo de propiedades.");
        }

        // Localiza el ID del archivo por su nombre
        String spreadsheetId = getFileIdByName(fileName);
        Sheets service = getSheetsService();

        // 1. Obtenemos todos los datos actuales de la hoja para determinar el final real
        // Se lee un rango amplio (A:ZZ) para asegurar que detectamos datos en cualquier columna
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, sheetName + "!A:ZZ").execute();
        List<List<Object>> allValues = response.getValues();

        if (allValues == null || allValues.isEmpty()) {
            throw new IOException("La hoja '" + sheetName + "' está vacía o no tiene fila de encabezados.");
        }

        // La primera fila (índice 0) contiene los nombres de las columnas
        List<Object> headers = allValues.get(0);

        // Determinamos la siguiente fila vacía.
        // Si hay 8 filas con datos, allValues.size() es 8, por lo tanto escribiremos en la 9.
        int nextRow = allValues.size() + 1;

        // 2. Preparamos la nueva fila con el tamaño exacto de los encabezados
        List<Object> rowData = new ArrayList<>(Collections.nCopies(headers.size(), ""));

        // 3. Mapeo inteligente: Colocamos cada dato en el índice de columna que le corresponde según el Header
        for (Map.Entry<String, String> entry : dataToSave.entrySet()) {
            int colIndex = headers.indexOf(entry.getKey());
            if (colIndex != -1) {
                rowData.set(colIndex, entry.getValue());
            } else {
                System.err.println("Advertencia: El encabezado '" + entry.getKey() + "' no se encontró en el archivo.");
            }
        }

        // 4. Ejecución de la inserción en la fila calculada
        ValueRange body = new ValueRange().setValues(Collections.singletonList(rowData));
        service.spreadsheets().values()
                .update(spreadsheetId, sheetName + "!A" + nextRow, body)
                .setValueInputOption("USER_ENTERED")
                .execute();

        System.out.println("Inserción exitosa: Registro guardado en la fila " + nextRow + " del archivo '" + fileName + "'.");
    }

    /**
     * Helper privado para inicializar el servicio de Google Sheets con los scopes necesarios.
     */
    private static Sheets getSheetsService() throws Exception {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(CREDENTIALS_PATH))
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));

        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials))
                .setApplicationName(APP_NAME)
                .build();
    }

    /**
     * Extrae el nombre de usuario del QA desde el archivo de credenciales.
     * <p>
     * El metodo lee la propiedad 'email' de 'credentials.properties',
     * realiza un split por el símbolo '@' y retorna la primera parte.
     * </p>
     * * @return String con el nombre del QA (ej. "joscar.sosa") o "Unknown.QA" si hay error.
     */
    private static String getQaNameFromProperties() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/credentials.properties")) {
            props.load(fis);
            String email = props.getProperty("email");
            if (email != null && email.contains("@")) {
                return email.split("@")[0];
            }
        } catch (IOException e) {
            System.err.println("Advertencia: No se pudo leer el nombre del QA desde properties.");
        }
        return "Unknown.QA";
    }

    /**
     * Selecciona una fila aleatoria que coincida con el estado proporcionado y la bloquea
     * para evitar que otros integrantes del equipo usen la misma data simultáneamente.
     * <p>
     * El proceso incluye:
     * 1. Leer toda la hoja.
     * 2. Filtrar filas por el valor de 'StatusCommunity'.
     * 3. Seleccionar una fila al azar.
     * 4. Actualizar la celda de estatus en Google Sheets a "IN_PROGRESS - [NombreQA]".
     * </p>
     * * @param sheetName   Nombre de la pestaña/hoja en Google Sheets.
     * @param statusValue El estado que se desea buscar (ej. "register pending").
     * @return Map con los datos originales de la fila seleccionada o null si no hay coincidencias.
     * @throws Exception Si ocurre un error en la comunicación con la API de Google.
     */
    public static Map<String, String> getAndLockRowByStatus(String sheetName, String statusValue) throws Exception {
        String fileName = loadDatabaseNameFromProperties();
        String spreadsheetId = getFileIdByName(fileName);
        Sheets service = getSheetsService();

        // Obtener el rango completo de la hoja
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, sheetName + "!A:ZZ").execute();
        List<List<Object>> allValues = response.getValues();

        if (allValues == null || allValues.size() < 2) return null;

        List<Object> headers = allValues.get(0);
        int statusColIndex = headers.indexOf("StatusCommunity");

        if (statusColIndex == -1) {
            throw new RuntimeException("La columna 'StatusCommunity' no existe en la hoja.");
        }

        // Identificar índices de filas que coinciden con el estatus
        List<Integer> matchingIndices = new ArrayList<>();
        for (int i = 1; i < allValues.size(); i++) {
            List<Object> row = allValues.get(i);
            if (row.size() > statusColIndex && row.get(statusColIndex).toString().equalsIgnoreCase(statusValue)) {
                matchingIndices.add(i);
            }
        }

        if (matchingIndices.isEmpty()) return null;

        // Selección aleatoria
        int randomIndex = matchingIndices.get(new Random().nextInt(matchingIndices.size()));
        List<Object> selectedRow = allValues.get(randomIndex);

        // Preparar el nuevo estado de bloqueo
        String qaName = getQaNameFromProperties();
        String newStatus = "IN_PROGRESS - " + qaName;

        // Calcular dirección de celda (Letra + Número de fila)
        String columnLetter = getColumnLetter(statusColIndex);
        String cellAddress = sheetName + "!" + columnLetter + (randomIndex + 1);

        // Actualización remota en Google Sheets
        ValueRange body = new ValueRange().setValues(Collections.singletonList(Collections.singletonList(newStatus)));
        service.spreadsheets().values()
                .update(spreadsheetId, cellAddress, body)
                .setValueInputOption("USER_ENTERED")
                .execute();

        System.out.println("Data bloqueada para uso de: " + qaName + " en celda " + cellAddress);

        // Convertir la fila seleccionada a un diccionario (Map)
        Map<String, String> resultMap = new HashMap<>();
        for (int i = 0; i < headers.size(); i++) {
            String value = (i < selectedRow.size()) ? selectedRow.get(i).toString() : "";
            resultMap.put(headers.get(i).toString(), value);
        }
        return resultMap;
    }

    /**
     * Convierte un índice de columna numérico (0-indexed) a su representación en letras de Excel.
     * <p>
     * Ejemplo: 0 -> A, 1 -> B, 26 -> AA.
     * </p>
     * * @param column Índice de la columna.
     * @return String con la letra correspondiente de la columna.
     */
    private static String getColumnLetter(int column) {
        StringBuilder columnLetter = new StringBuilder();
        while (column >= 0) {
            columnLetter.insert(0, (char) (column % 26 + 'A'));
            column = column / 26 - 1;
        }
        return columnLetter.toString();
    }

    /**
     * Busca un registro existente por Email e IdCommunity. Si lo encuentra, elimina la fila
     * antigua para evitar duplicados y guarda la nueva versión de los datos al final de la hoja.
     * * @param sheetName  Nombre de la pestaña/hoja donde se actualizarán los datos.
     * @param dataToSave Mapa con el nuevo set de datos a persistir.
     * @throws Exception Si falla la búsqueda, eliminación o inserción en la API.
     */
    public static void updateOrSaveData(String sheetName, Map<String, String> dataToSave) throws Exception {
        String fileName = loadDatabaseNameFromProperties();
        String spreadsheetId = getFileIdByName(fileName);
        Sheets service = getSheetsService();

        ValueRange response = service.spreadsheets().values().get(spreadsheetId, sheetName + "!A:ZZ").execute();
        List<List<Object>> allValues = response.getValues();
        List<Object> headers = allValues.get(0);

        // Localizar índices de columnas clave para la búsqueda de duplicados
        int emailIdx = headers.indexOf("EmailCommunity");
        int idCommIdx = headers.indexOf("IdCommunity");

        String targetEmail = dataToSave.get("EmailCommunity");
        String targetId = dataToSave.get("IdCommunity");

        Integer rowToDelete = null;

        // Buscar coincidencia exacta de Email + ID
        for (int i = 1; i < allValues.size(); i++) {
            List<Object> row = allValues.get(i);
            if (row.size() > Math.max(emailIdx, idCommIdx)) {
                if (row.get(emailIdx).toString().equals(targetEmail) &&
                        row.get(idCommIdx).toString().equals(targetId)) {
                    rowToDelete = i;
                    break;
                }
            }
        }

        // Proceder con la eliminación si se encontró un registro previo
        if (rowToDelete != null) {
            Spreadsheet spreadsheet = service.spreadsheets().get(spreadsheetId).execute();

            int sheetIdInt = spreadsheet.getSheets().stream()
                    .filter(s -> s.getProperties().getTitle().equals(sheetName))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No se encontró la pestaña '" + sheetName + "' en el archivo de Google Sheets."))
                    .getProperties().getSheetId();

            Request request = new Request().setDeleteDimension(new DeleteDimensionRequest()
                    .setRange(new DimensionRange()
                            .setSheetId(sheetIdInt)
                            .setDimension("ROWS")
                            .setStartIndex(rowToDelete)
                            .setEndIndex(rowToDelete + 1)));

            BatchUpdateSpreadsheetRequest batchRequest = new BatchUpdateSpreadsheetRequest()
                    .setRequests(Collections.singletonList(request));
            service.spreadsheets().batchUpdate(spreadsheetId, batchRequest).execute();

            System.out.println("Registro antiguo (Duplicado) eliminado. Procediendo a guardar actualización.");
        }

        // Guardar los nuevos datos al final de la lista
        saveDataInLastEmptyRow(sheetName, dataToSave);
    }

    /**
     * Metodo de prueba para validación integral del servicio.
     * Simula el flujo completo: Crear -> Bloquear (Lock) -> Actualizar (Update).
     */
    public static void main(String[] args) {
        String sheetName = "ComunidadesMX";
        String testEmail = "test.qa@comunidadfeliz.cl";
        String testCommunityId = "ID-999-TEST";

        try {
            System.out.println("--- INICIANDO VALIDACIÓN DE GOOGLE SHEETS SERVICE ---");

            // PASO 1: Crear un registro de prueba inicial
            System.out.println("\n1. Insertando registro inicial de prueba...");
            Map<String, String> dataInicial = new HashMap<>();
            dataInicial.put("NameCommunity", "Comunidad de Prueba QA");
            dataInicial.put("StatusCommunity", "register pending properties");
            dataInicial.put("EmailCommunity", testEmail);
            dataInicial.put("IdCommunity", testCommunityId);
            saveDataInLastEmptyRow(sheetName, dataInicial);

            // PASO 2: Probar el bloqueo (Get and Lock)
            System.out.println("\n2. Intentando bloquear un registro con estatus 'register pending properties'...");
            Map<String, String> dataBloqueada = getAndLockRowByStatus(sheetName, "register pending properties");

            if (dataBloqueada != null) {
                System.out.println("   Dato bloqueado con éxito.");
                System.out.println("   Nombre en Map: " + dataBloqueada.get("NameCommunity"));

                // PASO 3: Probar la actualización (Eliminar viejo y guardar nuevo)
                System.out.println("\n3. Simulando actualización de datos (Cambio de estatus a 'Completed')...");
                dataBloqueada.put("StatusCommunity", "Completed");
                dataBloqueada.put("NameCommunity", "Comunidad de Prueba QA - PROCESADA");

                // Este método debe borrar la fila que dice "IN_PROGRESS" e insertar la nueva al final
                updateOrSaveData(sheetName, dataBloqueada);

                System.out.println("\n--- VALIDACIÓN FINALIZADA CON ÉXITO ---");
            } else {
                System.out.println("\n[!] No se encontraron registros para bloquear. Revisa el estatus en la hoja.");
            }

        } catch (Exception e) {
            System.err.println("\n[X] Error durante la ejecución de las pruebas unitarias:");
            System.err.println("Causa: " + e.getMessage());
            e.printStackTrace();
        }
    }
}