package condominiosDemo.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class CommunityGenerator {

    private static final Random random = new Random();

    private static final List<String> NAMES = Arrays.asList(
            "Los Olivos", "Vista Hermosa", "Santa María", "Don Ricardo", "El Mirador",
            "Res. Oriente", "Altos Mirandinos", "San Francisco", "La Arboleda", "Bella Vista",
            "Pinares", "Costa Azul", "Los Fundadores", "San Agustín", "Portal Sol",
            "Villas Prado", "Monte Verde", "Los Castaños", "Santa Elena", "Río Claro",
            "Valle Nevado", "Sierra Linda", "Las Acacias", "Camino Real", "Altos Bosque",
            "Jardines Sur", "Puerta Hierro", "Lomas Sol", "Brisa Marina", "San Sebastián",
            "Mirador Lago", "Hacienda Real", "Praderas Chile", "Los Alerces", "Paseo Ribera",
            "San Valentín", "Cerro Grande", "Quinta Real", "Los Robles", "Montaña Azul",
            "Jardín Andes", "Nueva Esperanza", "Altamira", "Palmar Lilas", "San Jerónimo",
            "Piedra Blanca", "Los Manantiales", "Miraflores", "Bosque Nativo", "Valles Norte",
            "San Andrés", "Tierra Santa", "Agua Dulce", "Cumbres Maipú", "Portal Reina",
            "Rincón Valle", "San Rodrigo", "Los Copihues", "Aurora Alba", "Vía Seda"
    );


    /**
     * Extrae el nombre del usuario desde el email en credentials.properties
     * Ejemplo: joscar.sosa@condominiosDemo.cl -> Joscar Sosa
     */
    private static String getUserFromProperties() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/credentials.properties")) {
            props.load(fis);
            String email = props.getProperty("email");
            if (email != null && email.contains("@")) {
                String userPart = email.split("@")[0]; // Toma lo que está antes del @
                // Reemplazamos puntos o guiones por espacios y ponemos bonito
                return userPart.replace(".", " ").replace("-", " ");
            }
        } catch (IOException e) {
            System.err.println("No se pudo leer el email de properties: " + e.getMessage());
        }
        return "Tester"; // Valor por defecto si algo falla
    }

    /**
     * Genera un nombre de comunidad basado en la región y el tipo de comunidad.
     *
     * @param region        "Chile" o "Mexico"
     * @param typeCommunity "CC" (Con Control) o "SC" (Sin Control)
     * @return Nombre concatenado según reglas de negocio.
     */
    public static String communityNameGenerator(String region, String typeCommunity) {
        String userPrefix = getUserFromProperties();
        String name = NAMES.get(random.nextInt(NAMES.size()));

        // 1. Obtener y formatear la fecha actual
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        // 2. Determinar el sufijo de región y tipo
        String countryCode = region.equalsIgnoreCase("Chile") ? "CL" : "MX";

        // Usamos un switch moderno para mayor claridad en el tipo de comunidad

        return switch (region.toLowerCase()) {
            case "chile" -> {
                String type = typeCommunity.equalsIgnoreCase("CC") ? "CC" : "SC";
                yield String.format("%s %s %s %s Selenium %s", type, userPrefix, name, countryCode, datePart);
            }
            case "mexico" ->
                // Para México siempre es SC según tu lógica actual
                    String.format("SC %s %s MX Selenium %s", userPrefix, name, datePart);

            default -> String.format("%s %s %s", userPrefix, name, datePart);
        };
    }

    /**
     * Genera el correo electrónico a partir del nombre completo de la comunidad.
     * Ejemplo entrada: "CC joscar sosa Don Ricardo CL Selenium 10-02-2026"
     * Resultado esperado: admin.cclosolivos99@yopmail.com
     */
    public static String communityEmailGenerator(String communityName) {
        // 1. Limpieza inicial: pasar a minúsculas y dividir por espacios
        String[] parts = communityName.toLowerCase().split("\\s+");

        if (parts.length < 1) return "admin.test@yopmail.com";

        // El tipo siempre es el primero: "cc" o "sc"
        String type = parts[0];

        // 2. Extraer el nombre real de la comunidad
        // Buscamos las palabras que están después del indice 2 y antes de "cl" o "selenium"
        StringBuilder communityPart = new StringBuilder();

        // Empezamos desde el índice 3 para saltar tipo de comunidad y nombre del QA ejecutor
        for (int i = 3; i < parts.length; i++) {
            // Si llegamos a palabras que son sufijos de control, paramos
            if (parts[i].equals("cl") || parts[i].equals("selenium") || parts[i].matches(".*\\d{2}-\\d{2}-\\d{4}.*")) {
                break;
            }
            communityPart.append(parts[i]);
        }

        // 3. Limpieza final de caracteres especiales (por si acaso)
        String cleanName = communityPart.toString().replaceAll("[^a-z0-9]", "");

        // Si por alguna razón quedó vacío, usamos un fallback
        if (cleanName.isEmpty()) cleanName = "comunidad";

        int randomNum = random.nextInt(99);

        // Resultado: admin.cc.donricardo88@yopmail.com
        return String.format("admin.%s.%s%d@yopmail.com", type, cleanName, randomNum);
    }

    // Metodo main para validar los metodos
    public static void main(String[] args) {
        // Simulamos el nombre largo generado
        String nombreGenerado = communityNameGenerator("chile", "sc");

        // El metodo se encarga de extraer lo necesario
        String correo = communityEmailGenerator(nombreGenerado);

        System.out.println("Nombre Original: " + nombreGenerado);
        System.out.println("Correo Resultante: " + correo);
    }
}