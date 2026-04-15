package condominiosDemo.pages;

import condominiosDemo.actions.BaseActions;
import org.openqa.selenium.WebDriver;
import static condominiosDemo.actions.GeneralLocators.HomePageLocators.*;

public class Home extends BaseActions {

    /**
     * Constructor de BaseActions.
     *
     * @param driver Instancia activa de WebDriver.
     */
    public Home(WebDriver driver) {

        super(driver);
    }

    public void goIntoModuleCreateNormalCommunity(){
        hoverOnElementBySelector(locatorMenuSideBar, 5);
        clickOnElementBySelector(dropdownNewCommunity, 5);
        clickOnElementBySelector(optionCreateNormalCommunity, 5);

    }

    public void goIntoModuleCommunities(){
        hoverOnElementBySelector(locatorMenuSideBar, 5);
        clickOnElementBySelector(optionCommunitiesSideBar, 5);

    }

//    public void goIntoModuleSideBar()
}
