package condominiosDemo.pages;

import condominiosDemo.actions.BaseActions;
import org.openqa.selenium.WebDriver;

import java.util.Map;

import static condominiosDemo.actions.GeneralLocators.CommonsLocators.*;
import static condominiosDemo.actions.GeneralLocators.ViewCommunityLocators.*;

public class ViewCommunity extends BaseActions {

    /**
     * Constructor de BaseActions.
     *
     * @param driver Instancia activa de WebDriver.
     */
    public ViewCommunity(WebDriver driver) {
        super(driver);
    }

    public void fillFormToAssignAdminToCommunity(Map<String, String> dataMap){
        String fullname = dataMap.get("NamePublicAdministration");
        String[] splitFullname = fullname.split(" ");
        String name = splitFullname[0];
        String lastname = splitFullname[1];
        String emailAdmin = dataMap.get("EmailPublicAdministration");
        String cellAdmin = dataMap.get("CellphoneContact");
        String passAdmin = dataMap.get("PasswordAdmin");

        // Llenado de formulario
        scrollToElementIsVisibility(sectionAdministrator, 3);
        sendTextBySelector(nameAdminAssigned, name, 3);
        sendTextBySelector(lastnameAdminAssigned, lastname, 3);
        sendTextBySelector(emailAdminAssigned, emailAdmin, 3);
        sendTextBySelector(phoneAdminAssigned, cellAdmin, 3);
        sendTextBySelector(passAdminAssigned, passAdmin, 3);
        sendTextBySelector(confirmPassAdminAssigned, passAdmin, 3);


    }

    public void ClickButtonSaveAssignAdmin(){
        clickOnElementBySelector(btnAssignAdmin, 3);
    }

    public boolean checkCommunityAdminAssignedSuccess(){
        boolean val = findElementIsDisplayed(textInScreen(checkAdminAssigned), 3);
        if (val){
            screenshot("Se asigno el administrador correctamente a la comunidad");
            return true;
        }else {
            throw new RuntimeException("No se concreto la asignacion de administrador para la comunidad");
        }
    }




}
