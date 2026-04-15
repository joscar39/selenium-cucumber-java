package condominiosDemo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommunityNameBuilder {

    private static final String COMMUNITY_USER_NAME = CredentialsManager.getEmail().replace("@comunidadfeliz.cl", "");
    private static final String COMMUNITY_DATE_TIME = new SimpleDateFormat("ddMMyy - HH:mm").format(new Date());
    private static final String COMMUNITY_DATE = new SimpleDateFormat("ddMMyy").format(new Date());

    private static final String SC_COMMUNITY_NAME = "SC Prueba Selenium - " + COMMUNITY_USER_NAME + " - " + Environment.get() + " - " + COMMUNITY_DATE_TIME;

    private static final String CC_COMMUNITY_NAME = "CC Prueba Selenium - " + COMMUNITY_USER_NAME + " - " + Environment.get() + " - " + COMMUNITY_DATE_TIME;
    private static final String PARTIAL_COMMUNITY_NAME = CC_COMMUNITY_NAME.replace(COMMUNITY_DATE_TIME, COMMUNITY_DATE).replace("CC ", "");
    private static final String SHORT_COMMUNITY_NAME = CC_COMMUNITY_NAME.replace(COMMUNITY_DATE_TIME, "").replace("CC ", "");

    public static String getScCommunityName() {
        return SC_COMMUNITY_NAME;
    }

    public static String getCcCommunityName() {
        return CC_COMMUNITY_NAME;
    }

    public static String getPartialCommunityName() {
        return PARTIAL_COMMUNITY_NAME;
    }

    public static String getShortCommunityName() {
        return SHORT_COMMUNITY_NAME;
    }

    public static String getCommunityUserName(){
        return COMMUNITY_USER_NAME;
    }
}
