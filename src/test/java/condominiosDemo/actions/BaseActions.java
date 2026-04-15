package condominiosDemo.actions;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.support.ui.Select;
import java.util.stream.Collectors;
import java.io.File;

/**
 * Clase base que contiene todas las acciones genéricas de Selenium.
 * Utiliza las excepciones nativas de WebDriver para permitir un manejo flexible en niveles superiores.
 */
public class BaseActions {

    protected WebDriver driver;
    protected Actions action;

    public BaseActions(WebDriver driver) {
        this.driver = driver;
        this.action = new Actions(driver);
    }

    // ########################## CONFIGURATION OF BROWSER AND SET VALUES ###############################

    /**
     * Funcion que permite hacer una espera obligatoria por un intervalo de tiempo.
     * @param timeout Segundos totales de espera.
     */
    public static void timeWait(double timeout) {
        try {
            Thread.sleep((long) (timeout * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Metodo para Obtener el title de una pagina web.
     * @return Retorna el valor del title.
     * @throws RuntimeException Si ocurre un error desconocido al obtener el title.
     */
    public String getTitle() {
        try {
            String val = driver.getTitle();
            System.out.println("Se encontro el title: " + val);
            assert val != null;
            Allure.addAttachment("Obtencion de title", val);
            return val;
        } catch (Exception e) {
            String message = "Error desconocido al obtener el title de la pagina web\nLog: " + e.getMessage();
            throw new RuntimeException(message, e);
        }
    }

    /**
     * Metodo que permite obtener la URL actual del navegador y retornarlo.
     * @return Valor obtenido en la URL actual del navegador manejado por selenium.
     * @throws RuntimeException Si ocurre un error al consultar la URL.
     */
    public String getCurrentUrl() {
        try {
            return driver.getCurrentUrl();
        } catch (Exception e) {
            String message = "Error desconocido al obtener la URL actual del navegador\nLog: " + e.getMessage();
            throw new RuntimeException(message, e);
        }
    }

    /**
     * Metodo que permite navegar a una URL especifica y espera a que la pagina cargue.
     * @param url Direccion web a la que se desea navegar.
     * @param timeout Segundos de espera para asegurar que el navegador responda.
     * @throws RuntimeException Si la URL es invalida o el navegador no responde.
     */
    public void navigateToUrl(String url, int timeout) {
        try {
            // Configuramos el tiempo de espera para la carga de la pagina
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeout));

            // Navegacion
            driver.get(url);

            // Log en consola y Allure
            System.out.println("Navegacion exitosa a: " + url);
            Allure.addAttachment("Navegacion a URL", "Se ha cargado correctamente la URL: " + url);

        } catch (TimeoutException e) {
            String message = "La pagina tardo demasiado en cargar (Timeout):\nURL: " + url + "\nLog: " + e.getMessage();
            throw new RuntimeException(message, e);
        } catch (WebDriverException e) {
            String message = "Error de WebDriver al intentar navegar:\nURL: " + url + "\nLog: " + e.getMessage();
            throw new RuntimeException(message, e);
        } catch (Exception e) {
            String message = "Error desconocido al intentar navegar a la URL:\nURL: " + url + "\nLog: " + e.getMessage();
            throw new RuntimeException(message, e);
        }
    }

    // ##################################### SEARCH AND SEND ############################################

    /**
     * Metodo que permite enviar texto sobre un input a traves de localizador por selectores.
     * @param locator Objeto By que identifica el elemento.
     * @param text Texto que se enviara al input.
     * @param timeout Segundos que tardara el metodo en esperar que el input este presente.
     * @throws RuntimeException Si ocurre un Timeout, NoSuchElement o problemas de interaccion.
     */
    public void sendTextBySelector(By locator, Object text, int timeout) {
        try {
            // Convertimos el objeto a String usando .toString()
            String textToSend = String.valueOf(text);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            ele.clear();
            ele.sendKeys(textToSend);
            timeWait(0.5);
            System.out.println("Cargado el texto " + text + " correctamente");
            Allure.addAttachment("Envio de texto en input", "Cargado el texto " + text + " correctamente");
        } catch (NoSuchElementException | ElementNotInteractableException | TimeoutException e) {
            String message = "No se pudo interactuar con el input:\nElemento: " + locator + "\nLog: " + e.getMessage();
            throw new RuntimeException(message, e);
        } catch (WebDriverException e) {
            String message = "Error general de WebDriver al enviar el texto:\nElemento: " + locator + "\nLog: " + e.getMessage();
            throw new RuntimeException(message, e);
        } catch (Exception e) {
            String message = "Error desconocido al enviar el texto:\nElemento: " + locator + "\nLog: " + e.getMessage();
            throw new RuntimeException(message, e);
        }
    }

    /**
     * Método que permite enviar acciones de teclado sobre un elemento.
     * @param locator Localizador By (ID, XPATH, CSS, etc.)
     * @param seconds Tiempo de espera para la visibilidad
     * @param keys Acciones de teclado (ej: Keys.DOWN, Keys.ENTER, o texto plano)
     */
    public void sendKeyboardActions(By locator, int seconds, CharSequence... keys) {
        try {
            // 1. Esperar a que el elemento sea visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            // 2. Inicializar Actions
            Actions actionProvider = new Actions(driver);

            // 3. Ejecutar las acciones de teclado una por una
            StringBuilder logKeys = new StringBuilder();
            for (CharSequence key : keys) {
                actionProvider.sendKeys(element, key).perform();
                logKeys.append(key).append(" ");
            }

            // 4. Log y Allure
            String message = "Acciones de teclado enviadas: " + logKeys + " al elemento: " + locator.toString();
            System.out.println(message);
            Allure.addAttachment("Teclado", message);

        } catch (Exception e) {
            throw new RuntimeException(String.format(
                    "Error al interactuar con el teclado en el elemento: %s\nLog: %s",
                    locator.toString(), e.getMessage()));
        }
    }

    /**
     * Limpiar texto sobre un input determinado.
     * @param locator Objeto By del elemento a limpiar.
     * @param timeout Tiempo de espera hasta que no se encuentre el elemento.
     */
    public void clearTextOnInputBySelector(By locator, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            ele.clear();
            timeWait(1);
            System.out.println("Se ha limpiado el texto correctamente del input");
        } catch (TimeoutException e) {
            throw new RuntimeException("Error: Tiempo de espera agotado mientras se buscaba el elemento " + locator, e);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Error: No se encontró el elemento " + locator, e);
        } catch (ElementNotInteractableException e) {
            throw new RuntimeException("Error: No se puede interactuar con el elemento " + locator, e);
        } catch (Exception e) {
            throw new RuntimeException("Error desconocido al limpiar input: " + e.getMessage(), e);
        }
    }

    /**
     * Hacer scroll hacia un elemento específico.
     * @param locator Objeto By del elemento hacia donde realizar el scroll.
     * @param timeout Tiempo de espera hasta encontrar el elemento.
     */
    public void scrollToElementIsVisibility(By locator, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            WebElement val = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", val);
            if (val.isDisplayed()) {
                System.out.println("Desplazando al elemento " + locator);
                timeWait(0.5);
            }
        } catch (Exception e) {
            String message = "No se pudo interactuar con el elemento para scroll:\nElemento: " + locator + "\nLog: " + e.getMessage();
            throw new RuntimeException(message, e);
        }
    }

