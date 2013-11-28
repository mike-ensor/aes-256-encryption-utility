package com.acquitygroup.encryption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

public class MainApp {

    private static final String KEYSTORE = "keystore";
    private static final String STOREPASS = "storepass";
    private static final String ALIAS = "alias";
    private static final String KEYPASS = "keypass";

    private static final Logger LOG = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) {

        String keystoreFileLocation = System.getProperty(KEYSTORE);
        String storePass = System.getProperty(STOREPASS);

        String alias = System.getProperty(ALIAS);
        String keyPass = System.getProperty(KEYPASS);

        checkArgument(!isNullOrEmpty(keystoreFileLocation), "Please provide a 'keystore' file location.");
        checkArgument(!isNullOrEmpty(storePass), "Please provide a 'storepass' password for keystore");
        checkArgument(!isNullOrEmpty(alias), "Please provide an 'alias' for the specific key");
        checkArgument(!isNullOrEmpty(keyPass), "Please provide a 'keypass' for the specific key");


        LOG.debug("Keystore: {}\nStorePass: {}\nAlias: {}\nKeyPass: {}\n", keystoreFileLocation, storePass, alias, keyPass);


        Key keyFromKeyStore = KeystoreUtil.getKeyFromKeyStore(keystoreFileLocation, storePass, alias, keyPass);

        AESCipher cipher = new AESCipher(keyFromKeyStore);

        String cipherKey = cipher.getKey();

        System.out.println("\n\nPrint SecretPrivateKey from JCEKS Keystore\n===========================================");
        System.out.println("Key (Base64 Encoded): " + cipherKey);

    }
}
