package condominiosDemo.utils;

import java.util.List;
import java.util.Random;

/**
 * Clase encargada de generar datos bancarios aleatorios para los tests.
 */
public class BankDataGenerator {

    private static final Random RANDOM = new Random();

    private static final List<String> BANKS = List.of(
            "Banco Santander",
            "Banco de Chile",
            "Banco Estado",
            "BCI",
            "Scotiabank",
            "Itaú",
            "Banco Falabella",
            "Banco Provincial",
            "Banco Banesco",
            "Banco Nacional de Credito",
            "Banco BBVA"
    );

    public static String getRandomBankName() {
        return BANKS.get(RANDOM.nextInt(BANKS.size()));
    }

    public static String generateAccountNumber(int length) {
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < length; i++) {
            number.append(RANDOM.nextInt(10));
        }
        return number.toString();
    }

    public static String getFullBankDetails() {
        return String.format("%s - N° %s", getRandomBankName(), generateAccountNumber(12));
    }

    // ##########################################################################
    // #                     MÉTODO MAIN PARA PRUEBAS UNITARIAS                 #
    // ##########################################################################
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE GENERADOR DE DATOS BANCARIOS ===");
        System.out.println("Generando 5 ejemplos aleatorios...\n");

        for (int i = 1; i <= 5; i++) {
            System.out.println("Ejecución N° " + i);
            System.out.println("Banco: " + getRandomBankName());
            System.out.println("Cuenta (10 dígitos): " + generateAccountNumber(10));
            System.out.println("Detalle completo: " + getFullBankDetails());
            System.out.println("----------------------------------------------");
        }
    }
}
