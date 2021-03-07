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
        String keyHex = "421990ECB00E9A9B8372D0E46727F6AA";
        String ivHex = "5973EAA7D1C1D5240B78CB8A4458D663";
        String cipherTextHex = "b04f31425a2b018221cf1c00602d6cdc";

        byte[] key = HexUtils.fromHexString(keyHex);
        byte[] iv = HexUtils.fromHexString(ivHex);
        byte[] cipherText = HexUtils.fromHexString(cipherTextHex);

        byte[] clearText = aesDecrypt(key, iv, cipherText);
        System.out.println(new String(clearText, StandardCharsets.UTF_8));
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
}
