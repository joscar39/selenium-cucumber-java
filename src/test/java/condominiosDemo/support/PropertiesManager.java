package condominiosDemo.support;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {

    private static String getProperty(String property) {
        try (FileInputStream fis = new FileInputStream("src/test/resources/application.properties")) {
            Properties props = new Properties();
            props.load(fis);
            return props.getProperty(property);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar la propiedad solicitada", e);
        }
    }

    public static String get(String property) {
        return getProperty(property);
    }
}
