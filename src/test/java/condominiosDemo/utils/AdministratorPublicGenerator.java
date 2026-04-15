package condominiosDemo.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AdministratorPublicGenerator {

    private static final Random random = new Random();

    /**
     * Genera un nombre de administrador público o entidad legal aleatoria.
     * Ejemplo: "Consorcio Bravo", "Gerente Juan Rodriguez", "Sociedad Alborada".
     */
    public static String publicNameGenerator() {
        // Nombres de personas para acompañar cargos
        List<String> individualNames = Arrays.asList(
                "Juan", "Maria", "Carlos", "Ana", "Luis", "Laura", "Pedro", "Sofia",
                "Diego", "Elena", "Javier", "Carmen", "Daniel", "Paula"
        );

        // Nombres de empresas o conceptos abstractos
        List<String> entityNames = Arrays.asList(
                "Alborada", "Central", "Norte", "Sur", "Pacifico", "Andina", "ProGest",
                "Urbania", "Siglo XXI", "Horizonte", "Patrimonial", "Integral", "Bravo"
        );

        // Sustantivos de organización (Tipo de Buffet o Sociedad)
        List<String> organizations = Arrays.asList(
                "Buffete", "Consorcio", "Sociedad", "Administraciones", "Asociados",
                "Organizacion", "Grupo", "Consultores", "Estudio"
        );

        // Keywords de cargos o tipo de administración
        List<String> keywords = Arrays.asList(
                "Administrador", "Director", "Gerente", "Compañia", "Sindicato", "Gestor"
        );

        String result = "";

        // Decidimos el formato aleatoriamente entre 3 opciones
        int option = random.nextInt(3);

        switch (option) {
            case 0: // Formato: Organización + Nombre de Entidad (Ej: Consorcio Alborada)
                result = organizations.get(random.nextInt(organizations.size())) + " " +
                        entityNames.get(random.nextInt(entityNames.size()));
                break;
            case 1: // Formato: Cargo + Nombre Individual (Ej: Gerente Juan)
                result = keywords.get(random.nextInt(keywords.size())) + " " +
                        individualNames.get(random.nextInt(individualNames.size()));
                break;
            case 2: // Formato: Nombre Entidad + Keywords (Ej: Alborada Compañia)
                result = entityNames.get(random.nextInt(entityNames.size())) + " " +
                        keywords.get(random.nextInt(keywords.size()));
                break;
        }

        return result;
    }

    /**
     * Genera un correo corporativo basado en el nombre de la entidad.
     */
    public static String corporateEmailGenerator(String publicName) {
        List<String> domains = Arrays.asList("admin.com", "gestioncf.cl", "edificio.mx", "comunidad.com", "yopmail.com");

        // Limpiamos el nombre: quitamos tildes/espacios y pasamos a minúsculas
        String cleanName = publicName.toLowerCase()
                .replace(" ", "")
                .replace("ñ", "n")
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u");

        String num = String.format("%02d", random.nextInt(99) + 1);
        String domain = domains.get(random.nextInt(domains.size()));

        return cleanName + num + "@" + domain;
    }

    // Método para ejecución de prueba
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE GENERADOR DE ADMINISTRADORES PUBLICOS ===");
        for (int i = 0; i < 5; i++) {
            String nombre = publicNameGenerator();
            String correo = corporateEmailGenerator(nombre);
            System.out.println("Entidad " + (i + 1) + ":");
            System.out.println("   Nombre Público: " + nombre);
            System.out.println("   Email Corp:     " + correo);
            System.out.println("------------------------------------------------------");
        }
    }
}
