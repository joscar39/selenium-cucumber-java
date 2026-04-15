package condominiosDemo.support;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hooks {

    public static WebDriver driver;

    // Códigos ANSI para emular Colorama de Python
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";

    @Before
    public void beforeScenario(Scenario scenario) {

        System.out.println("################");
        System.out.println("[ CONFIGURACION ] - Inicializando la configuracion del controlador");
        System.out.println("################");

        // Inicializa el driver (Ajusta el nombre del navegador según tu ConfigPage)
        driver = WebDriverFactory.setup(System.getProperty("browser", "CHROME"));

        System.out.println(YELLOW + "##############################" + RESET);
        System.out.println("[ SCENARIO ] - " + BLUE + scenario.getName() + RESET);
        System.out.println(YELLOW + "##############################" + RESET);
    }
    @After
    public void afterScenario(Scenario scenario) {

        if (scenario.isFailed()) {
            // Lógica de captura de error
            String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String screenshotName = scenario.getName() + "_" + currentTime;

            // Adjuntar a Allure
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(screenshotName, new ByteArrayInputStream(screenshot));

            System.out.println(RED + "###############################################" + RESET);
            System.out.println(RED + "[  DRIVER STATUS  ] - Limpiando y cerrando instancia debido a un error" + RESET);
            System.out.println(RED + scenario.getName() + RESET);
            System.out.println(RED + "###############################################" + RESET);
            System.out.println("____________________________________________________________________________");
        } else {
            System.out.println(GREEN + "----------------------" + RESET);
            System.out.println(GREEN + "[  SCENARIO STATUS  ] - Prueba Exitosa (PASS): " + scenario.getName() + RESET);
            System.out.println(GREEN + "----------------------" + RESET);

        }

        if (driver != null) {
            driver.quit();

        }
    }
}