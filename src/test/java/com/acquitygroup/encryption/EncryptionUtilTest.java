package com.acquitygroup.encryption;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class EncryptionUtilTest {

    private static final Logger LOG = LoggerFactory.getLogger(EncryptionUtilTest.class);

    @Test
    public void encryptMessage() throws UnsupportedEncodingException {

        String key = "770A8A65DA156D24EE2A093277530142";
        AESCipher cipher = new AESCipher(key.getBytes("UTF-8"));

        String encryptedMessage = cipher.getEncryptedMessage("this is message");
        LOG.debug("Message is: {}", encryptedMessage);

        assertThat(encryptedMessage, is(notNullValue()));
        assertThat(encryptedMessage, is(not("this is message")));
    }

    @Test
    public void decryptMessage() throws UnsupportedEncodingException {

        String key = "770A8A65DA156D24EE2A093277530142";
        AESCipher cipher = new AESCipher(key.getBytes("UTF-8"));

        String messageToEncrypt = "this is the secret message I want to encode";

        String encryptedMessage = cipher.getEncryptedMessage(messageToEncrypt);
        String decryptedMessage = cipher.getDecryptedMessage(encryptedMessage);

        LOG.debug("Original Message: {}, Encrypted Message: {}, Decrypted Message: {}", messageToEncrypt, encryptedMessage, decryptedMessage);
        assertThat(decryptedMessage, is(messageToEncrypt));
    }

    @Test
    public void encryptMessageFromKeystore() throws UnsupportedEncodingException {

        Key key = KeystoreUtil.getKeyFromKeyStore("src/test/resources/aes-keystore.jck", "mystorepass", "jceksaes", "mykeypass");

        AESCipher cipher = new AESCipher(key);

        String encryptedMessage = cipher.getEncryptedMessage("this is message");
        LOG.debug("Message is: {}", encryptedMessage);

        assertThat(encryptedMessage, is(notNullValue()));
        assertThat(encryptedMessage, is(not("this is message")));
    }

    @Test
    public void decryptMessageFromKeystore() throws UnsupportedEncodingException {

        Key key = KeystoreUtil.getKeyFromKeyStore("src/test/resources/aes-keystore.jck", "mystorepass", "jceksaes", "mykeypass");
        AESCipher cipher = new AESCipher(key);

        String messageToEncrypt = "this is the secret message I want to encode";

        String encryptedMessage = cipher.getEncryptedMessage(messageToEncrypt);
        String decryptedMessage = cipher.getDecryptedMessage(encryptedMessage);

        LOG.debug("Original Message: {}, Encrypted Message: {}, Decrypted Message: {}", messageToEncrypt, encryptedMessage, decryptedMessage);
        assertThat(decryptedMessage, is(messageToEncrypt));
    }

    @Test
    public void encryptMessageFromKeystoreWithIv() throws UnsupportedEncodingException {

        Key key = KeystoreUtil.getKeyFromKeyStore("src/test/resources/aes-keystore.jck", "mystorepass", "jceksaes", "mykeypass");
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        AESCipher cipher = new AESCipher(key, iv);

        String encryptedMessage = cipher.getEncryptedMessage("this is message");
        LOG.debug("Message is: {}", encryptedMessage);

        assertThat(encryptedMessage, is(notNullValue()));
        assertThat(encryptedMessage, is(not("this is message")));
    }

    @Test
    public void decryptMessageFromKeystoreWithIv() throws UnsupportedEncodingException {

        Key key = KeystoreUtil.getKeyFromKeyStore("src/test/resources/aes-keystore.jck", "mystorepass", "jceksaes", "mykeypass");
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        AESCipher cipher = new AESCipher(key, iv);

        String messageToEncrypt = "this is the secret message I want to encode";

        String encryptedMessage = cipher.getEncryptedMessage(messageToEncrypt);
        String decryptedMessage = cipher.getDecryptedMessage(encryptedMessage);

        LOG.debug("Original Message: {}, Encrypted Message: {}, Decrypted Message: {}", messageToEncrypt, encryptedMessage, decryptedMessage);
        assertThat(decryptedMessage, is(messageToEncrypt));
    }

}
