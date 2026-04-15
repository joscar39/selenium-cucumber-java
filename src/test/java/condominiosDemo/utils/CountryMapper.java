package condominiosDemo.utils;

/**
 * Mapea los nombres de países a los códigos 'value' del HTML de ComunidadFeliz.
 */
public class CountryMapper {

    /**
     * Traduce el nombre del país al valor esperado por el atributo 'value' del select.
     */
    public static String getCode(String countryName) {
        if (countryName == null || countryName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del país no puede estar vacío.");
        }

        String name = countryName.toLowerCase().trim();

        return switch (name) {
            case "argentina"                     -> "AR";
            case "bolivia"                       -> "BO";
            case "chile"                         -> "CL";
            case "colombia"                      -> "CO";
            case "ecuador"                       -> "EC";
            case "el salvador"                   -> "SV";
            case "estados unidos", "usa", "us"   -> "US";
            case "guatemala"                     -> "GT";
            case "honduras"                      -> "HN";
            case "mexico", "méxico"              -> "MX";
            case "panama", "panamá"              -> "PA";
            case "peru", "perú"                  -> "PE";
            case "republica dominicana", "república dominicana" -> "DO";
            case "uruguay"                       -> "UY";
            case "venezuela"                     -> "VE";
            default -> throw new IllegalArgumentException("País no configurado: " + countryName);
        };
    }

    /**
     * Método Main para probar la lógica rápidamente sin abrir Selenium.
     */
    public static void main(String[] args) {
        System.out.println("--- Iniciando Pruebas de CountryMapper ---");

        // Prueba 1: Con tilde
        System.out.println("Prueba 'México': " + getCode("México")); // Debería dar MX

        // Prueba 2: Sin tilde
        System.out.println("Prueba 'Mexico': " + getCode("Mexico")); // Debería dar MX

        // Prueba 3: Mayúsculas
        System.out.println("Prueba 'CHILE': " + getCode("CHILE"));   // Debería dar CL

        // Prueba 4: Con espacios
        System.out.println("Prueba '  panamá  ': " + getCode("  panamá  ")); // Debería dar PA

        System.out.println("--- Pruebas finalizadas con éxito ---");
    }
}