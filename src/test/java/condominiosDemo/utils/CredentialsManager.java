package condominiosDemo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class CredentialsManager {


    private static String getCredential(String credential) {
        Properties props = new Properties();

        File file = new File("src/test/resources/credentials.properties");
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                props.load(fis);
            } catch (IOException e) {
                System.out.println("No se pudo leer el archivo de credenciales, se usarán variables de entorno: " + e.getMessage());
            }
        }

        if (props.getProperty(credential) != null) {
            return props.getProperty(credential);
        }

        return switch (credential) {
            case "email" -> System.getenv("EMAIL");
            case "password" -> System.getenv("PASS");
            case "bankConnection" -> System.getenv("BANK_CONNECTION");
            case "optionalPassword" -> System.getenv("OPTIONAL_PASS");
            default -> null;
        };
    }

    public static String getEmail() {
        return getCredential("email");
    }

    public static String getPassword() {
        return getCredential("password");
    }

    public static String getOptionalPassword() {
        return getCredential("optionalPassword");
    }

    public static String getBankConnectionUserPassword() {
        return getCredential("bankConnection");
    }
}
