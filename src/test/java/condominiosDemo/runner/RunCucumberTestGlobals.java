package condominiosDemo.runner;


import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features") // Apuntamos solo a la subcarpeta del feature
@ConfigurationParameter(
        key = Constants.GLUE_PROPERTY_NAME,
        // CAMBIO CLAVE: Quitamos "condominiosDemo.steps" (que es muy general)
        // y apuntamos solo a los steps del creator y específicamente a donde están tus Hooks.
        value = "condominiosDemo.steps, condominiosDemo.support"
)
@ConfigurationParameter(
        key = Constants.PLUGIN_PROPERTY_NAME,
        value = "pretty, io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
)
public class RunCucumberTestGlobals {
}
