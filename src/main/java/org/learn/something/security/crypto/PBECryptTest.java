package org.learn.something.security.crypto;

import lombok.SneakyThrows;
import org.apache.tomcat.util.buf.HexUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * @author guo
 * @date 2021/4/11
 */
public class PBECryptTest {

    public static void main(String[] args) {
        byte[] plainText = "hello".getBytes(StandardCharsets.UTF_8);

        // create key
        char[] password = "12345678".toCharArray();

        // salt can be acquired by Random generator
        byte[] salt = HexUtils.fromHexString("3631C9DA382CE487");
        int iter = 1000;
        int keyLength = 128;

        // init algorithm
        // iv can be acquired by Random generator
        byte[] iv = HexUtils.fromHexString("5FFD9210A708073E5AB718800F9958E6");

        // ==============================================================================
        // 1. use pbkdf2 to generate 128 bits key
        // 2. use key and AES to encrypt the message
        byte[] key = derivePbkdf2Key(password, salt, iter, keyLength);
        byte[] cipherText1 = encryptTest(plainText, key, iv);
        opensslOutput(salt, cipherText1);


        // ==============================================================================
        // use PBE to encrypt the message so we don't have to care about the derived key
        byte[] cipherText2 = pbeEncryptTest(plainText, password, salt, iter, iv);
        opensslOutput(salt, cipherText2);
    }

    /**
     * openssl output format: Salted__${salt}${cipherText}
     *
     * @param salt
     * @param cipherText
     */
    private static void opensslOutput(byte[] salt, byte[] cipherText) {
        byte[] finalBytes = new byte["Salted__".length() + salt.length + cipherText.length];
        System.arraycopy("Salted__".getBytes(), 0, finalBytes, 0, "Salted__".length());
        System.arraycopy(salt, 0, finalBytes, "Salted__".length(), salt.length);
        System.arraycopy(cipherText, 0, finalBytes, salt.length + "Salted__".length(), cipherText.length);
        System.out.println("openssl output format: " + Base64.getEncoder().encodeToString(finalBytes));
    }

    /**
     * pbkdf2
     *
     * @return
     */
    @SneakyThrows
    private static byte[] derivePbkdf2Key(char[] password, byte[] salt, int iter, int keyLength) {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        KeySpec pbeKeySpec = new PBEKeySpec(password, salt, iter, keyLength);
        SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);

        byte[] encodedKey = secretKey.getEncoded();
        System.out.printf("algorithm： %s, key(hex format): %s\n", secretKey.getAlgorithm(), HexUtils.toHexString(encodedKey));
        return encodedKey;
    }

    @SneakyThrows
    public static byte[] encryptTest(byte[] plainText, byte[] key, byte[] iv) {
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

        AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        algorithmParameters.init(ivParameterSpec);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, algorithmParameters);

        return cipher.doFinal(plainText);
    }

    @SneakyThrows
    public static byte[] pbeEncryptTest(byte[] plainText, char[] password, byte[] salt, int iter, byte[] iv) {

        KeySpec pbeKeySpec = new PBEKeySpec(password, salt, iter, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_128");
        SecretKey secretKey = factory.generateSecret(pbeKeySpec);

        Cipher cipher = Cipher.getInstance("PBEWithHmacSHA256AndAES_128/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, iter, ivParameterSpec);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
        return cipher.doFinal(plainText);
    }
}
