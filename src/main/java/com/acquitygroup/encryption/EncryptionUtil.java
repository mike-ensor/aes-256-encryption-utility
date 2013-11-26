package com.acquitygroup.encryption;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * Unlimited JCE JDK7 http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html
 */
public class EncryptionUtil {

    private EncryptionUtil() {
    }

    public static EncryptionUtil getInstance() {
        return new EncryptionUtil();
    }

    public String encrypt(String message, AESCipher aesCipher) {
        return aesCipher.getEncryptedMessage(message);
    }

    public String decrypt(String message, AESCipher aesCipher) {
        return aesCipher.getDecryptedMessage(message);
    }

    private Key getKeyFromStore() {
        Key secureKey = null;
        try {
            KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
            java.io.FileInputStream fis = new java.io.FileInputStream("keyStoreName");
            store.load(fis, getPassword());
            fis.close();

            secureKey = store.getKey("paylessPrivateAesKey", getPassword());
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException | UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return secureKey;
    }

    private char[] getPassword() {
        return new char[]{'a', 'b', 'c', 'd', 'e'};
    }

}
