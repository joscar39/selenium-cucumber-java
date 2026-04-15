package condominiosDemo.pages;

import condominiosDemo.actions.BaseActions;

import org.openqa.selenium.WebDriver;


import static condominiosDemo.actions.GeneralLocators.LoginPageLocators.*;
import static condominiosDemo.actions.GeneralLocators.CommonsLocators.*;

public class Login extends BaseActions {

    /**
     * Constructor de BaseActions.
     *
     * @param driver Instancia activa de WebDriver.
     */
    public Login(WebDriver driver) {

        super(driver);
    }

    public void checkRedirectionToLoginPage() {
        navigateToUrl(LOGIN_URL, 10);

        boolean val = findElementIsDisplayed(emailBox, 10);

        if (val) {
            screenshot("Se mostro correctamente el login");
        } else {
            // Si es false, lanzamos el error para detener la prueba
            throw new RuntimeException("No se visualizó el elemento de validación en la pantalla de login");
        }
    }

    public void insertEmail(String email){
        // Validamos que no sea nulo y que no esté vacío.
        if (email != null && !email.trim().isEmpty()) {
            sendTextBySelector(emailBox, email, 3);
        } else {
            String valorError = (email == null || email.isEmpty()) ? "Empty" : email;
            throw new RuntimeException("El dato ingresado como Email no es correcto o esta vacio: " + valorError);
        }
    }

    public void insertPassword(String password){
        // Validamos que no sea nulo y que no esté vacío.
        if (password != null && !password.trim().isEmpty()) {
            sendTextBySelector(passwordBox, password, 3);
        } else {
            String valorError = (password == null || password.isEmpty()) ? "Empty" : password;
            throw new RuntimeException("El dato ingresado como Email no es correcto o esta vacio: " + valorError);
        }
    }

    public void clickOnButtonLogin(){
        clickOnElementBySelector(loginBtn, 3);
    }

    public void loginAs(String email, String password) {
        // Validamos que no sean nulos o vacíos antes de proceder
        if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
            insertEmail(email);
            insertPassword(password);
            clickOnButtonLogin();
        } else {
            throw new RuntimeException("Credenciales inválidas para realizar el login");
        }
    }

    public void checkLoginSuperAdmin(){
       boolean val = findElementIsDisplayed(textInScreen(checkLoginSuperAdmin), 3);
       if (val){
           screenshot("Se hizo login como superadmin exitosamente");
       } else {
           // Si es false, lanzamos el error para detener la prueba
           throw new RuntimeException("No se logro hacer login como superadmin");
       }
    }

    public void checkRedirectionTermAndConditionsPage(){
        boolean val = findElementIsDisplayed(checkPageTermAndConditions, 3);

        if (val) {
            screenshot("Se mostro correctamente la page de terminos y condiciones");
            clickOnElementBySelector(btnAcceptTermConditions, 3);
        } else {
            throw new RuntimeException("No se visualizó la pantalla de terminos y condiciones");
        }
    }

    public void changeNewPassword(String password){
        sendTextBySelector(inputNewPassword, password, 3);
        sendTextBySelector(inputConfimNewPass, password, 3);
        clickOnElementBySelector(btnChangePassword, 3);
    }


    public boolean checkRedirectionMyCommunitiesPage(){
        boolean val = findElementIsDisplayed(checkMyCommunitiesPage, 3);

        if (val) {
            screenshot("Se mostro correctamente la page de mis comunidades");
            return true;
        } else {
            throw new RuntimeException("No se visualizó la pantalla de mis comunidades");
        }

    }


}
