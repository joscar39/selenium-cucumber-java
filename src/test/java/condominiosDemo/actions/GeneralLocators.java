package condominiosDemo.actions;

import condominiosDemo.support.PropertiesManager;
import condominiosDemo.utils.Environment;
import org.openqa.selenium.By;

public class GeneralLocators {

    public static class CommonsLocators {

        public static final By saveBtn = By.cssSelector("input[type='submit']");

        public static final By permissionDisable = By.xpath("//a[contains(@href, '/self_granted_permissions')][.//div[contains(@class, 'red')]]");
        public static final By permissionEnable = By.xpath("//a[.//div[contains(@class, 'green')]]");

        // Para localizadores dinámicos (los que tienen {})
        public static By textInScreen(String text) {
            return By.xpath(String.format("//*[contains(text(), '%s')]", text));
        }
    }

    public static class LoginPageLocators {
        public static final String LOGIN_URL = PropertiesManager.get("condominiosDemo").replace("env", Environment.get());
        public static final String checkLoginSuperAdmin = "ADMIN DASHBOARD";
        public static final By emailBox = By.id("email");
        public static final By passwordBox = By.id("password");
        public static final By loginBtn = By.cssSelector("button[type='submit']");
//        public static final By securityCode = By.id("mfa_code");
//        public static final By adminDashBoardTitle = By.xpath("//h1[contains(text(), 'ADMIN DASHBOARD')]");
//        public static final By acceptConditionsBtn = By.cssSelector("a[href='/usuarios/aceptar_condiciones']");
//        public static final By remindMeLaterBtn = By.cssSelector("a[href='/inicio/index?not_now=true']");
//        public static final By closeModalBtn = By.cssSelector("button[class='close'][type='button']");
//        public static final By closeLibraryModalBtn = By.cssSelector("a[class='shepherd-cancel-link']");
//        public static final By closeDiscoveryModalBtn = By.cssSelector("button[class='btn-close close']");

//        public static final By communityWallTitle = By.xpath("//div[contains(text(), 'Muro de la Comunidad')]");
        public static final By checkPageTermAndConditions = By.xpath("//h1[contains(text(), 'Términos y Condiciones')]");
        public static final By btnAcceptTermConditions = By.xpath("//a[@href='/usuarios/aceptar_condiciones'][.//div[contains(text(), 'He leído y acepto')]]");
        public static final By checkMyCommunitiesPage = By.xpath("//div[contains(text(), 'Comunidades que administro')]");
        public static final By inputNewPassword = By.id("new_password");
        public static final By inputConfimNewPass = By.id("confirm_password");
        public static final By btnChangePassword = By.id("submit-button");
    }

    public static class HomePageLocators{
        public static final By locatorMenuSideBar = By.id("superadmin-sidebar");
        public static final By optionCommunitiesSideBar = By.id("superadmin-communities");
//        public static final By optionSupportSideBar = By.id("superadmin-support");
//        public static final By optionDeactivationSideBar  = By.id("superadmin-deactivation");
        public static final By dropdownNewCommunity = By.xpath("//div[text()='Nueva comunidad']/parent::div[@class='navlink-dropdown']");
        public static final By optionCreateNormalCommunity = By.id("new-community-normal");
//        public static final By optionCreateAutoinstallCommunity = By.id("new-community-autoinstall");
//        public static final By optionCloseCommonExpenses = By.id("superadmin-closing-common-expenses");
//        public static final By optionUsers = By.id("superadmin-users");
//        public static final By optionInnerBilling = By.id("inner-billing-invoice");
//        public static final By optionInnerBillingIndicators = By.id("inner-billing-indicators");
//        public static final By optionPaymentSupport = By.id("superadmin-payment-support");
//        public static final By optionScrapper = By.id("superadmin-scraper");
//        public static final By optionHolidays = By.id("superadmin-holidays");
//        public static final By optionPromotionsCampaigns = By.id("superadmin-promotion-campaigns");
//        public static final By optionMessageCenter = By.id("inner-messaging-center-news");
//        public static final By optionCenterMessagings = By.id("inner-messaging-center-messagings");
//        public static final By optionAcademy = By.id("superadmin-academy");
//        public static final By optionBasePackages = By.id("superadmin-base-packages");
//        public static final By optionRouteUsers = By.id("superadmin-route-users");
//        public static final By optionSuperAdminPermissions = By.id("superadmin-superadmin-permissions");
//        public static final By optionFileVectorization = By.id("superadmin-file-vectorization");
//        public static final By  = By.id("");
//        public static final By  = By.id("");


    }


