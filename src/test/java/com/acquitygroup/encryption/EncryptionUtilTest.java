package com.acquitygroup.encryption;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class EncryptionUtilTest {

    private static final Logger LOG = LoggerFactory.getLogger(EncryptionUtilTest.class);

    @Test
    public void encryptMessage() throws UnsupportedEncodingException {

        String key = "770A8A65DA156D24EE2A093277530142";
        AESCipher cipher = new AESCipher(key.getBytes("UTF-8"));

        EncryptionUtil util = EncryptionUtil.getInstance();

        String encryptedMessage = util.encrypt("this is message", cipher);
        LOG.debug("Message is: {}", encryptedMessage);

        assertThat(encryptedMessage, is(notNullValue()));
        assertThat(encryptedMessage, is(not("this is message")));
    }

    @Test
    public void decryptMessage() throws UnsupportedEncodingException {
        String key = "770A8A65DA156D24EE2A093277530142";
        AESCipher cipher = new AESCipher(key.getBytes("UTF-8"));


        String messageToEncrypt = "this is the secret message I want to encode";

        EncryptionUtil util = EncryptionUtil.getInstance();

        String encryptedMessage = util.encrypt(messageToEncrypt, cipher);
        String decryptedMessage = util.decrypt(encryptedMessage, cipher);

        LOG.debug("Original Message: {}, Encrypted Message: {}, Decrypted Message: {}", messageToEncrypt, encryptedMessage, decryptedMessage);
        assertThat(decryptedMessage, is(messageToEncrypt));
    }

}
