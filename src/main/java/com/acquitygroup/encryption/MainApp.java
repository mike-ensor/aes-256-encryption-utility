package com.acquitygroup.encryption;

import com.google.common.io.BaseEncoding;
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
        AESCipher cipher;
        switch(command) {
            case "showKey":
                cipher = new AESCipher(keyFromKeyStore);
                showKey(cipher);
                break;
            case "encrypt":
                checkArgument(args.length==3, "Command encryption takes 2 arguments: Message to encrypt and IV in String format");
                cipher = new AESCipher(keyFromKeyStore);
                LOG.debug("Encrypt({},{})", args[1], args[2]);
                encrypt(cipher, args[1], args[2].getBytes());
                break;
            case "decrypt":
                checkArgument(args.length==3, "Command decryption takes 2 arguments: Message to encrypt and IV in String format");
                cipher = new AESCipher(keyFromKeyStore);
                LOG.debug("Decrypt({},{})", args[1], args[2]);
                decrypt(cipher, args[1], args[2].getBytes());

        }


    }

    private static void decrypt(AESCipher cipher, String message, byte[] iv) {
        LOG.debug("Decrypting: " + message + " using IV:" + BaseEncoding.base64().encode(iv));

        System.out.println("\n\nDecrypted Message\n=======================================");
        System.out.println(cipher.getDecryptedMessage(message));
        System.out.println("\n\n");
    }

    private static void encrypt(AESCipher cipher, String message, byte[] iv) {
        LOG.debug("Encrypting: " + message + " using IV:" + BaseEncoding.base64().encode(iv));

        System.out.println("\n\nEncrypted Message\n=======================================");
        System.out.println(cipher.getEncryptedMessage(message));
        System.out.println("\n\n");
    }

    private static void showKey(AESCipher cipher) {
        String cipherKey = cipher.getKey();

        System.out.println("\n\nPrint SecretPrivateKey from JCEKS Keystore\n===========================================");
        System.out.println("Key (Base64 Encoded): " + cipherKey);

    }
}
