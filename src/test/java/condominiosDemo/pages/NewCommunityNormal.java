package condominiosDemo.pages;

import condominiosDemo.actions.BaseActions;
import condominiosDemo.utils.CountryMapper;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import static condominiosDemo.actions.GeneralLocators.CreateNewCommunityLocators.*;
import static condominiosDemo.utils.CommunityGenerator.*;
import static condominiosDemo.utils.RutGenerator.*;
import static condominiosDemo.utils.RfcGenerator.*;
import static condominiosDemo.utils.BankDataGenerator.*;
import static condominiosDemo.utils.AdministratorPublicGenerator.*;
import static condominiosDemo.utils.AddressGeneratorByCountry.*;
import static condominiosDemo.actions.GeneralLocators.CommonsLocators.*;

public class NewCommunityNormal extends BaseActions {

    /**
     * Constructor de BaseActions.
     *
     * @param driver Instancia activa de WebDriver.
     */
    public NewCommunityNormal(WebDriver driver) {

        super(driver);
    }

    public Map<String, String> fillFormNormalCommunityRegistration(String typeCommunity, String regionCommunity) {
        Random random = new Random();
        Map<String, String> dataMap = new LinkedHashMap<>(); // Usamos LinkedHashMap para mantener el orden de las columnas

        // 1. OPTIMIZACIÓN DE VARIABLES DE CONTROL
        boolean isMexico = regionCommunity.equalsIgnoreCase("mexico");
        boolean isChile = regionCommunity.equalsIgnoreCase("chile");

        // --- BLOQUE DE GENERACIÓN DE DATOS (DICCIONARIO) ---
        String nameCommunity = communityNameGenerator(regionCommunity, typeCommunity);
        String rutOrRfc = isChile ? generateRandomRut(true) : (isMexico ? generateRandomRfc() : "N/A");
        String bankName = getRandomBankName();
        int balanceBank = (int)(Math.random() * (1000000 - 1000 + 1) + 1000);
        String adminName = publicNameGenerator();
        String adminEmail = corporateEmailGenerator(adminName);
        int cellphone = (int)(Math.random() * (99999999 - 10000000 + 1) + 10000000);
        String address = generateAddress(regionCommunity);
        String zipCode = isMexico ? createZipCodeMexico(address) : "N/A";
        boolean isReal = random.nextBoolean();

        // Datos Financieros
        int expDay = (int)(Math.random() * 30 + 1);
        int percReserve = (int)(Math.random() * 100 + 1);
        int initReserve = (int)(Math.random() * 9000000 + 10000);
        int initOperational = (int)(Math.random() * 9000000 + 10000);

        // Obteniendo valores de los select una sola vez

        String typeFine = getSelectOptionsText(selectTypeFine, 3).get(random.nextInt(getSelectOptionsText(selectTypeFine, 3).size()));
        int amountFine;
        if (typeFine.equalsIgnoreCase("Porcentual")) {
            // Si es porcentual, valor entre 1 y 10
            amountFine = random.nextInt(10) + 1;
        } else {
            // Si es monto fijo u otro, valor entre 10000 y 50000
            amountFine = (int)(Math.random() * 40000 + 10000);
        }
        String currencyFine = getSelectOptionsText(selectTypeCurrencyByFine, 3).get(random.nextInt(getSelectOptionsText(selectTypeCurrencyByFine, 3).size()));
        String freqFine = getSelectOptionsText(selectTypePeriodicityFine, 3).get(random.nextInt(getSelectOptionsText(selectTypePeriodicityFine, 3).size()));
        int minDebt = (int)(Math.random() * 50000 + 50000);
        int intRate = (int)(Math.random() * 10 + 1);
        String typeRate = getSelectOptionsText(selectTypeInterest, 3).get(random.nextInt(getSelectOptionsText(selectTypeInterest, 3).size()));
        String priorInt = getSelectOptionsText(selectTypeInterestCompound, 3).get(random.nextInt(getSelectOptionsText(selectTypeInterestCompound, 3).size()));
        String calcIntAmount = getSelectOptionsText(selectInterestCalculateAmount, 3).get(random.nextInt(getSelectOptionsText(selectInterestCalculateAmount, 3).size()));

        String communityEmail = communityEmailGenerator(nameCommunity);

        // --- ALMACENAMIENTO EN EL DICCIONARIO (ORDEN SEGÚN DATABASE) ---
        dataMap.put("NameCommunity", nameCommunity);
        dataMap.put("EmailCommunity", communityEmail);
        dataMap.put("CommunityIDNumber(RUTorRFC)", rutOrRfc);
        dataMap.put("CommunityBank", bankName);
        dataMap.put("InitialBalanceBankAccount", String.valueOf(balanceBank));
        dataMap.put("NamePublicAdministration", adminName);
        dataMap.put("EmailPublicAdministration", adminEmail);
        dataMap.put("PasswordAdmin", "Aa123456.");
        dataMap.put("CellphoneContact", String.valueOf(cellphone));
        dataMap.put("CommunityIsReal", String.valueOf(isReal));
        dataMap.put("Country", regionCommunity);
        dataMap.put("Address", address);
        dataMap.put("ZipCode", zipCode);
        dataMap.put("TypeCommunity", typeCommunity);
        dataMap.put("ExpirationDayCommosExpenses", String.valueOf(expDay));
        dataMap.put("PercentageFoundReserve", String.valueOf(percReserve));
        dataMap.put("InitialBalanceFoundReserve", String.valueOf(initReserve));
        dataMap.put("InitialBalanceFoundOperational", String.valueOf(initOperational));
        dataMap.put("TypeFine", typeFine);
        dataMap.put("MountFine", String.valueOf(amountFine));
        dataMap.put("TypeCurrencyFine", currencyFine);
        dataMap.put("FrequencyCollecteFine", freqFine);
        dataMap.put("MinimunDebtForFine", String.valueOf(minDebt));
        dataMap.put("InterestRate", String.valueOf(intRate));
        dataMap.put("TypeRate", typeRate);
        dataMap.put("ConsiderPriorInterests", priorInt);
        dataMap.put("AmountCalculatingInterest", calcIntAmount);

        // ==========================================
        // SECCIÓN 1: PRIMERA PÁGINA DEL FORMULARIO
        // ==========================================
        clickOnElementBySelector(selectCommunityCountry, 3);
        selectOptionInList(selectCommunityCountry, "value", CountryMapper.getCode(regionCommunity), 3);
        sendTextBySelector(communityName, nameCommunity, 3);
        sendTextBySelector(communityIdRutOrRfc, rutOrRfc, 3);
        sendTextBySelector(communityBankName, bankName, 3);
        sendTextBySelector(initialBalanceAccountBankCommunity, balanceBank, 3);
        sendTextBySelector(publicAdministrationName, adminName, 3);
        sendTextBySelector(publicAdministrationEmail, adminEmail, 3);
        sendTextBySelector(communityContactPhone, cellphone, 3);


        // Lógica dinámica del Checkbox "Comunidad Real"
        boolean isSelected = isElementSelectedBySelector(communityRealCheck, 3);

        if (isReal && !isSelected) {
            // Si debe ser real y no está marcado -> Lo marcamos
            clickOnElementBySelector(communityRealCheck, 3);
        } else if (!isReal && isSelected) {
            // Si NO debe ser real y está marcado -> Lo desmarcamos
            clickOnElementBySelector(communityRealCheck, 3);
        }

        scrollToElementIsVisibility(sectionAddress, 3); // scroll en direccion
        sendTextBySelector(communityCity, address, 3);
        sendKeyboardActions(communityCity, 3, Keys.ARROW_DOWN, Keys.ENTER);


        if (isMexico) {
            sendTextBySelector(postalCodeMx, zipCode, 3);
            screenshot("CP ingresado en formulario México");
        }

        // Lógica de tipo de comunidad (Control vs Sin Control)
        if (typeCommunity.equalsIgnoreCase("cc") && isElementSelectedBySelector(checkWithoutControlCommunity, 3)) {
            clickOnElementBySelector(checkWithControlCommunity, 3);
        } else if (typeCommunity.equalsIgnoreCase("sc") && isElementSelectedBySelector(checkWithControlCommunity, 3)) {
            clickOnElementBySelector(checkWithoutControlCommunity, 3);
        }

        // Inputs Financieros
        scrollToElementIsVisibility(sectionFundAndExpiration, 3); //scroll en fondo de reserva y expiracion
        sendTextBySelector(expirationCommonExpensesDays, expDay, 3);
        sendTextBySelector(percentageFundReserve, percReserve, 3);
        sendTextBySelector(initialFundReservePrice, initReserve, 3);
        sendTextBySelector(initialFundOperationalPrice, initOperational, 3);

        // Selects Financieros
        scrollToElementIsVisibility(sectionFine, 3); // scroll en multa
        selectOptionInList(selectTypeFine, "text", typeFine, 3);
        sendTextBySelector(inputAmountFine, amountFine, 3);
        selectOptionInList(selectTypeCurrencyByFine, "text", currencyFine, 3);
        // CONDICIONAL: Solo se ejecuta si NO es Porcentual
        if (!typeFine.equalsIgnoreCase("Porcentual")) {
            selectOptionInList(selectTypePeriodicityFine, "text", freqFine, 3);
        }
        sendTextBySelector(inputMinAmountDebt, minDebt, 3);
        sendTextBySelector(inputInterestsPercentage, intRate, 3);
        selectOptionInList(selectTypeInterest, "text", typeRate, 3);
        selectOptionInList(selectTypeInterestCompound, "text", priorInt, 3);
        selectOptionInList(selectInterestCalculateAmount, "text", calcIntAmount, 3);

        clickOnElementBySelector(saveBtn, 3);

        // ==========================================
        // SECCIÓN 2: TRANSICIÓN Y SEGUNDA PÁGINA
        // ==========================================
        if (findElementIsDisplayed(confirmationMsgTitle, 10) && findElementIsDisplayed(secondStepTitle, 10)) {
            screenshot("Transicion exitosa a la segunda parte");
        } else {
            throw new RuntimeException("Error: No se detectó el cambio a la segunda parte del formulario.");
        }

        if (isMexico) {
            clickOnElementBySelector(checkDefaultFiscalValueMx, 3);
        }

        clickOnElementBySelector(checkPrincipalContact, 3);
        sendTextBySelector(inputEmailContactCommunity, communityEmail, 3);
        clickOnElementBySelector(saveBtn, 3);

        return dataMap; // Retornamos todos los datos generados para tu BD
    }


    public boolean checkCommunityCreatedSuccess(){
        boolean val = findElementIsDisplayed(communityCreatedTitle, 3);
        if (val){
            screenshot("Se registro la comunidad exitosamente");
            return true;
        }else {
            // Si es false, lanzamos el error para detener la prueba
            throw new RuntimeException("No se visualizó la comunidad registrada");
        }
    }

    public String checkCommunityIdCreatedSuccess() {
        boolean val = findElementIsDisplayed(communityIdCreated, 3);
        if (val) {
            screenshot("Se mostro el ID y el titulo de comunidad creada correctamente");
            String elementText = getTextOnElement(communityIdCreated, 3);
            String[] splitText = elementText.split("#");
            if (splitText.length > 1) {
                return splitText[1];
            }

        } else {
            throw new RuntimeException("No se visualizo ningun ID o nombre de la comunidad registrada");
        }
        return null;
    }

}
