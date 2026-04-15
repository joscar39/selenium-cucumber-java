package condominiosDemo.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UploadFile {

    // 1. CORRECCIÓN: Se agrega la 's' a 'files'
    private static final String BASE_PATH = System.getProperty("user.dir") + File.separator +
            "src" + File.separator +
            "test" + File.separator +
            "resources" + File.separator +
            "files" + File.separator +
            "imagen" + File.separator;

    // 2. CORRECCIÓN: Nombres exactos según tu imagen (Torre.jpeg, urbanismo.jpeg)
    private static final List<String> COMMUNITY_IMAGES = Arrays.asList(
            "urbanismo.jpeg",
            "Torre.jpeg",
            "apartamento.jpg",
            "condominio.jpg",
            "conjunto residencial.jpg"
    );

    private static final List<String> ADMINISTRATORS_IMAGES = Arrays.asList(
            "Administracion uno.png",
            "Administracion dos.png",
            "Administracion tres.png",
            "Administracion cuatro.png",
            "Administracion cinco.png"
    );

    public static String getRandomCommunityImagePath() {
        return getAbsolutePathFromList(COMMUNITY_IMAGES);
    }

    public static String getRandomAdministratorImagePath() {
        return getAbsolutePathFromList(ADMINISTRATORS_IMAGES);
    }

    private static String getAbsolutePathFromList(List<String> list) {
        String fileName = list.get(new Random().nextInt(list.size()));
        // Usamos el separador del sistema para mayor compatibilidad
        File file = new File(BASE_PATH + fileName);

        if (!file.exists()) {
            throw new RuntimeException("ERROR: El archivo '" + fileName + "' no existe en: " + file.getAbsolutePath());
        }

        return file.getAbsolutePath();
    }

    public static void main(String[] args) {
        System.out.println("=== VALIDACIÓN DE RUTAS CON ESTRUCTURA REAL ===");
        try {
            System.out.println("Ruta Comunidad: " + getRandomCommunityImagePath());
            System.out.println("Ruta Admin: " + getRandomAdministratorImagePath());
            System.out.println("\n✔ ¡Ahora sí funciona! Los nombres coinciden con el disco.");
        } catch (Exception e) {
            System.err.println("\n✘ " + e.getMessage());
        }
    }
}
