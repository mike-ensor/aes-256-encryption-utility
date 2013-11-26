This is a sample utility to encrypt/decrypt using AES/CBC/PKCS5Padding algorithm

JDK must have the unlimited strength policy for the JDK version

NOTE: This example is built using JDK7, ultimate strength JCE (JDK7) and Maven 3.x

To Run:
====================

    mvn clean test


To Use:
====================

    # Some key (substitute for PBE Key, from file or from JCEKS keystore
    String key = "770A8A65DA156D24EE2A093277530142";
    AESCipher cipher = new AESCipher(key.getBytes("UTF-8"));

    EncryptionUtil util = EncryptionUtil.getInstance();

    String encryptedMessage = util.encrypt("this is message", cipher);


