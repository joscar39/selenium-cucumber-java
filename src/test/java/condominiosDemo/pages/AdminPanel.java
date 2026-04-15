package condominiosDemo.pages;

import condominiosDemo.actions.BaseActions;
import condominiosDemo.utils.ExcelReader;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static condominiosDemo.actions.GeneralLocators.AdminPanelLocators.*;
import static condominiosDemo.actions.GeneralLocators.AdminResidentsLocators.*;

public class AdminPanel extends BaseActions {

    /**
     * Constructor de BaseActions.
     *
     * @param driver Instancia activa de WebDriver.
     */
    public AdminPanel(WebDriver driver) {
        super(driver);
    }

    public void clickOnCommunityListedMyCommunitiesPage(String community, String identificationCommunity) {
        boolean val = findElementIsDisplayed(locRutSpecific(identificationCommunity), 3);
        if (val){
            clickOnElementBySelector(cardCommByCard(community), 3);
        }else {
            throw new RuntimeException(String.format("No se visualizó el card de la comunidad: %s con identificacion: %s", community, identificationCommunity));
        }
    }

    public void  checkModalJourneyInitial(){
        boolean val = findElementIsDisplayed(modalJourneyInitial, 3);
        if (val){
            screenshot("Se mostro la modal de viaje inicial en la configuracion de comunidad");
            clickOnElementBySelector(btnStarTour, 3);
        }else {
            screenshot("No se visualizó la modal para inicializar el tour por la web");
        }
    }

    public void checkIsVisibleSectionTaskPending(){
        scrollToElementIsVisibility(sectionTaskPending, 3);
        boolean val = findElementIsDisplayed(pendingItemCreateProperties, 3);
        if (val){
            screenshot("Se muestra la tarea pendiente de crear unidades/propiedades");
        }else {
            throw new RuntimeException("No se visualizó la tarea pendiente de crear unidades/propiedades");
        }
    }

    public void clickOnPendingItemCreateProperties(){
        clickOnElementBySelector(redirectionTaskPending, 3);
    }

    public void checkRedirectionToPageImportProperties(){
        boolean val = findElementIsDisplayed(checkImportationPropertiesPage, 3);
        if (val){
            screenshot("Se mostro la redireccion a la pagina de importacion de unidades/propiedades");
        }else {
            throw new RuntimeException("No se redirecciono a la pagina de importar unidades/propiedades");
        }
    }

    public void submitTemplatePropertiesCommunities(String fileName){

        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator +
                "test" + File.separator + "resources" + File.separator + "files" + File.separator + File.separator + "documents" + File.separator + fileName;

        // Se carga el archivo seleccionado en el input de upload File
        uploadFile(inputUploadTemplateExcel, path, 3);
        // confirmar que se subio el archivo correctamente
        boolean val = findElementIsDisplayed(checkFileUploaded, 3);
        if (val){
            screenshot("Se selecciono correctamente el documento a importar");
            // Se hace click en el boton para subir documento
            clickOnElementBySelector(btnUploadFile, 3);
        } else {
            throw new RuntimeException("No se mostro como seleccionado el documento a importar");
        }
    }

    public void confirmateIfFileIsDeployedSuccess(){
        int i = 0;
        int repeat = 6;
        boolean process = false;

        while (i < repeat) {
            i++;
            System.out.println("🔍 Intento " + i + " de " + repeat + " : Verificando estado del documento...");

            // 1. Obtener el texto del elemento
            String status = getTextOnElement(statusLastFileUploading, 5).trim();

            // 2. CASO ÉXITO: El documento se importó
            if (status.equalsIgnoreCase("Importado") || status.equalsIgnoreCase("Éxito")) {
                screenshot("Importacion_Exitosa");
                System.out.println("✅ Documento procesado correctamente.");
                process = true;
                break;
            }
            // 3. CASO FALLO: El servidor indica que hubo un error
            if (status.equalsIgnoreCase("Falló")) {
                String messageFail = "❌ El documento no se cargó: El sistema devolvió estado 'Falló'.";
                // Lanzamos la excepción para que el Hook detenga la ejecución y tome captura
                throw new RuntimeException(messageFail);
            }
            // 4. CASO PENDIENTE: Si no es ninguna de las anteriores, refrescar y reintentar
            System.out.println("⏳ El documento sigue en proceso. Refrescando navegador...");

            // Refresco del navegador
            driver.navigate().refresh();
            timeWait(3);
        }

        // 5. CASO TIMEOUT: Si sale del bucle sin éxito
        if (!process) {
            throw new RuntimeException(" Se superarion los limites de intentos para esperar que el documento cargue exitosamente");
        }
    }

    public void validateAlmostOnePropertiesIsRegistered(){
        hoverOnElementBySelector(locatorMenuSideBar, 3);
        clickOnElementBySelector(itemCommunityConnected, 3);
        clickOnElementBySelector(optResidents, 3);

        boolean val = findElementIsDisplayed(checkResidentsPage, 3);
        if (val){
            screenshot("Se mostro la pantalla de residentes correctamente");

            // Leer los datos del archivo cargado para validar si se registro corretamente
            String fileName = "Plantilla Propiedades Comunidad CC.xlsx";
            String columnsSearch = "Unidad";
            int repeatSearch = 5;
            ExcelReader reader = new ExcelReader();
            List<String> properties = reader.readColumnFile(fileName, columnsSearch, repeatSearch);
            List<String> unityUnknow = new ArrayList<>();
            for (String und : properties) {

                boolean isVisible = findElementIsDisplayed(locNameProperty(und), 3);

                if (isVisible) {
                    System.out.println("✅ Unidad encontrada: " + und);
                } else {
                    System.out.println("❌ Unidad NO visible: " + und);
                    unityUnknow.add(und);
                }
            }

            // 3. Decisión final basada en los resultados
            if (unityUnknow.isEmpty()) {
                screenshot("Validacion_Unidades_Exitosa");
                System.out.println("✨ Todas las unidades se cargaron correctamente en la interfaz.");
            } else {
                // Formateamos el mensaje de error con las unidades faltantes
                String mensajeError = "No se redireccionó correctamente al apartado de residentes o faltan unidades. " +
                        "Unidades no encontradas: " + unityUnknow;

                throw new RuntimeException(mensajeError);
            }
        } else {
            throw new RuntimeException("No se redirecciono al apartado de residentes");
        }


    }
}
