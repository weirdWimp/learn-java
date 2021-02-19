package org.learn.something.security;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;

public class CipherTest {

    public static final char[] hexChars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static void main(String[] args) throws Exception {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(128);
//        SecretKey key = keyGenerator.generateKey();
//
//        Cipher cipher = Cipher.getInstance("AES_128/CFB/NoPadding");
//        cipher.init(Cipher.ENCRYPT_MODE, key);
//
//        byte[] cipherBytes = cipher.doFinal("helloworld".getBytes(StandardCharsets.UTF_8));
//        System.out.println(cipher.getIV().length);

//        withParameters();

//        keyPairGen();
//        withParameters();
        keyStoreTest();

    }

    public static void withParameters() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        PBEKeySpec pbeKeySpec = new PBEKeySpec("passowrd".toCharArray());
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_256");
        SecretKey secretKey = factory.generateSecret(pbeKeySpec);

        Cipher cipher = Cipher.getInstance("PBEWithHmacSHA256AndAES_256/CBC/PKCS5Padding");

        IvParameterSpec ivParameterSpec = new IvParameterSpec("0123456789abcdef".getBytes());
        PBEParameterSpec parameterSpec = new PBEParameterSpec("love the world".getBytes(), 1000, ivParameterSpec);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
        byte[] cipherTextBytes = cipher.doFinal("hello".getBytes());
        System.out.println(toHex(cipherTextBytes));
    }

    public static void keyPairGen() throws NoSuchAlgorithmException {
        KeyPairGenerator pairGenerator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = pairGenerator.generateKeyPair();

        System.out.println(keyPair.getPublic().getFormat());
        System.out.println(keyPair.getPrivate().getFormat());

    }

    public static String toHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            int high = (b & 0xf0) >> 4;
            int low = (b & 0x0f);
            builder.append(hexChars[high]).append(hexChars[low]);
        }
        return builder.toString();
    }

    public static void keyStoreTest() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        KeyStore keyStore = KeyStore.getInstance("pkcs12");
        keyStore.load(null, null);

        SecretKey key = KeyGenerator.getInstance("AES").generateKey();
        keyStore.setKeyEntry("aes-key", key, "123456".toCharArray(), null);

        FileOutputStream outputStream = new FileOutputStream("../guo.keystore");
        keyStore.store(outputStream, "123456".toCharArray());
    }

    public static void certFactoryTest() throws CertificateException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
    }
}