    public static class CreateNewCommunityLocators{

//        public static final By firstStepTitle = By.xpath("//h1[contains(text(), 'Paso 1: Configura los datos básicos de la comunidad')]");
        public static final By communityName = By.id("community_name");
        public static final By communityIdRutOrRfc = By.id("community_identifications_attributes_0_identity");
        public static final By communityBankName = By.id("community_bank");
        public static final By initialBalanceAccountBankCommunity = By.id("transaction_value");
//        public static final By initialBalanceAccountBankCommunity = By.xpath("//input[@id='transaction_value']");
        public static final By publicAdministrationName = By.id("community_contact_name");
        public static final By publicAdministrationEmail = By.id("community_contact_email");
        public static final By communityContactPhone = By.id("community_contact_phone");
        public static final By communityRealCheck = By.cssSelector("input[id='community_count_csm']");
        public static final By selectCommunityCountry = By.id("community_country_code");
        public static final By sectionAddress = By.xpath("//h1[contains(text(), 'Dirección')]");
        public static final By communityCity = By.cssSelector("input[id='autocomplete']");
        public static final By postalCodeMx = By.id("community_mx_company_attributes_postal_code");
        public static final By checkWithControlCommunity = By.id("period_control_yes");
        public static final By checkWithoutControlCommunity = By.id("period_control_no");
        public static final By sectionFundAndExpiration = By.xpath("//h1[contains(text(), 'Fondo de reserva y expiración')]");
        public static final By expirationCommonExpensesDays= By.id("community_expiration_day");
        public static final By percentageFundReserve = By.id("community_reserve_fund");
        public static final By initialFundReservePrice = By.id("community_initial_price");
        public static final By initialFundOperationalPrice = By.id("community_operational_fund_initial_price");
        public static final By sectionFine = By.xpath("//h1[contains(text(), 'Multa')]");
        public static final By selectTypeFine = By.id("community_community_interests_attributes_0_price_type");
        public static final By inputAmountFine = By.id("community_community_interests_attributes_0_price");
        public static final By selectTypeCurrencyByFine = By.id("community_community_interests_attributes_0_currency_id");
        public static final By selectTypePeriodicityFine = By.id("community_community_interests_attributes_0_fixed_daily_interest");
        public static final By inputMinAmountDebt = By.id("community_community_interests_attributes_0_minimun_debt");
        public static final By inputInterestsPercentage = By.id("community_community_interests_attributes_0_amount");
        public static final By selectTypeInterest = By.id("community_community_interests_attributes_0_rate_type");
        public static final By selectTypeInterestCompound= By.id("community_community_interests_attributes_0_compound");
        public static final By selectInterestCalculateAmount = By.id("community_community_interests_attributes_0_only_common_expenses");
        public static final By confirmationMsgTitle = By.xpath("//span[contains(text(), 'Queda un solo paso para tener la comunidad andando!')]");
        public static final By secondStepTitle = By.xpath("//h1[contains(text(), 'Paso 2: Configurar los datos de facturación')]");
        public static final By checkDefaultFiscalValueMx = By.xpath("//div[@data-accounts--form-target='defaultFiscalValuesCheckbox']//input[@type='checkbox']");
        public static final By checkPrincipalContact = By.id("account_account_contacts_attributes_0__destroy");
        public static final By inputEmailContactCommunity = By.id("account_account_contacts_attributes_0_email");
//        public static final By communityAdminCheckbox = By.cssSelector("input[id='account_account_contacts_attributes_2__destroy']");
        public static final By communityCreatedTitle = By.xpath("//span[contains(text(), 'La cuenta fue creada!')]");
        public static final By communityIdCreated = By.xpath("//div[@class='header-box-title' and starts-with(normalize-space(), 'Comunidad:')]");
//        public static final By communityDataSelect = By.cssSelector("button[id='cf-selector-button']");




    }
    public static class CommunitiesLocators{

