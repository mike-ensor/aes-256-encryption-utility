[Run](#run) | [How To Use](#howto) | [Generate AES-256 Key](#generate) | [View AES-256 Key](#view) | [Command Line Encrypt/Decrypt](#use)

Overview:
===================
This is a sample utility to encrypt/decrypt using AES/CBC/PKCS5Padding algorithm

_Most common error_: "Invalid Key Size" error is most likely caused by not updating JCE strength policy, see above


**NOTE:** This example is built using **JDK7**, ultimate strength JCE (JDK7) and [Maven 3.x](http://maven.apache.org "Maven Documentation")

 - [Unlimited Strength Policy JDK7](http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html "Unlimited Strength Policy for JDK7")
 - JDK must have the unlimited strength policy for the JDK version


<a name="run"></a>To Run:
====================

    mvn clean test


[Run](#run) | [How To Use](#howto) | [Generate AES-256 Key](#generate) | [View AES-256 Key](#view) | [Command Line Encrypt/Decrypt](#use)

<a name="howto"></a>To Use:
====================

    # Key stored in JCEKS formatted Java keystore
    Key key = ....; // see tests pulling key from keystore
    // alternative is to hard-code key in string
    AESCipher cipher = new AESCipher(key);

    String encryptedMessage = cipher.getEncryptedMessage("this is message");
    String decryptedMessage = cipher.getDecryptedMessage(encryptedMessage);

    AESCipher cipherWithIv = new AESCipher(key, "0123456789012345".getBytes());
    String encryptedMessage = cipherWithIv.getEncryptedMessage("this is message");
    String decryptedMessage = cipherWithIv.getDecryptedMessage(encryptedMessage);


[Run](#run) | [How To Use](#howto) | [Generate AES-256 Key](#generate) | [View AES-256 Key](#view) | [Command Line Encrypt/Decrypt](#use)

<a name="generate"></a>Generate an AES-256 Key
======================

    keytool -genseckey -alias jceksaes -keyalg AES -keysize 256 -storetype JCEKS -keypass mykeypass -storetype jceks -keystore aes-keystore.jck -storepass mystorepass


[Run](#run) | [How To Use](#howto) | [Generate AES-256 Key](#generate) | [View AES-256 Key](#view) | [Command Line Encrypt/Decrypt](#use)

<a name="view"></a>View AES-256 Key from command line
======================

    mvn clean package // generate executable JAR file
    java -Dkeystore=main-aes-keystore.jck -Dstorepass=mystorepass -Dalias=jceksaes -Dkeypass=mykeypass -jar target/example-encryption-util.jar

    // or optionally with Maven (using the above defaults)
    mvn exec:java


[Run](#run) | [How To Use](#howto) | [Generate AES-256 Key](#generate) | [View AES-256 Key](#view) | [Command Line Encrypt/Decrypt](#use)

<a name="use"></a>Encrypt/Decrypt AES-256 from command line
======================

    // Generate executable JAR with:  mvn package

    // Ideally the IV passed in (0000000000000000) would be randomly generated
    java -Dkeystore=main-aes-keystore.jck -Dstorepass=mystorepass -Dalias=jceksaes -Dkeypass=mykeypass -jar target/example-encryption-util.jar encrypt blahblahblah 0000000000000000
    java -Dkeystore=main-aes-keystore.jck -Dstorepass=mystorepass -Dalias=jceksaes -Dkeypass=mykeypass -jar target/example-encryption-util.jar decrypt baN3CIAcVgq+AQr7lvKmLw== 0000000000000000


    java -Dkeystore=main-aes-keystore.jck -Dstorepass=mystorepass -Dalias=jceksaes -Dkeypass=mykeypass -jar target/example-encryption-util.jar encrypt blahblahblah 0000000000000001
    java -Dkeystore=main-aes-keystore.jck -Dstorepass=mystorepass -Dalias=jceksaes -Dkeypass=mykeypass -jar target/example-encryption-util.jar decrypt Wcaov8LNN4GJvp1bvOTJ0g== 0000000000000001

[Run](#run) | [How To Use](#howto) | [Generate AES-256 Key](#generate) | [View AES-256 Key](#view) | [Command Line Encrypt/Decrypt](#use)

Copywrite &copy;2013 - Use of this code and it's concepts are considered a Proof-of-concept and should not be used directly in any environment
