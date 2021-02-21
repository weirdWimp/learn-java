package org.learn.something.security.digest;

import lombok.SneakyThrows;
import org.apache.tomcat.util.buf.HexUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.Security;

public class DigestTest {


    public static void main(String[] args) {
        bouncyCastleLib();
    }

    @SneakyThrows
    public static void basicDigestAlgorithmsByJava() {
        String[] digestAlgorithms = {"MD5", "SHA-256", "SHA-512"};
        for (String digestAlgorithm : digestAlgorithms) {
            MessageDigest messageDigest = MessageDigest.getInstance(digestAlgorithm);
            byte[] digest = messageDigest.digest("hello world".getBytes(StandardCharsets.UTF_8));
            System.out.printf("Algorithm: %-8s Length: %d Digest: %s\n", digestAlgorithm, digest.length, HexUtils.toHexString(digest));
        }
    }

    @SneakyThrows
    private static void bouncyCastleLib() {
        // register BouncyCastle
        Security.addProvider(new BouncyCastleProvider());
        MessageDigest ripeMD160 = MessageDigest.getInstance("RipeMD160");
        byte[] digest = ripeMD160.digest("hello world".getBytes(StandardCharsets.UTF_8));
        System.out.println("RipeMD160， digest:" + HexUtils.toHexString(digest));
    }

}