        public static final By checkCommunitiesPage = By.xpath("//h1[contains(text(), 'Listado de comunidades en funcionamiento')]");
        public static final By inputSearchCommunity = By.id("search");
        public static final By selectStatusCommunity = By.id("status");
        public static final By selectFuntionalityCommunity = By.id("demo");
        public static final By selectCommunityIsReal = By.id("count_csm");
        public static final String checkRedirectionEditComm = "Ajustes de la comunidad";
        /**
         * Crea un localizador dinámico para verificar la redireccion correcta a la pantalla de ver comunidad.
         * @param name Nombre de la comunidad
         * @param id id de la comunidad.
         * @return By localizador.
         */
        public static By checkRedirectionViewComm(String name, String id) {
            return By.xpath(String.format("//div[@class='header-box-title' and contains(text(), 'Comunidad: %s, #%s')]", name, id));
        }
//        public static final By selectCountryCommunity = By.id("country");
//        public static final By inputCommune = By.id("comuna");
        public static final By searchBtn = By.cssSelector("input[type='submit']");
        public static final By actionImperAdmin = By.xpath("//div[@data-original-title='Ingresar como administrador']");
//        public static final By actionImperSuperAdmin = By.xpath("//div[@data-original-title='Ingresar como superadmin']");
        public static final By actionSeeComm = By.xpath("//div[@data-original-title='Ver']");
//        public static final By actionStateAccountComm = By.xpath("//div[@data-original-title='Estado de cuenta']");
//        public static final By actionAccountComm = By.xpath("//div[@data-original-title='Cuenta']");
        public static final By actionEditComm = By.xpath("//div[@data-original-title='Editar']");
//        public static final By actionImperAdminDeactivate = By.xpath("//div[@data-original-title='Sin administrador']");
        public static final By actionDeactivateComm = By.xpath("//div[@data-original-title='Desactivar']");
//        public static final By actionDeactivateCommOff = By.xpath("//div[@data-original-title='Ya se programó la desactivación para esta comunidad']");




    }

    public static class EditCommunityLocator{

        public static final By avatarCommunity = By.id("community_avatar");
        public static final By sectionAdministrationEdit = By.id("administration_title");
        public static final By avatarAdministration = By.id("community_company_image");

        public static final By accountBankNameInternational = By.id("community_bank");

        public static final By accountBankNumberInternational = By.id("community_bank_account_number");
        public static final By selectCurrencyBankInternational = By.id("community_currency_code");
        public static final By selectBankTypeCl = By.xpath("(//button[@id='cf-selector-button'])[2]");
        // XPath inmutable que apunta a los nombres dentro del dropdown
        public static final By locNameBanksAvailable = By.xpath("//div[@class='option-name']");

        /**
         * Crea un localizador dinámico para una opción del dropdown basada en su nombre.
         * @param text Nombre visible de la opción.
         * @return By localizador.
         */
        public static By selectOptionDropdownBanksAccount(String text) {
            return By.xpath(String.format("//div[@class='option-name' and normalize-space()='%s']", text));
        }

