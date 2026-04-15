package condominiosDemo.steps.administrator;

import condominiosDemo.applications.Applications;
import condominiosDemo.support.ErrorStepException;
import condominiosDemo.support.Hooks;
import condominiosDemo.support.TestContext;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AdminPanelSteps {

    public Applications app;
    private final TestContext context;


    public AdminPanelSteps(TestContext context) {
        this.context = context;
        // Usamos el driver que Hooks ya inicializó en el @Before
        this.app = new Applications(Hooks.driver);

    }

    @Given("un administrador se encuentra loggeado en la vista de panel de CF")
    public void unAdministradorSeEncuentraLoggeadoEnLaVistaDePanelDeCf() {
        try {
            String emailAdmin = context.getDataMap().get("EmailPublicAdministration");
            String passAdmin = context.getDataMap().get("PasswordAdmin");
            String nameCommunity = context.getDataMap().get("NameCommunity");
            String rutOrRfcCommunity = context.getDataMap().get("CommunityIDNumber(RUTorRFC)");
            app.login.checkRedirectionToLoginPage();
            app.login.loginAs(emailAdmin, passAdmin);
            app.login.checkRedirectionMyCommunitiesPage();
            app.adminPanel.clickOnCommunityListedMyCommunitiesPage(nameCommunity, rutOrRfcCommunity);
            app.adminPanel.checkModalJourneyInitial();

        } catch (Exception ex) {
            String messageFail = "Fallo el step al acceder a comunidad feliz como administrador\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }
    @When("el usuario accede a lista de tareas pendientes")
    public void el_usuario_accede_a_lista_de_tareas_pendientes() {
       try {
            app.adminPanel.checkIsVisibleSectionTaskPending();
        } catch (Exception ex) {
            String messageFail = "Fallo el step al usuario acceder a la lista de tareas pendientes\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }
    @When("selecciona opcion Crear unidades")
    public void selecciona_opcion_crear_unidades() {
        try {
            app.adminPanel.clickOnPendingItemCreateProperties();
            app.adminPanel.checkRedirectionToPageImportProperties();
        } catch (Exception ex) {
            String messageFail = "Fallo el step al seleccionar la opcion de crear unidades\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }
    @When("Se carga el template para unidades: {string}")
    public void seCargaElTemplateParaUnidades(String fileName) {
        try {
            app.adminPanel.submitTemplatePropertiesCommunities(fileName);
        } catch (Exception ex) {
            String messageFail = "Fallo el step al cargar el documento template de unidades\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }
    @Then("Se confirma que la plantilla se cargo correctamente")
    public void se_confirma_que_la_plantilla_se_cargo_correctamente() {
        try {
            app.adminPanel.confirmateIfFileIsDeployedSuccess();
        } catch (Exception ex) {
            String messageFail = "Fallo el step al \n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }
    @Then("se muestra unidades registradas en modulo residentes")
    public void se_muestra_unidades_registradas_en_modulo_residentes() {
        try {
            app.adminPanel.validateAlmostOnePropertiesIsRegistered();
        } catch (Exception ex) {
            String messageFail = "Fallo el step al \n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }
}
