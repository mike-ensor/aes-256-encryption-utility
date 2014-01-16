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

        checkArgument(args.length > 0, "Usage: java -jar <pathToJar.jar> command");

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

        String command = args[0];
        String iv;
        AESCipher cipher;
        switch(command) {
            case "showKey":
                cipher = new AESCipher(keyFromKeyStore);
                showKey(cipher);
                break;
            case "encrypt":
                checkArgument(args.length==3, "Command encryption takes 2 arguments: Message to encrypt and IV in String format");
                LOG.debug("Encrypt({},{})", args[1], args[2]);

                iv = args[2];
                cipher = new AESCipher(keyFromKeyStore, iv.getBytes());

                encrypt(cipher, args[1]);
                break;
            case "decrypt":
                checkArgument(args.length==3, "Command decryption takes 2 arguments: Message to encrypt and IV in String format");
                LOG.debug("Decrypt({},{})", args[1], args[2]);

                iv = args[2];
                cipher = new AESCipher(keyFromKeyStore, iv.getBytes());

                decrypt(cipher, args[1]);
        }


    }

    private static void decrypt(AESCipher cipher, String message) {
        LOG.debug("Decrypting: " + message + " using IV:" + cipher.getIV());

        System.out.println("\n\nDecrypted Message\n=======================================");
        System.out.println(cipher.getDecryptedMessage(message));
        System.out.println("\n\n");
    }

    private static void encrypt(AESCipher cipher, String message) {
        LOG.debug("Encrypting: " + message + " using IV:" + cipher.getIV());

        System.out.println("\n\nEncrypted Message\n=======================================");
        System.out.println(cipher.getEncryptedMessage(message));
        System.out.println("\n\n");
    }

    private static void showKey(AESCipher cipher) {

        System.out.println("\n\nPrint SecretPrivateKey from JCEKS Keystore\n===========================================");
        System.out.println("Key (Base64 Encoded): " + cipher.getKey(KeyEncoding.BASE64));
        System.out.println("Key (Hex Encoded): " + cipher.getKey(KeyEncoding.HEX));
        System.out.println("Key (Base32 Encoded): " + cipher.getKey(KeyEncoding.BASE32));

    }
}
