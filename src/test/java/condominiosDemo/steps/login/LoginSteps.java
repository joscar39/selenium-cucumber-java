package condominiosDemo.steps.login;

import condominiosDemo.applications.Applications;
import condominiosDemo.support.ErrorStepException;
import condominiosDemo.support.Hooks;
import condominiosDemo.support.TestContext;
import condominiosDemo.utils.CredentialsManager;
import condominiosDemo.utils.GoogleSheetsService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

public class LoginSteps {

    public Applications app;
    private final TestContext context;
//    private String initialStatusCommunity;

    // Constructor: Se ejecuta ANTES que cualquier step
    public LoginSteps(TestContext context) {
        this.context = context;
        // Usamos el driver que Hooks ya inicializó en el @Before
        this.app = new Applications(Hooks.driver);
    }

    @Given("la aplicación está inicializada y en la pantalla de login")
    public void la_aplicación_está_inicializada_y_en_la_pantalla_de_login() {
        try {
            app.login.checkRedirectionToLoginPage();
        } catch (Exception ex) {
            String messageFail = "Fallo el step al inicializar el navegador en la pantalla de login\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }
    @When("Se ingresas las credenciales como superadmin")
    public void se_ingresas_las_credenciales_como_superadmin() {
        try {
            app.login.loginAs(CredentialsManager.getEmail(), CredentialsManager.getPassword());
        } catch (Exception ex) {
            String messageFail = "Fallo el step al ingresar las credenciales de superadmin\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }

    @Then("Se redireccionar al home como superadmin exitosamente")
    public void se_redireccionar_al_home_como_superadmin_exitosamente() {
        try {
            app.login.checkLoginSuperAdmin();
        } catch (Exception ex) {
            String messageFail = "Fallo el step al validar el redireccionamiento al home como superadmin\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }

    @Given("se leen los datos de la BD para acceder como admin: {string} con status: {string}")
    public void seLeenLosDatosDeLaBDParaAccederComoAdminConStatus(String sheetName, String statusCommunity) {
        try {
            // Estado inicial de la comunidad
            Map<String, String> dataGenerated = GoogleSheetsService.getAndLockRowByStatus(sheetName, statusCommunity);

            // Validación adicional: si no encuentra filas, lanzamos excepción para que entre al catch
            if (dataGenerated == null || dataGenerated.isEmpty()) {
                throw new RuntimeException("No se encontró registro en la BD que coincida con los criterios. de busqueda "+ sheetName+ " " + statusCommunity );
            }
            // Se guarda el diccionario en el contexto global
            context.setDataMap(dataGenerated);
        } catch (Exception ex) {
            String messageFail = "Fallo el step al validar el \n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }

    @When("Se ingresas las credenciales de usuario admin de una comunidad")
    public void seIngresasLasCredencialesDeUsuarioAdminDeUnaComunidad() {
        try {
            String emailAdmin = context.getDataMap().get("EmailPublicAdministration");
            String passAdmin = context.getDataMap().get("PasswordAdmin");
            app.login.loginAs(emailAdmin, passAdmin);
        } catch (Exception ex) {
            String messageFail = "Fallo el step al ingresar las credenciales del usauiro administrador de una comunidad\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }

    @When("se muestra redireccion hacia pantalla de terminos y condiciones")
    public void seMuestraRedireccionHaciaPantallaDeTerminosYCondiciones() {
        try {
            app.login.checkRedirectionTermAndConditionsPage();
        } catch (Exception ex) {
            String messageFail = "Fallo el step al redireccionar a la pantalla de terminos y condiciones\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }

    @When("Se debe configurar contraseña nueva como admin")
    public void seDebeConfigurarContraseñaNuevaComoAdmin() {
        try {
            String password = context.getDataMap().get("PasswordAdmin");
            app.login.changeNewPassword(password);
        } catch (Exception ex) {
            String messageFail = "Fallo el step al redireccionar a la pantalla de terminos y condiciones\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }


    @Then("Se redirecciona a la pantalla de mis comunidades")
    public void seRedireccionaALaPantallaDeMisComunidades() {
        try {
            boolean val = app.login.checkRedirectionMyCommunitiesPage();
            if (val){
                Map<String, String>finalData = context.getDataMap();
                finalData.put("StatusCommunity", "ReadyForConfiguration");
                GoogleSheetsService.updateOrSaveData(context.getSheetName(), finalData);
            } else {
                throw new Exception("No se redirecciono a la pantalla de mis comunidades");
            }

        } catch (Exception ex) {
            String messageFail = "Fallo el step al redireccionar a la pantalla de mis comunidades\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }


}
