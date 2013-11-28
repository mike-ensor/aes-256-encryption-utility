package com.acquitygroup.encryption;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.security.Key;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class KeystoreUtilTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void getKeystoreResource() {

        Key key = KeystoreUtil.getKeyFromKeyStore("src/test/resources/aes-keystore.jck", "mystorepass", "jceksaes", "mykeypass");

        assertThat(key, is(notNullValue()));
    }

    @Test
    public void getKeystoreResourceMissingKeystore() {

        exception.expect(RuntimeException.class);

        KeystoreUtil.getKeyFromKeyStore("notFound.jck", "mystorepass", "jceksaes", "mykeypass");
    }

}
