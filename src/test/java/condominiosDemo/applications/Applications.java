package condominiosDemo.applications;

import condominiosDemo.pages.*;

import org.openqa.selenium.WebDriver;

public class Applications {
    public Login login;
    public Home home;
    public Communities communities;
    public EditCommunities editCommunities;
    public ViewCommunity viewCommunity;
    public NewCommunityNormal newCommunityNormal;
    public AdminPanel adminPanel;


    public Applications(WebDriver driver) {

        this.login = new Login(driver);
        this.home = new Home(driver);
        this.communities = new Communities(driver);
        this.editCommunities = new EditCommunities(driver);
        this.viewCommunity = new ViewCommunity(driver);
        this.newCommunityNormal = new NewCommunityNormal(driver);
        this.adminPanel = new AdminPanel(driver);
        // Aquí irás agregando cada página nueva que crees
    }
}