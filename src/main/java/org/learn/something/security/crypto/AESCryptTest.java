package org.learn.something.security.crypto;

import lombok.SneakyThrows;
import org.apache.tomcat.util.buf.HexUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;

public class AESCryptTest {

    public static final String AES = "AES";
    public static final String AES_CBC_PKCS_5_PADDING = "AES/CBC/PKCS5Padding";

    public static void main(String[] args) {
        String keyHex = "1004087F17C38D06C8B24CA69175984C";
        String ivHex = "5FFD9210A708073E5AB718800F9958E6";
        String cipherTextHex = "68b055596c1215a16f2da380aae12dfa";

        byte[] key = HexUtils.fromHexString(keyHex);
        byte[] iv = HexUtils.fromHexString(ivHex);
        byte[] cipherText = HexUtils.fromHexString(cipherTextHex);

        byte[] clearText = aesDecrypt(key, iv, cipherText);
        System.out.println("####" + new String(clearText, StandardCharsets.UTF_8) + "####");

        byte[] cipherText2 = aesEncrypt(key, iv, "hello".getBytes(StandardCharsets.UTF_8));
        System.out.println(HexUtils.toHexString(cipherText2));

    }

    @SneakyThrows
    public static byte[] aesDecrypt(byte[] key, byte[] iv, byte[] cipherText) {
        // constructor a secret key
        SecretKeySpec secretKey = new SecretKeySpec(key, AES);

        // init algorithm parameters with iv
        AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance(AES);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        algorithmParameters.init(ivParameterSpec);

        // init a AES cipher with key and iv
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_5_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, algorithmParameters);

        // decrypt
        return cipher.doFinal(cipherText);
    }

    @SneakyThrows
    public static byte[] aesEncrypt(byte[] key, byte[] iv, byte[] plainText) {
        // constructor a secret key
        SecretKeySpec secretKey = new SecretKeySpec(key, AES);

        // init algorithm parameters with iv
        AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance(AES);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        algorithmParameters.init(ivParameterSpec);

        // init a AES cipher with key and iv
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, algorithmParameters);

        // decrypt
        return cipher.doFinal(plainText);
    }
}
