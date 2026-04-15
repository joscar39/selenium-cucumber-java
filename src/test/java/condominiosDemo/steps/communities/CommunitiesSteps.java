package condominiosDemo.steps.communities;

import condominiosDemo.applications.Applications;
import condominiosDemo.support.ErrorStepException;
import condominiosDemo.support.Hooks;
import condominiosDemo.support.TestContext;
import condominiosDemo.utils.GoogleSheetsService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

public class CommunitiesSteps {

    public Applications app;
    private final TestContext context;
    private String initialStatusCommunity;


    public CommunitiesSteps(TestContext context) {
        this.context = context;
        // Usamos el driver que Hooks ya inicializó en el @Before
        this.app = new Applications(Hooks.driver);

    }


    @Given("el usuario superadmin se encuentra en el modulo comunidades")
    public void el_usuario_superadmin_se_encuentra_en_el_modulo_comunidades() {
       try {
            app.home.goIntoModuleCommunities();
            app.communities.checkRedirectionToCommunitiesPage();
        } catch (Exception ex) {
            String messageFail = "Fallo el step al acceder al modulo de listado de comunidades\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }

    @Given("se leen y extraen los datos de pruebas desde la BD: {string}, con status: {string}")
    public void seLeenYExtraenLosDatosDePruebasDesdeLaBDConStatus(String sheetName, String statusCommunity) {
        try {
            // Obtener un valor aleatorio de una comunidad bajo el status indicado y actualizar status a IN_PROGRESS
            this.initialStatusCommunity = statusCommunity;
            context.setSheetName(sheetName);

            Map<String, String> dataGenerated = GoogleSheetsService.getAndLockRowByStatus(sheetName, statusCommunity);

            // Validación adicional: si no encuentra filas, lanzamos excepción para que entre al catch
            if (dataGenerated == null || dataGenerated.isEmpty()) {
                throw new RuntimeException("No se encontró registro en la BD que coincida con los criterios. de busqueda "+ sheetName+ " " + statusCommunity );
            }
            // Se guarda el diccionario en el contexto global
            context.setDataMap(dataGenerated);
        } catch (Exception ex) {
            String messageFail = String.format(
                    "Fallo el step al obtener los datos de la BD en la hoja [%s] y con status de comunidad [%s].%nEl motivo: %s",
                    sheetName,
                    statusCommunity,
                    ex.getMessage()
            );
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }

    @When("Ingresa nombre de comunidad a buscar")
    public void ingresa_nombre_de_comunidad_a_buscar() {
        try {

            app.communities.insertNameCommunityInInputSearch(context.getDataMap().get("NameCommunity"));
        } catch (Exception ex) {
            String messageFail = "Fallo el step al ingresar el nombre de la comunidad a buscar\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }
    @When("Se pulsa boton filtrar")
    public void se_pulsa_boton_filtrar() {
        try {
            app.communities.clickOnButtonSearch();
        } catch (Exception ex) {
            String messageFail = "Fallo el step al pulsar boton filtrar\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }
    @Then("se muestra resultados exitosos de busqueda de comunidad")
    public void se_muestra_resultados_exitosos_de_busqueda() {
        try {
             boolean val = app.communities.checkIsCommunityIsVisibleOnResults(context.getDataMap().get("NameCommunity"));

            if (val) {
                // Restauramos el estado inicial en el mapa y actualizamos la BD
                Map<String, String> data = context.getDataMap();
                data.put("StatusCommunity", this.initialStatusCommunity);
                GoogleSheetsService.updateOrSaveData(context.getSheetName(), data);
            } else {
                throw new Exception("No se obtuvo el filtrado esperado para la comunidad " + context.getDataMap().get("NameCommunity"));
            }

        } catch (Exception ex) {
            String messageFail = "Fallo el step al validar que se muestran los resultados de busqueda exitosos\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }


    @And("Se pulsar el boton de accion Editar Comunidad")
    public void sePulsarElBotonDeAccionEditarComunidad() {
        try {
            app.communities.clickOnButtonEditComm();
        } catch (Exception ex) {
            String messageFail = "Fallo el step al pulsar boton editar comunidad\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }

    @Then("se muestra redireccion exitosa al modulo de edicion de comunidad")
    public void seMuestraRedireccionExitosaAlModuloDeEdicionDeComunidad() {
        try {
            boolean val = app.communities.checkRedirectionEditCommPage();

            if (val){
                Map<String, String> data = context.getDataMap();
                data.put("StatusCommunity", this.initialStatusCommunity);
                GoogleSheetsService.updateOrSaveData(context.getSheetName(), data);
            }else {
                throw new Exception("No se redirecciono a la pantalla de editar comunidad " + context.getDataMap().get("NameCommunity"));
            }

        } catch (Exception ex) {
            String messageFail = "Fallo el step al validar que se muestra la redireccion a la pantalla d eeditar comunidad\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }

    @When("Se pulsar el boton de accion visualizar Comunidad")
    public void sePulsarElBotonDeAccionVisualizarComunidad() {
        try {
            app.communities.clickOnButtonViewComm();
        } catch (Exception ex) {
            String messageFail = "Fallo el step al pulsar boton ver comunidad\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }

    @Then("se muestra redireccion exitosa al modulo de ver comunidad")
    public void seMuestraRedireccionExitosaAlModuloDeVerComunidad() {
        try {
            Map<String, String> data = context.getDataMap();
            String idCommunity = data.get("IdCommunity");
            boolean val = app.communities.checkRedirectionViewCommPage(context.getDataMap().get("NameCommunity"), idCommunity);
            if (val){
                data.put("StatusCommunity", this.initialStatusCommunity);
                GoogleSheetsService.updateOrSaveData(context.getSheetName(), data);
            } else {
                throw new Exception("No se redirecciono correctamente a la pantalla de ver comunidad");
            }

        } catch (Exception ex) {
            String messageFail = "Fallo el step al redireccionar el modulo de ver comunidad\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }

    @When("Se pulsar el boton de accion impersonar administrador")
    public void sePulsarElBotonDeAccionImpersonarAdministrador() {
        try {
            app.communities.clickOnButtonPersonifyAdmin();
        } catch (Exception ex) {
            String messageFail = "Fallo el step al pulsar para impersonar un administrador\n El motivo: " + ex.getMessage();
            throw new ErrorStepException(messageFail, ex.getMessage());
        }
    }


}
