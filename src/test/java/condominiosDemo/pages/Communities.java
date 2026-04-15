package condominiosDemo.pages;

import condominiosDemo.actions.BaseActions;
import org.openqa.selenium.WebDriver;

import static condominiosDemo.actions.GeneralLocators.CommonsLocators.textInScreen;
import static condominiosDemo.actions.GeneralLocators.CommunitiesLocators.*;


public class Communities extends BaseActions {
    /**
     * Constructor de BaseActions.
     *
     * @param driver Instancia activa de WebDriver.
     */
    public Communities(WebDriver driver) {
        super(driver);
    }

    public void checkRedirectionToCommunitiesPage() {

        boolean val = findElementIsDisplayed(checkCommunitiesPage, 10);

        if (val) {
            screenshot("Se mostro correctamente la pagina de listado de comunidades");
        } else {
            // Si es false, lanzamos el error para detener la prueba
            throw new RuntimeException("No se visualizó el elemento de validación en la pantalla de comunidadeds");
        }
    }

    public void insertNameCommunityInInputSearch(String nameComunity){

        sendTextBySelector(inputSearchCommunity, nameComunity, 3);
    }

    public void clickOnButtonSearch(){
        clickOnElementBySelector(searchBtn, 3);
    }

    public void clickOnButtonEditComm(){
        clickOnElementBySelector(actionEditComm, 3);
    }

    public void clickOnButtonViewComm(){
        clickOnElementBySelector(actionSeeComm, 3);
    }

    public void clickOnButtonDeactivateComm(){
        clickOnElementBySelector(actionDeactivateComm, 3);
    }

    public boolean checkIsCommunityIsVisibleOnResults(String nameCommunity){
        boolean val = findElementIsDisplayed(textInScreen(nameCommunity), 15);

        if (val) {
            screenshot("Se mostro correctamente la comunidad filtrada");
            return true;
        } else {
            // Si es false, lanzamos el error para detener la prueba
            throw new RuntimeException("No se visualizó la comunidad esperada como resultado de busqueda " + nameCommunity);
        }

    }

    public boolean checkRedirectionEditCommPage(){
        boolean val = findElementIsDisplayed(textInScreen(checkRedirectionEditComm), 15);

        if (val) {
            screenshot("Se mostro correctamente la page de editar comunidad");
            return true;
        } else {
            // Si es false, lanzamos el error para detener la prueba
            throw new RuntimeException("No se visualizó la pantalla de edicion de comunidad ");
        }

    }

    public boolean checkRedirectionViewCommPage(String name, String id){
        boolean val = findElementIsDisplayed(checkRedirectionViewComm(name, id), 15);

        if (val) {
            screenshot("Se mostro correctamente la page de ver comunidad");
            return true;
        } else {
            // Si es false, lanzamos el error para detener la prueba
            throw new RuntimeException("No se visualizó la pantalla de ver comunidad ");
        }

    }

    public void clickOnButtonPersonifyAdmin(){
        clickOnElementBySelector(actionImperAdmin, 3);
    }




}
