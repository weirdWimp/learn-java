package org.learn.something.security;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Enumeration;

public class KeyStoreTest {

    public static void main(String[] args) throws Exception {
        keyStoreTest();
    }

    /**
     * Use openssl tool to create pkcs12 file:
     * $ openssl pkcs12 -export -in con_key_crt.pem -out sub_ca.p12 -CAfile root_ca.crt -name myKeyEntry
     *
     * con_key_crt.pem is a simple concatenation of sub_ca_key.pem and sub_ca.crt:
     * $ cat sub_ca_key.pem sub_ca.crt >> con_key_crt.pem
     *
     *
     * @throws Exception
     */
    public static void keyStoreTest() throws Exception {
        try (InputStream in = new FileInputStream("D:\\idea-workspace\\sub_ca.p12")) {
            KeyStore keyStore = KeyStore.getInstance("pkcs12");
            keyStore.load(in, "123456".toCharArray());

            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                System.out.println("alias:" + aliases.nextElement());
            }

            Key key = keyStore.getKey("myKeyEntry", "123456".toCharArray());
            System.out.println(key.getAlgorithm());

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(key, RSAPrivateKeySpec.class);
            System.out.println(privateKeySpec.getModulus());

            Certificate certificate = keyStore.getCertificate("myKeyEntry");
            System.out.println(certificate.toString());
        }
    }

    public static void certFactoryTest() throws CertificateException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
    }
}
