This is a sample utility to encrypt/decrypt using AES/CBC/PKCS5Padding algorithm

JDK must have the unlimited strength policy for the JDK version

NOTE: This example is built using JDK7, ultimate strength JCE (JDK7) and Maven 3.x
 - http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html

To Run:
====================

    mvn clean test


To Use:
====================

    # Key stored in JCEKS formatted Java keystore
    Key key = ....; // see tests pulling key from keystore
    // alternative is to hard-code key in string
    AESCipher cipher = new AESCipher(key);

    String encryptedMessage = cipher.getEncryptedMessage("this is message");
    String decryptedMessage = cipher.getDecryptedMessage(encryptedMessage);



