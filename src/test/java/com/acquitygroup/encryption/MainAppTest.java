package com.acquitygroup.encryption;

import org.junit.Before;
import org.junit.Test;

public class MainAppTest {

    private String initialIV;

    @Before
    public void setup() {
        createSystemProperties();
        initialIV = "0000000000000000";
    }

    @Test
    public void showKey() {
        MainApp.main(createArguments("showKey"));
    }

    @Test
    public void encrypt() {
        MainApp.main(createArguments("encrypt", "'this is the message that should be decrypted'", initialIV));
    }

    @Test
    public void decrypt() {
        MainApp.main(createArguments("decrypt", "kFKgXtUKBWdZLgaSyx+RhS/ZbpiXxZqiHTcdRCruJu74fpZnapVbvugka/o9+2IE", initialIV));
    }

    @Test
    public void decryptWithBadIV() {
        MainApp.main(createArguments("decrypt", "kFKgXtUKBWdZLgaSyx+RhS/ZbpiXxZqiHTcdRCruJu74fpZnapVbvugka/o9+2IE", "1111111111111111"));
    }

    private String[] createArguments(String... args) {
        return args;
    }

    private void createSystemProperties() {
        System.setProperty("keystore","src/test/resources/aes-keystore.jck");
        System.setProperty("storepass","mystorepass");
        System.setProperty("alias","jceksaes");
        System.setProperty("keypass", "mykeypass");
    }
}
