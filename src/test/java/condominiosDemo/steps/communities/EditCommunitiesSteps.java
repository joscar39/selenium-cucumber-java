package condominiosDemo.steps.communities;

import condominiosDemo.applications.Applications;
import condominiosDemo.support.ErrorStepException;
import condominiosDemo.support.Hooks;
import condominiosDemo.support.TestContext;
import condominiosDemo.utils.GoogleSheetsService;
import condominiosDemo.utils.UploadFile;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

public class EditCommunitiesSteps {

    public Applications app;

    private final TestContext context;
    private String nameCommunity;
    private Map<String, String> newDataGenerated;



    public EditCommunitiesSteps(TestContext context) {
        this.context = context;
        // Usamos el driver que Hooks ya inicializó en el @Before
        this.app = new Applications(Hooks.driver);

    }

    @Given("el usuario superadmin accede al modulo editar comunidades")
    public void elUsuarioAcceedAlModuloEditarComunidades() {
        try {
            this.nameCommunity = context.getDataMap().get("NameCommunity");
            app.communities.insertNameCommunityInInputSearch(this.nameCommunity);
            app.communities.clickOnButtonSearch();
            app.communities.checkIsCommunityIsVisibleOnResults(this.nameCommunity);
            app.communities.clickOnButtonEditComm();
            app.communities.checkRedirectionEditCommPage();

        } catch (Exception ex) {
            String messageFail = "Fallo el step al acceder al modulo de editar comunidades \n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }

    @When("el usuario superadmin activa permisos temporales en la vista")
    public void elUsuarioSuperadminActivaPermisosTemporalesEnLaVista() {
        try {
            app.editCommunities.ActiveTemporaryPermissions();
        } catch (Exception ex) {
            String messageFail = "Fallo el step al otorgar permisos temporales para el administrador en la vista\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }

    @When("Se ingresa imagen de la comunidad")
    public void seIngresaImagenDeLaComunidad() {
        try {
            String imgCommunity = UploadFile.getRandomCommunityImagePath();
            app.editCommunities.InsertImageOfCommunity(imgCommunity);
        } catch (Exception ex) {
            String messageFail = "Fallo el step al cargar iamgen de la comunidad\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }

    @When("Ingresa logo de la administracion")
    public void ingresaLogoDeLaAdministracion() {
        try {
            String imgAdministration = UploadFile.getRandomAdministratorImagePath();
            app.editCommunities.InsertImageOfAdministration(imgAdministration);
        } catch (Exception ex) {
            String messageFail = "Fallo el step al cargar la imagen de la administracion\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }


    @When("Se modifica datos bancarios para la comunidad segun la region")
    public void se_modifica_datos_bancarios_para_la_comunidad_segun_la_region() {
        try {
            // RECUPERACIÓN: Obtenemos el diccionario que se ha estado guardando con datos de la BD
            Map<String, String> dataFromContext = context.getDataMap();
            this.newDataGenerated = app.editCommunities.fillFormDataBank(dataFromContext);
            context.setDataMap(this.newDataGenerated);
        } catch (Exception ex) {
            String messageFail = "Fallo el step al modificar los datos de la comnidad iamgen y datso bancarios\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }
    @Then("se muestra mensaje de comunidad actualiza con exito")
    public void se_muestra_mensaje_de_comunidad_actualiza_con_exito() {
        try {
            boolean val;
            val = app.editCommunities.checkUpdateCommunitySuccess();
            if (val){
                // Obtenemos la data del contexto para asegurarnos de tener los últimos cambios
                Map<String, String> finalData = context.getDataMap();
                finalData.put("StatusCommunity", "CommunityEdited");

                // USAR CONTEXTO: Usamos getSheetName() para saber en qué hoja guardar6
                GoogleSheetsService.updateOrSaveData(context.getSheetName(), finalData);
            }
        } catch (Exception ex) {
            String messageFail = "Fallo el step al confirmar la edicion exitsa de los datos dela comunidad\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }



}
