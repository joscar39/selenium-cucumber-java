package condominiosDemo.steps.newCommunity;

import condominiosDemo.applications.Applications;
import condominiosDemo.support.ErrorStepException;
import condominiosDemo.support.Hooks;
import condominiosDemo.support.TestContext;
import condominiosDemo.utils.CredentialsManager;
import condominiosDemo.utils.GoogleSheetsService;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

public class NewCommunityNormalSteps {

    public Applications app;
    private final TestContext context;
    private boolean resultCreatedCommunity;
    private String resultIdCommunityCreated;

    /**
     * Constructor con Inyección de Dependencias.
     * Eliminamos el constructor vacío para que PicoContainer gestione el TestContext.
     */
    public NewCommunityNormalSteps(TestContext context) {
        this.context = context;
        this.app = new Applications(Hooks.driver);
    }


    @Given("un usuario superadmin se encuentra loggeado en CF")
    public void un_usuario_superadmin_se_encuentra_loggeado_en_cf() {
         try {
             app.login.checkRedirectionToLoginPage();
             app.login.loginAs(CredentialsManager.getEmail(), CredentialsManager.getPassword());
             app.login.checkLoginSuperAdmin();
        } catch (Exception ex) {
            String messageFail = "Fallo el step al acceder como usuario superadmin a CF\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }
    @Given("el usuario ingresa al modulo nueva comunidad normal desde el sidebar")
    public void el_usuario_ingresa_al_modulo_nueva_comunidad() {
         try {
            app.home.goIntoModuleCreateNormalCommunity();
        } catch (Exception ex) {
            String messageFail = "Fallo el step al acceder al modulo de nueva comunidad desde el sidebar\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }
    @When("llenar formulario de creacion de nueva comunidad normal de tipo: {string} en la region: {string}")
    public void ingresa_los_datos_de_la_comunidad_la_region(String typeCommunity, String regionCommunity) {
         try {
             // Generamos la data y la subimos inmediatamente al contexto compartido
             Map<String, String> data = app.newCommunityNormal.fillFormNormalCommunityRegistration(typeCommunity, regionCommunity);
             context.setDataMap(data);
        } catch (Exception ex) {
             String messageFail = String.format(
                     "Fallo el step al ingresar los datos de la comunidad\n El motivo: %s",
                     ex.getMessage()
             );
             throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }
    @Then("se muestra el mensaje de confirmacion de cuenta creada")
    public void se_muestra_el_mensaje_de_confirmacion() {
         try {
            this.resultCreatedCommunity =app.newCommunityNormal.checkCommunityCreatedSuccess();
        } catch (Exception ex) {
            String messageFail = "Fallo el step al valdiar mensaje de confirmacion\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }

    @And("se muestra el Id y nombre de la comunidad creada")
    public void seMuestraElIdYNombreDeLaComunidadCreada() {
        try {
            this.resultIdCommunityCreated =app.newCommunityNormal.checkCommunityIdCreatedSuccess();
        } catch (Exception ex) {
            String messageFail = "Fallo el step al valdiar mensaje de confirmacion\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }


    @Then("Se almacenan los datos registrados en la BD: {string}")
    public void seAlmacenanLosDatosRegistradosEnLaBD(String nameSheetBd) {
        try {
            // Recuperamos el mapa que se empezó a llenar en el step de 'llenar formulario'
            Map<String, String> dataGenerated = context.getDataMap();
            // 1. Verificamos que la prueba haya llegado hasta aquí con éxito
            if (this.resultCreatedCommunity && dataGenerated != null && this.resultIdCommunityCreated != null) {

                // 2. Anexamos el ID extraído al diccionario existente
                dataGenerated.put("IdCommunity", this.resultIdCommunityCreated);

                // 3. Anexamos el status de la comunidad para usos posteriores
                dataGenerated.put("StatusCommunity", "Registered pending properties");

                // 4. Invocamos el servicio para importar los resultados a la BD.
                // Nota: El servicio ya sabe qué archivo abrir gracias a loadDatabaseNameFromProperties()
                GoogleSheetsService.saveDataInLastEmptyRow(nameSheetBd, dataGenerated);

                System.out.println("✅ Sincronización con Google Sheets completada para: " + nameSheetBd);
            } else {
                throw new Exception("La comunidad no se creó correctamente o no hay datos para guardar.");
            }
        } catch (Exception ex) {
            // Aquí se capturará tanto el fallo de Google Sheets como tu RuntimeException de archivo no encontrado
            throw new ErrorStepException("Fallo crítico al guardar en BD: " + ex.getMessage(), ex.getMessage());
        }

    }


}
