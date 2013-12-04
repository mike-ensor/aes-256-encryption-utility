package com.acquitygroup.encryption;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class EncryptionUtilTest {

    private static final Logger LOG = LoggerFactory.getLogger(EncryptionUtilTest.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
    public void encryptMessageFromKeystore() {

        Key key = KeystoreUtil.getKeyFromKeyStore("src/test/resources/aes-keystore.jck", "mystorepass", "jceksaes", "mykeypass");

        AESCipher cipher = new AESCipher(key);

        String encryptedMessage = cipher.getEncryptedMessage("this is message");
        LOG.debug("Message is: {}", encryptedMessage);

        assertThat(encryptedMessage, is(notNullValue()));
        assertThat(encryptedMessage, is(not("this is message")));
    }

    @Test
    public void decryptMessageFromKeystore() {

        Key key = KeystoreUtil.getKeyFromKeyStore("src/test/resources/aes-keystore.jck", "mystorepass", "jceksaes", "mykeypass");
        AESCipher cipher = new AESCipher(key);

        String messageToEncrypt = "this is the secret message I want to encode";

        String encryptedMessage = cipher.getEncryptedMessage(messageToEncrypt);
        String decryptedMessage = cipher.getDecryptedMessage(encryptedMessage);

        LOG.debug("Original Message: {}, Encrypted Message: {}, Decrypted Message: {}", messageToEncrypt, encryptedMessage, decryptedMessage);
        assertThat(decryptedMessage, is(messageToEncrypt));
    }

    @Test
    public void usingStringBasedIV() {

        Key key = KeystoreUtil.getKeyFromKeyStore("src/test/resources/aes-keystore.jck", "mystorepass", "jceksaes", "mykeypass");
        String iv = "1111111111111111";

        AESCipher cipher = new AESCipher(key, iv.getBytes());

        String encryptedMessage = cipher.getEncryptedMessage("this is message");
        LOG.debug("Message is: {}", encryptedMessage);

        assertThat(encryptedMessage, is(notNullValue()));
        assertThat(encryptedMessage, is(not("this is message")));
    }

    @Test
    public void usingStringBasedIVWithIncorrectLength() {

        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Wrong IV length: must be 16 bytes long");

        Key key = KeystoreUtil.getKeyFromKeyStore("src/test/resources/aes-keystore.jck", "mystorepass", "jceksaes", "mykeypass");
        String iv = "11111111";

        AESCipher cipher = new AESCipher(key, iv.getBytes());

        cipher.getEncryptedMessage("this is message");
    }

    @Test
    public void encryptMessageFromKeystoreWithIv() {

        Key key = KeystoreUtil.getKeyFromKeyStore("src/test/resources/aes-keystore.jck", "mystorepass", "jceksaes", "mykeypass");
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        AESCipher cipher = new AESCipher(key, iv);

        String encryptedMessage = cipher.getEncryptedMessage("this is message");
        LOG.debug("Message is: {}", encryptedMessage);

        assertThat(encryptedMessage, is(notNullValue()));
        assertThat(encryptedMessage, is(not("this is message")));
    }

    @Test
    public void decryptMessageFromKeystoreWithIv() {

        Key key = KeystoreUtil.getKeyFromKeyStore("src/test/resources/aes-keystore.jck", "mystorepass", "jceksaes", "mykeypass");
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        AESCipher cipher = new AESCipher(key, iv);

        String messageToEncrypt = "this is the secret message I want to encode";

        String encryptedMessage = cipher.getEncryptedMessage(messageToEncrypt);
        String decryptedMessage = cipher.getDecryptedMessage(encryptedMessage);

        LOG.debug("Original Message: {}, Encrypted Message: {}, Decrypted Message: {}", messageToEncrypt, encryptedMessage, decryptedMessage);
        assertThat(decryptedMessage, is(messageToEncrypt));
    }

    @Test
    public void encryptDecryptUsingDifferentCiphersSameIV() {

        Key key = KeystoreUtil.getKeyFromKeyStore("src/test/resources/aes-keystore.jck", "mystorepass", "jceksaes", "mykeypass");
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        byte[] differentIV = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        AESCipher cipher = new AESCipher(key, iv);
        AESCipher differentCipher = new AESCipher(key, differentIV);

        String messageToEncrypt = "this is the secret message I want to encode";

        String encryptedMessage = cipher.getEncryptedMessage(messageToEncrypt);
        String decryptedMessage = differentCipher.getDecryptedMessage(encryptedMessage);

        LOG.debug("Original Message: {}, Encrypted Message: {}, Decrypted Message: {}", messageToEncrypt, encryptedMessage, decryptedMessage);
        assertThat(decryptedMessage, is(messageToEncrypt));
    }

    @Test
    public void encryptDecryptUsingDifferentCiphersDifferentIV() {

        Key key = KeystoreUtil.getKeyFromKeyStore("src/test/resources/aes-keystore.jck", "mystorepass", "jceksaes", "mykeypass");
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        byte[] differentIV = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

        AESCipher cipher = new AESCipher(key, iv);
        AESCipher differentCipher = new AESCipher(key, differentIV);

        String messageToEncrypt = "this is the secret message I want to encode";

        String encryptedMessage = cipher.getEncryptedMessage(messageToEncrypt);
        String decryptedMessage = differentCipher.getDecryptedMessage(encryptedMessage);

        LOG.debug("Encrypted: [{}], Decrypted[{}]", encryptedMessage, decryptedMessage);

        assertThat("Original message should have not been the same after decoding with a different IV", decryptedMessage, is(not(messageToEncrypt)));
    }

}
