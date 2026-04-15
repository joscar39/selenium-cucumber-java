package condominiosDemo.support;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


public class WebDriverFactory {

    public static WebDriver driver;

    /**
     * Inicializa el driver llamando a la Factory si no existe uno previo.
     */
    public static WebDriver setup(String webBrowser) {

        // 1. Validar si el navegador viene nulo desde la terminal
        if (webBrowser == null || webBrowser.isEmpty()) {
            System.out.println("No se especificó navegador en la terminal (-Dbrowser). Usando CHROME por defecto.");
            webBrowser = "CHROME";
        }

        // 2. Leer 'headless' directamente desde la terminal (-Dheadless=true)
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        // 3. Inicializar el driver asignándolo directamente a la variable de CLASE (static)
        switch (webBrowser.toUpperCase()) {
            case "CHROME" -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--disable-cache");
                if (isHeadless) chromeOptions.addArguments("--headless=new");
                driver = new ChromeDriver(chromeOptions);
            }
            case "FIREFOX" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless) firefoxOptions.addArguments("-headless");
                driver = new FirefoxDriver(firefoxOptions);
            }
            case "MICROSOFT EDGE", "EDGE" -> {
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (isHeadless) edgeOptions.addArguments("--headless");
                driver = new EdgeDriver(edgeOptions);
            }
            default -> throw new RuntimeException("Navegador no soportado o nombre inválido: " + webBrowser);
        }

        driver.manage().window().maximize();
        return driver;
    }
}