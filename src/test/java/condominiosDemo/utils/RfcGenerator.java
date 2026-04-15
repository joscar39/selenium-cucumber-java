package condominiosDemo.utils;

import java.time.LocalDate;
import java.util.Random;

public class RfcGenerator {

    private static final Random random = new Random();

    /**
     * Genera un RFC válido para persona física.
     * Estructura: [4 letras][6 números][3 caracteres homoclave]
     */
    public static String generateRandomRfc() {
        // 1. Generar las 4 letras iniciales (Simulando iniciales de nombre/apellidos)
        String letters = generateRandomLetters();

        // 2. Generar fecha de nacimiento (YYMMDD)
        // Generamos una fecha aleatoria para alguien de entre 18 y 60 años
        int yearsToSubtract = 18 + random.nextInt(42);
        LocalDate birthDate = LocalDate.now().minusYears(yearsToSubtract)
                .minusDays(random.nextInt(365));

        String datePart = String.format("%02d%02d%02d",
                birthDate.getYear() % 100,
                birthDate.getMonthValue(),
                birthDate.getDayOfMonth());

        // 3. Generar Homoclave (3 caracteres: letras o números)
        String homoclave = generateAlphanumeric();

        return (letters + datePart + homoclave).toUpperCase();
    }

    private static String generateRandomLetters() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            // CORRECCIÓN: alphabet.length() en lugar de .size()
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return sb.toString();
    }

    private static String generateAlphanumeric() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            // CORRECCIÓN: chars.length() en lugar de .size()
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    // Método main para probar internamente
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE GENERADOR DE RFC (MÉXICO) ===");
        for (int i = 0; i < 5; i++) {
            System.out.println("RFC Generado " + (i + 1) + ": " + generateRandomRfc());
        }
        System.out.println("-------------------------------------------");
    }
}
