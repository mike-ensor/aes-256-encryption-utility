package com.acquitygroup.encryption;

import com.google.common.io.BaseEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.cert.CertificateException;

public class AESCipher {

    private static final String ALGORITHM_AES256 = "AES/CBC/PKCS5Padding";
    // ECP, default
//    private static final String ALGORITHM_AES256 = "AES";
    private static final byte[] INITIAL_IV = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static final Logger LOG = LoggerFactory.getLogger(AESCipher.class);

    private final SecretKeySpec secretKeySpec;
    private final Cipher cipher;
    private IvParameterSpec iv;
    private Object key;

    public AESCipher(Key key) {
        this(key.getEncoded());
    }

    public AESCipher(byte[] key) {
        try {
            this.secretKeySpec = new SecretKeySpec(key, "AES");
            this.iv = new IvParameterSpec(INITIAL_IV);
            this.cipher = Cipher.getInstance(ALGORITHM_AES256);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Takes message and encrypts with Key
     * @param message String
     * @return String Base64 encoded
     */
    public String getEncryptedMessage(String message) {
        try {
            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);

            byte[] encryptedTextBytes = cipher.doFinal(message.getBytes("UTF-8"));

            return BaseEncoding.base64().encode(encryptedTextBytes);
        } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Takes Base64 encoded String and decodes with provided key
     * @param message String encoded with Base64
     * @return String
     */
    public String getDecryptedMessage(String message) {
        try {
            Cipher cipher = getCipher(Cipher.DECRYPT_MODE);

            byte[] encryptedTextBytes = BaseEncoding.base64().decode(message);
            byte[] decryptedTextBytes = cipher.doFinal(encryptedTextBytes);

            return new String(decryptedTextBytes);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Base64 encoded version of key
     * @return String
     */
    public String getKey() {
        return BaseEncoding.base64().encode(secretKeySpec.getEncoded());
    }

    private Cipher getCipher(int encryptMode) throws InvalidKeyException, InvalidAlgorithmParameterException {
        cipher.init(encryptMode, getSecretKeySpec(), iv);
        return cipher;
    }

    private SecretKeySpec getSecretKeySpec() {
        return secretKeySpec;
    }
}