    /**
     * Obtener el texto de un elemento, retornará el texto solamente.
     * @param locator Objeto By para encontrar el elemento.
     * @param timeout Tiempo de espera.
     * @return El texto del elemento.
     */
    public String getTextOnElement(By locator, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            WebElement val = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return val.getText();
        } catch (Exception e) {
            String message = "Error al obtener texto del elemento " + locator + "\nLog: " + e.getMessage();
            throw new RuntimeException(message, e);
        }
    }

    // ##################################### CLICK ON ELEMENT ###########################################

    /**
     * Metodo para Hacer click sobre un elemento que se localice por selectores.
     * @param locator Objeto By del elemento.
     * @param timeout tiempo de espera mientras se localiza el elemento.
     */
    public void clickOnElementBySelector(By locator, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(locator));
            ele.click();
            System.out.println("Se realizo click sobre el elemento: " + locator);
            Allure.addAttachment("Click sobre elemento", "Click realizado en: " + locator);
            timeWait(0.8);
        } catch (Exception e) {
            String message = "No se pudo interactuar con el elemento para click:\nElemento: " + locator + "\nLog: " + e.getMessage();
            throw new RuntimeException(message, e);
        }
    }

    /**
     * Realiza multiples clics sobre un elemento.
     * @param locator Objeto By del elemento.
     * @param cantClick cantidad de veces que se hara click.
     * @param timeout Tiempo de espera inicial.
     */
    public void multiClickElementBySelector(By locator, int cantClick, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            for (int i = 0; i < cantClick; i++) {
                ele.click();
            }
            String logMessage = ("Se hicieron " + cantClick + " Clicks sobre el elemento " + locator);
            System.out.println(logMessage);
            Allure.addAttachment("Multiclick sobre elemento", logMessage);
            timeWait(1);
        } catch (Exception e) {
            String message = "Error al realizar multiclick en el elemento " + locator + "\nLog: " + e.getMessage();
            throw new RuntimeException(message, e);
        }
    }

    /**
     * Realiza un clic utilizando JavaScript.
     * Útil cuando un elemento está presente en el DOM pero es bloqueado físicamente por otros (tours, banners, etc).
     * @param locator Objeto By que identifica el elemento.
     * @param timeout Segundos de espera para la presencia del elemento.
     */
    public void clickByJS(By locator, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            // Esperamos a que el elemento esté presente en el DOM
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

            // Ejecutamos el clic forzado vía JavaScript
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", element);

            System.out.println("Click forzado por JS realizado en: " + locator);
            Allure.addAttachment("Click JS", "Se forzó el clic mediante JavaScript en: " + locator);
        } catch (Exception e) {
            String message = "Error al intentar click por JS en el elemento: " + locator + "\nLog: " + e.getMessage();
            throw new RuntimeException(message, e);
        }
    }

    /**
     * Simula un hover (mover el mouse sobre) un elemento localizado por By.
     * @param locator Localizador By (XPATH, ID, CSS, etc.)
     * @param seconds Tiempo de espera para la visibilidad
     */
    public void hoverOnElementBySelector(By locator, int seconds) {
        try {
            // 1. Esperar a que el elemento esté presente en el DOM
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            // 2. Realizar la acción de Hover (move_to_element en Python)
            Actions action = new Actions(driver);
            action.moveToElement(element).perform();

            // 3. Log y Reporte
            String logMessage = String.format("Se realizó hover sobre el elemento: %s", locator.toString());
            System.out.println(logMessage);
            Allure.addAttachment("Hover sobre elemento", logMessage);

            // Pequeña espera para estabilizar el hover
            timeWait(1);

        } catch (TimeoutException | NoSuchElementException e) {
            throw new RuntimeException(String.format(
                    "No se pudo hacer hover sobre el elemento:\nElemento: %s\nLog: %s",
                    locator.toString(), e.getMessage()));

        } catch (WebDriverException e) {
            throw new RuntimeException(String.format(
                    "Error general de WebDriver al hacer hover:\nElemento: %s\nLog: %s",
                    locator.toString(), e.getMessage()));

        } catch (Exception e) {
            throw new RuntimeException(String.format(
                    "Error desconocido al hacer hover:\nElemento: %s\nLog: %s",
                    locator.toString(), e.getMessage()));
        }
    }

    /**
     * Selecciona una opción de un input tipo select basado en una estrategia específica.
     * @param locator El selector (By) del elemento select.
     * @param searchType Tipo de búsqueda: "text", "value" o "index".
     * @param data El valor a buscar (String para text/value, Integer para index).
     * @param timeout Tiempo de espera en segundos.
     */
    public void selectOptionInList(By locator, String searchType, Object data, int timeout) {
        try {
            // Esperar y encontrar el elemento
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            Select select = new Select(element);
            String type = searchType.toLowerCase();

            switch (type) {
                case "text" -> select.selectByVisibleText(data.toString());
                case "value" -> select.selectByValue(data.toString());
                case "index" -> {
                    // Convertimos el objeto data a entero
                    int index = (data instanceof Integer) ? (int) data : Integer.parseInt(data.toString());
                    select.selectByIndex(index);
                }
                default ->
                        throw new IllegalArgumentException("El tipo de búsqueda debe ser: text, value o index. Ingresado: " + searchType);
            }

            // Obtenemos el texto que realmente quedó seleccionado en la UI
            String selectedText = select.getFirstSelectedOption().getText();
            String logMessage = String.format("Selector: %s | Tipo: %s | Valor enviado: %s | Seleccionado real: %s",
                    locator.toString(), type, data, selectedText);

            System.out.println(logMessage);

            // Forma correcta de Allure: (Título, Contenido)
            Allure.addAttachment("Registro de Selección en Select", logMessage);

        } catch (Exception e) {
            throw new RuntimeException("Error al seleccionar en el select " + locator + ": " + e.getMessage());
        }
    }

    /**
     * Método auxiliar para remover tildes y convertir a minúsculas.
     * Ejemplo: "México" -> "mexico"
     */
    private String normalizeText(String text) {
        if (text == null) return "";
        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "") // Remueve acentos
                .toLowerCase()
                .trim();
    }

    /**
     * Obtiene la lista de todos los textos visibles dentro de un select.
     * @param locator El selector del elemento select.
     * @param timeout Tiempo de espera en segundos.
     * @return Una lista de Strings con los textos de las opciones.
     */
    public List<String> getSelectOptionsText(By locator, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            Select select = new Select(element);

            // Usamos Java Streams (Java 8+) para convertir la lista de WebElements a Strings
            List<String> optionsText = select.getOptions().stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());

            System.out.println("Opciones encontradas: " + String.join(", ", optionsText));
            return optionsText;

        } catch (Exception e) {
            throw new RuntimeException("No se pudieron obtener las opciones del select: " + locator);
        }
    }

    // ####################### EVALUATE ELEMENT TO RETURN BOOLEAN #######################################

    /**
     * Esta funcion permite Encontrar un texto específico en pantalla y que retorne True si lo consigue.
     * @param locator Objeto By del elemento.
     * @param timeout Tiempo de espera.
     * @return Un valor True si se encuentra el elemento visible, de lo contrario False.
     */
    public boolean findElementIsDisplayed(By locator, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            boolean isDisplayed = element.isDisplayed();
            if (isDisplayed) {
                System.out.println("Se encontró en pantalla el elemento: " + locator);
            }
            return isDisplayed;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Funcion que retorna una lista de localizadores con el mismo tipo de selector.
     * @param locator Objeto By para buscar los elementos.
     * @return Listado de WebElements encontrados.
     */
    public List<WebElement> findListOfElementsBySelector(By locator) {
        try {
            timeWait(2);
            List<WebElement> val = driver.findElements(locator);
            if (val.isEmpty()) {
                throw new NoSuchElementException("La lista esta vacia");
            }
            return val;
        } catch (Exception e) {
            String message = "Error al buscar listado de elementos con selector: " + locator + "\nLog: " + e.getMessage();
            throw new RuntimeException(message, e);
        }
    }

    /**
     * Metodo que verifica si un checkbox o radio button esta actualmente marcado (con el ticket).
     * @param locator Objeto By que identifica el elemento.
     * @param timeout Segundos de espera.
     * @return true si esta marcado, false si esta desmarcado.
     */
    public boolean isElementSelectedBySelector(By locator, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            boolean isSelected = ele.isSelected();

            System.out.println("El elemento " + locator + " esta seleccionado: " + isSelected);
            return isSelected;

        } catch (Exception e) {
            String message = "Error al verificar seleccion del elemento: " + locator + "\nLog: " + e.getMessage();
            throw new RuntimeException(message, e);
        }
    }

    // ############################ ALLURE SCREENSHOT AND UPLOAD IMAGEN ###################################################

    /**
     * Funcion que permite tomar una captura de pantalla y adjuntarla comprimida a Allure.
     * @param name Nombre con el cual se guardara la captura.
     */
    public void screenshot(String name) {
        try {
            byte[] capture = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(capture));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            byte[] compressedImageData = outputStream.toByteArray();

            Allure.addAttachment(name, new ByteArrayInputStream(compressedImageData));
            System.out.println("Imagen capturada");
            timeWait(0.5);
        } catch (IOException e) {
            System.out.println("[LOG] Error al procesar captura: " + e.getMessage());
        }
    }

    /**
     * Metodo universal para cargar archivos/imagenes en inputs de tipo 'file'.
     * Maneja la visibilidad del elemento y asegura que la ruta sea valida antes de enviarla.
     * * @param locator Localizador del input real (By.id, By.xpath, etc).
     * @param filePath Ruta absoluta del archivo a cargar (obtenida de UploadFile).
     * @param timeout Segundos de espera para que el input este presente en el DOM.
     */
    public void uploadFile(By locator, String filePath, int timeout) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new RuntimeException("El archivo no existe en la ruta especificada: " + filePath);
            }
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            // Evaluar si el elemento esta disponible
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].style.display = 'block'; arguments[0].style.visibility = 'visible';",
                    fileInput
            );
            fileInput.sendKeys(filePath);
            System.out.println("Archivo cargado exitosamente: " + file.getName());
            Allure.addAttachment("Carga de Archivo", "Archivo: " + file.getName() + " cargado en el elemento: " + locator.toString());

        } catch (TimeoutException e) {
            throw new RuntimeException("El input de archivo no aparecio tras " + timeout + " segundos: " + locator, e);
        } catch (Exception e) {
            throw new RuntimeException("Error critico al intentar cargar el archivo: " + filePath + ". Log: " + e.getMessage(), e);
        }
    }
}