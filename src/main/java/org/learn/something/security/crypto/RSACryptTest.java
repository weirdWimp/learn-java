package org.learn.something.security.crypto;

import lombok.SneakyThrows;

import javax.crypto.Cipher;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @author guo
 * @date 2021/5/18
 */
public class RSACryptTest {

    @SneakyThrows
    public static void main(String[] args) {

        String pkcs8PrivateKeyPath = "E:\\learning-dir\\shell-learning\\rsa\\rsa_private_pkcs8.der";
        String cipherTextPath = "E:\\learning-dir\\shell-learning\\rsa\\cipher_text";

        byte[] privateKeyBytes = Files.readAllBytes(Paths.get(pkcs8PrivateKeyPath));
        byte[] cipherText = Files.readAllBytes(Paths.get(cipherTextPath));

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] clearText = cipher.doFinal(cipherText);
        System.out.println(new String(clearText));
    }
}
