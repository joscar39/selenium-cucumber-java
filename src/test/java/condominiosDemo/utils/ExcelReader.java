package condominiosDemo.utils;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {


    /**
     * Lee una columna específica y devuelve una cantidad limitada de registros.
     * @param fileName Nombre del archivo en src/test/resources/data/
     * @param columnName Nombre de la cabecera a buscar.
     * @param limit Cantidad de filas a leer (después de la cabecera).
     * @return Lista de Strings con los datos encontrados.
     */
    public List<String> readColumnFile(String fileName, String columnName, int limit) {
        List<String> datos = new ArrayList<>();
        // Ruta para acceder a los archivos
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator +
                "test" + File.separator + "resources" + File.separator + "files" + File.separator + File.separator + "documents" + File.separator + fileName;

        try (FileInputStream fis = new FileInputStream(path);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            int columnIndex = -1;

            // 1. Buscamos la columna dinámicamente
            for (Cell cell : headerRow) {
                if (cell.getStringCellValue().trim().equalsIgnoreCase(columnName)) {
                    columnIndex = cell.getColumnIndex();
                    break;
                }
            }

            if (columnIndex == -1) {
                throw new RuntimeException("La columna '" + columnName + "' no existe en el Excel.");
            }

            // 2. Leemos con límite dinámico
            DataFormatter formatter = new DataFormatter();
            int totalFilas = sheet.getLastRowNum();
            // Determinamos hasta dónde leer: el límite pedido o el total de filas disponibles
            int filasALeer = Math.min(limit, totalFilas);

            for (int i = 1; i <= filasALeer; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Cell cell = row.getCell(columnIndex);
                    String val = formatter.formatCellValue(cell).trim();
                    if (!val.isEmpty()) {
                        datos.add(val);
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error procesando Excel: " + e.getMessage());
        }
        return datos;
    }

    public static void main(String[] args) {
        ExcelReader reader = new ExcelReader();

        // --- PRUEBA DINÁMICA ---
        String archivo = "Plantilla Propiedades Comunidad CC.xlsx";
        String columnaABuscar = "Unidad"; // Puedes cambiarlo a "RUT", "Nombre", etc.
        int cantidadDeseada = 5;

        System.out.println("=== EJECUTANDO LECTURA DINÁMICA ===");
        try {
            List<String> resultados = reader.readColumnFile(archivo, columnaABuscar, cantidadDeseada);

            System.out.println("Columna consultada: [" + columnaABuscar + "]");
            System.out.println("Registros recuperados: " + resultados.size());
            System.out.println("-------------------------------------------");

            resultados.forEach(dato -> System.out.println("-> " + dato));

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }
}