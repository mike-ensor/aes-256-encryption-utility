package com.acquitygroup.encryption;

import com.google.common.io.BaseEncoding;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.cert.CertificateException;

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

        Key key = getKeyFromKeyStore();

        AESCipher cipher = new AESCipher(key);

        String encryptedMessage = cipher.getEncryptedMessage("this is message");
        LOG.debug("Message is: {}", encryptedMessage);

        assertThat(encryptedMessage, is(notNullValue()));
        assertThat(encryptedMessage, is(not("this is message")));
    }

    @Test
    public void decryptMessageFromKeystore() throws UnsupportedEncodingException {

        Key key = getKeyFromKeyStore();
        AESCipher cipher = new AESCipher(key);

        String messageToEncrypt = "this is the secret message I want to encode";

        String encryptedMessage = cipher.getEncryptedMessage(messageToEncrypt);
        String decryptedMessage = cipher.getDecryptedMessage(encryptedMessage);

        LOG.debug("Original Message: {}, Encrypted Message: {}, Decrypted Message: {}", messageToEncrypt, encryptedMessage, decryptedMessage);
        assertThat(decryptedMessage, is(messageToEncrypt));
    }

    private Key getKeyFromKeyStore() {
        try {
            InputStream keystoreStream = ClassLoader.getSystemResourceAsStream("aes-keystore.jck");

            KeyStore keystore = KeyStore.getInstance("JCEKS");
            String keystorePass = "mystorepass";
            String keyPass = "mykeypass";
            String alias = "jceksaes";

            keystore.load(keystoreStream, keystorePass.toCharArray());

            LOG.debug("Keystore with alias {} found == {}", alias, keystore.containsAlias(alias));
            if (!keystore.containsAlias(alias)) {
                throw new RuntimeException("Alias for key not found");
            }

            Key key = keystore.getKey(alias, keyPass.toCharArray());
            LOG.debug("Key Found {} -> {}", key.getAlgorithm(), BaseEncoding.base64().encode(key.getEncoded()));

            return key;

        } catch (UnrecoverableKeyException | KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
