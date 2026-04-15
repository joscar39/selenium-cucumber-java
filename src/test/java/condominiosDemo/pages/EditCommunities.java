package condominiosDemo.pages;

import condominiosDemo.actions.BaseActions;
import condominiosDemo.utils.BankDataGenerator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import java.util.Map;
import java.util.Random;

import static condominiosDemo.actions.GeneralLocators.EditCommunityLocator.*;
import static condominiosDemo.actions.GeneralLocators.CommonsLocators.*;

public class EditCommunities  extends BaseActions {

    /**
     * Constructor de BaseActions.
     *
     * @param driver Instancia activa de WebDriver.
     */
    public EditCommunities(WebDriver driver) {
        super(driver);
    }

    public void ActiveTemporaryPermissions(){
        boolean val = findElementIsDisplayed(permissionDisable, 3);
        if (val){
            screenshot("Los permisos temporales no estan habilitados, se procede a habilitarlos");
            clickOnElementBySelector(permissionDisable, 3);
        }else {
            boolean val2 = findElementIsDisplayed(permissionEnable, 3);
            if (val2){
                screenshot("Permisos temporales ya fueron otorgados");
            } else {
                throw new RuntimeException("No se visualizo el candado para permisos temporales como superadmin en la vista");
            }
        }
    }

    public void InsertImageOfCommunity(String path){
        uploadFile(avatarCommunity, path, 3);
    }
    public void InsertImageOfAdministration(String path){
        scrollToElementIsVisibility(sectionAdministrationEdit, 3);
        uploadFile(avatarAdministration, path, 3);
    }

    public Map<String, String> fillFormDataBank(Map<String, String> dataMap) {
        timeWait(3); // Esperar a que renderice el apartado de datso bancarios ya que se muestra mal por defecto de la web para comunidades CC
        Random random = new Random();
        scrollToElementIsVisibility(textInScreen(sectionDataBankEdit), 3);

        if (dataMap.get("Country").equalsIgnoreCase("chile")) {

            clickOnElementBySelector(selectBankTypeCl, 3); // abrir dropdown

            // Captura dinámica de opciones reales del navegador
            java.util.List<WebElement> elementOptions = findListOfElementsBySelector(locNameBanksAvailable);
            java.util.List<String> bankOptions = new java.util.ArrayList<>();
            for (WebElement element : elementOptions) {
                String text = element.getText().trim();
                if (!text.isEmpty() && !text.equalsIgnoreCase("Seleccionar banco")) {
                    bankOptions.add(text);
                    System.out.println("Banco detectado en dropdown: " + text);
                }
            }

            // Selección aleatoria y clic usando el localizador dinámico
            String bankCommunity = bankOptions.get(random.nextInt(bankOptions.size()));
            clickOnElementBySelector(selectOptionDropdownBanksAccount(bankCommunity), 3);

            // Sincronización con el Diccionario para la BD
            dataMap.put("CommunityBank", bankCommunity);

            int selection = random.nextInt(3) + 1;
            String nameOption;

            switch (selection) {
                case 1:
                    nameOption = "Cuenta corriente";
                    clickOnElementBySelector(radioBtnTypeCurrent, 3);
                    break;
                case 2:
                    nameOption = "Cuenta de ahorro";
                    clickOnElementBySelector(radioBtnTypeSaving, 3);
                    break;
                case 3:
                    nameOption = "Cuenta vista/RUT";
                    clickOnElementBySelector(radioBtnTypeAccountRut, 3);
                    break;
                default:
                    throw new RuntimeException("Número aleatorio fuera de rango");
            }

            dataMap.put("TypeBankAccount", nameOption);

            // Generación de cuenta y sincronización con el Mapa
            String newNumberBank = BankDataGenerator.generateAccountNumber(10);
            dataMap.put("BankNumberAccount", newNumberBank);

            sendTextBySelector(accountBankNumber, newNumberBank, 3);
            sendTextBySelector(accountRutOwner, dataMap.get("CommunityIDNumber(RUTorRFC)"), 3);
            sendTextBySelector(accountNameOwner, dataMap.get("NameCommunity"), 3);
            sendTextBySelector(accountEmailOwner, dataMap.get("EmailCommunity"), 3);

        } else {
            // Bloque Internacional
            String newBankName = "Banco editado manualmente";
            dataMap.put("CommunityBank", newBankName);
            sendTextBySelector(accountBankNameInternational, newBankName, 3);

            String newBankNumber = BankDataGenerator.generateAccountNumber(10);
            dataMap.put("BankNumberAccount", newBankNumber);
            sendTextBySelector(accountBankNumberInternational, newBankNumber, 3);

            java.util.List<String> currencyOptions = getSelectOptionsText(selectCurrencyBankInternational, 3);
            String typeCurrencyBankAccount = currencyOptions.get(random.nextInt(currencyOptions.size()));

            selectOptionInList(selectCurrencyBankInternational, "text", typeCurrencyBankAccount, 3);
            dataMap.put("TypeCurrencyAccount", typeCurrencyBankAccount);
        }

        clickOnElementBySelector(saveBtn, 3);

        // Opcional: Agregar una pequeña espera aquí para confirmar que el guardado terminó
        return dataMap;
    }

    public boolean checkUpdateCommunitySuccess(){
        boolean val = findElementIsDisplayed(textInScreen(checkTextUpdateCommunity), 3);
        if (val){
            screenshot("Se actualizo la comunidad exitosamente");
            return true;
        } else {
            // Si es false, lanzamos el error para detener la prueba
            throw new RuntimeException("No se logro hacer login como superadmin");
        }
    }
}