        public static final String sectionDataBankEdit = "Datos bancarios";
        public static final By radioBtnTypeCurrent= By.id("bank_account_account_type_checking");
        public static final By radioBtnTypeSaving = By.id("bank_account_account_type_savings");
        public static final By radioBtnTypeAccountRut = By.id("bank_account_account_type_sight");
        public static final By accountBankNumber= By.id("bank_account_number");
        public static final By accountRutOwner = By.id("bank_account_holder_rut");
        public static final By accountNameOwner = By.id("bank_account_holder_name");
        public static final By accountEmailOwner= By.id("bank_account_email");
        public static final String checkTextUpdateCommunity = "Comunidad actualizada correctamente";
    }

    public static class ViewCommunityLocators{
        public static final By sectionAdministrator = By.xpath("//h1[contains(text(), 'Administrador')]");
        public static final String checkAdminAssigned = "Administrador ingresado";
        public static final By nameAdminAssigned = By.id("user_first_name");
        public static final By lastnameAdminAssigned = By.id("user_last_name");
        public static final By emailAdminAssigned = By.id("user_email");
        public static final By phoneAdminAssigned = By.id("user_phone");
        public static final By passAdminAssigned = By.id("user_password");
        public static final By confirmPassAdminAssigned = By.id("user_password_confirmation");
        public static final By btnAssignAdmin  = By.id("submit_button");

    }

    public static class AdminPanelLocators{

        /**
         * Crea un localizador dinámico para verificar la redireccion correcta a la pantalla de ver comunidad.
         * @param communityName Nombre de la comunidad
         * @return By localizador.
         */
        public static By cardCommByCard(String communityName) {
            return By.xpath(String.format("//div[@class='comm-name' and text()='%s']/ancestor::div[contains(@class, 'commImg')]", communityName));
        }
        /**
         * Localiza el párrafo que contiene el número del RUT.
         * @param rutNumber El número de RUT completo (ej: "15.234.066-4").
         * @return By localizador.
         */
        public static By locRutSpecific(String rutNumber) {
            return By.xpath("//h6[text()='RUT']/following-sibling::p[text()='" + rutNumber + "']");
        }
        public static final By locatorMenuSideBar = By.id("admin-sidebar");
        public static final By itemCommunityConnected = By.xpath("//div[contains(@class, 'item-name') and normalize-space()='Comunidad conectada']");
        public static final By optResidents = By.id("admin-property-user");
        public static final By modalJourneyInitial = By.xpath("//p[@class='admin-tour-title' and contains(text(), '¡Bienvenido a condominiosDemo!')]");
        public static final By closeModalJourneyInitial = By.xpath("//button[@class='close' and @type='button']");
        public static final By btnStarTour = By.id("btn-start-admin-tour");
        public static final By sectionTaskPending = By.id("area-pending-tasks");
        public static final By pendingItemCreateProperties = By.xpath("//div[@class='pending-item' and contains(text(),'Crea las unidades de la comunidad')]");
        public static final By redirectionTaskPending = By.id("redirect-button");
        public static final By checkImportationPropertiesPage = By.xpath("//div[@class='header-box-title' and contains(text(),'Importador de Unidades')]");
        public static final By inputUploadTemplateExcel = By.id("file");
        public static final By btnUploadFile = By.xpath("//button[@type='submit' and @name='button']");
        public static final By checkFileUploaded = By.xpath("//div[@class='file-name']");
        public static final By statusLastFileUploading = By.xpath("(//div[@data-excel-uploads--has-processing])[1]");


    }

    public static class AdminResidentsLocators{
        public static final By checkResidentsPage = By.xpath("//div[@class='header-box-title' and contains(text(), 'Residentes')]");
        /**
         * Localiza las unidades visibles en residentes page
         * @param nameProperty nombre de la propiedad o unidad.
         * @return By localizador.
         */
        public static By locNameProperty(String nameProperty) {
            return By.xpath(String.format("//a[@data-original-title='Ver detalle de la unidad' and contains(text(),'%s')]", nameProperty));
        }

    }

}
