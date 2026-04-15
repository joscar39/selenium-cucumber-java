package condominiosDemo.utils;

import org.openqa.selenium.NotFoundException;

import java.util.Arrays;

public class Environment {

    private static final String[] env = {"admin", "app", "app2", "smoke", "saas", "saas2", "test1", "test2", "test3", "test4", "test5", "test6", "test7"};

    public static String get() {
        String envToUse = System.getProperty("env");
        if (!Arrays.asList(env).contains(envToUse)) {
            throw new NotFoundException("Ambiente: " + envToUse + " inválido.");
        }
        return envToUse;
    }
}
