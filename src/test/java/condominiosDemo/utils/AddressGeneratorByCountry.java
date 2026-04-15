package condominiosDemo.utils;

import java.util.*;

public class AddressGeneratorByCountry {

    private static final Random random = new Random();

    // Listas de direcciones por país
    private static final List<String> CHILE_ADDRESSES = Arrays.asList(
//            "Santiago Metropolitan Region, Chile",
//            "Bellavista, Providencia, Chile",
//            "Viña del Mar, Chile",
//            "Cañete, Chile",
//            "Lago Ranco, Chile"
            "Santiago Metropolitan Region Chile",
            "Bellavista Providencia Chile",
            "Viña del Mar Chile",
            "Cañete Chile",
            "Lago Ranco Chile"
    );

    private static final List<String> MEXICO_ADDRESSES = Arrays.asList(
//            "Ciudad de México, CDMX, Mexico",
//            "Guadalajara, Jalisco, Mexico",
//            "Oaxaca, Mexico",
//            "Cuautitlán Izcalli, State of Mexico, Mexico",
//            "Nuevo Leon, Mexico"
            "Ciudad de México CDMX Mexico",
//            "Guadalajara Jalisco Mexico",
//            "Oaxaca Mexico",
//            "Cuautitlán Izcalli State of Mexico Mexico",
//            "Nuevo Leon Mexico"
            "San Luis Potosi Mexico"
    );

    private static final Map<String, String[]> PREFIJOS_CP_MEXICO = new HashMap<>() {{
        put("CDMX", new String[]{"01", "03", "06", "11"});
        put("Jalisco", new String[]{"44", "45"});
        put("Nuevo Leon", new String[]{"64", "66"});
        put("State of Mexico", new String[]{"50", "55"}); // Corregido para que coincida con tu lista
        put("Oaxaca", new String[]{"68", "71"}); // Agregado porque está en tu lista
    }};

    /**
     * Genera una dirección aleatoria según la región/país proporcionado.
     * @param region Nombre del país (Chile o Mexico).
     * @return Una dirección aleatoria de la lista correspondiente.
     */
    public static String generateAddress(String region) {
        String address;

        if (region.equalsIgnoreCase("Chile")) {
            address = CHILE_ADDRESSES.get(random.nextInt(CHILE_ADDRESSES.size()));
        }
        else if (region.equalsIgnoreCase("Mexico") || region.equalsIgnoreCase("México")) {
            address = MEXICO_ADDRESSES.get(random.nextInt(MEXICO_ADDRESSES.size()));
        }
        else {
            // Caso por defecto si no es ninguno de los dos
            address = "ERROR: Region no soportada (" +  region + ")";
        }

        return address;
    }

    /**
     * Crea un Código Postal de México basado en el estado que encuentre en la dirección.
     * @param addressCity La dirección que salió de tu función previa.
     * @return El código postal de 5 dígitos.
     */
    public static String createZipCodeMexico(String addressCity) {
        // Si la dirección es de error o de Chile, no procesamos CP
        if (addressCity.contains("ERROR") || addressCity.contains("Chile")) {
            return "";
        }

        String prefijo = "01"; // Default para CDMX o si no hay match claro

        for (String estado : PREFIJOS_CP_MEXICO.keySet()) {
            // Buscamos la palabra clave dentro de tu dirección
            if (addressCity.contains(estado)) {
                String[] opciones = PREFIJOS_CP_MEXICO.get(estado);
                prefijo = opciones[random.nextInt(opciones.length)];
                break;
            }
        }

        return prefijo + String.format("%03d", random.nextInt(1000));
    }

    /**
     * Metodo de prueba (Main) mejorado
     */
    public static void main(String[] args) {

        System.out.println("=== INICIO DE PRUEBA DE DIRECCIONES REALES ===");

        // Solo probaremos las regiones que tus listas soportan para evitar errores
        String[] regionesValidas = {"Chile", "Mexico", "peru"};

        for (String reg : regionesValidas) {
            System.out.println("\nProbando: " + reg);

            // Generamos 3 direcciones de ejemplo por cada país
            for (int i = 0; i < 3; i++) {
                String address = generateAddress(reg);

                if (reg.equalsIgnoreCase("Mexico")) {
                    String cp = createZipCodeMexico(address);
                    System.out.println("Resultado México: " + address + ", CP " + cp);
                } else {
                    System.out.println("Resultado "+ reg +": " + address);
                }
            }
        }
    }
}
