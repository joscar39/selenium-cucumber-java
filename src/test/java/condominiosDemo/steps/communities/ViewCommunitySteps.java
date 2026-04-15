package condominiosDemo.steps.communities;

import condominiosDemo.applications.Applications;
import condominiosDemo.support.ErrorStepException;
import condominiosDemo.support.Hooks;
import condominiosDemo.support.TestContext;
import condominiosDemo.utils.GoogleSheetsService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

public class ViewCommunitySteps {


    public Applications app;
    private final TestContext context;


    public ViewCommunitySteps(TestContext context) {
        this.context = context;
        // Usamos el driver que Hooks ya inicializó en el @Before
        this.app = new Applications(Hooks.driver);

    }

    @Given("el usuario superadmin accede al modulo ver comunidades")
    public void el_usuario_superadmin_accede_al_modulo_ver_comunidades() {
        try {
            String nameCommunity = context.getDataMap().get("NameCommunity");
            String idCommunity = context.getDataMap().get("IdCommunity");
            app.communities.insertNameCommunityInInputSearch(nameCommunity);
            app.communities.clickOnButtonSearch();
            app.communities.checkIsCommunityIsVisibleOnResults(nameCommunity);
            app.communities.clickOnButtonViewComm();
            app.communities.checkRedirectionViewCommPage(nameCommunity, idCommunity);

        } catch (Exception ex) {
            String messageFail = "Fallo el step al acceder al modulo de ver comunidad \n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }

    @When("llena datos del adminsitrador a asignar a la comunidad")
    public void llenaDatosDelAdminsitradorAAsignarALaComunidad() {
        try {
            Map<String, String> dataExtracted = context.getDataMap();
            app.viewCommunity.fillFormToAssignAdminToCommunity(dataExtracted);
        } catch (Exception ex) {
            String messageFail = "Fallo el step al acceder al llenar formulario de asignacion de administrador \n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }

    @When("Se pulsa el boton asignar administrador")
    public void sePulsaElBotonAsignarAdministrador() {
        try {
            app.viewCommunity.ClickButtonSaveAssignAdmin();
        } catch (Exception ex) {
            String messageFail = "Fallo el step al acceder al pulsar el boton de asignar administrador \n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }


    @Then("Se valida la asignacion correcta de administrador para la comunidad")
    public void seValidaLaAsignacionCorrectaDeAdministradorParaLaComunidad() {
        try {
            boolean val = app.viewCommunity.checkCommunityAdminAssignedSuccess();
            if (val){
                Map<String, String> finalData = context.getDataMap();
                finalData.put("StatusCommunity", "Admin Assigned");
                GoogleSheetsService.updateOrSaveData(context.getSheetName(), finalData);
            } else {
            throw new Exception("No se asigno el administrador para la comunidad");
        }
        } catch (Exception ex) {
            String messageFail = "Fallo el step al acceder al validar el mensaje de asignacion exitosa de administrador \n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }
}
