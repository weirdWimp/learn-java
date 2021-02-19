package org.learn.something.security;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CertTest {
    public static void main(String[] args) throws Exception {
//        getCert();
        certPathBuild();
//        loadCertPath();
    }

    public static void getCert() throws CertificateException {
        CertificateFactory cerfFactory = CertificateFactory.getInstance("X.509");

        try (InputStream input = new FileInputStream("D:\\idea-workspace\\root_ca.crt")) {

            // load a certificate from file
            Certificate certificate = cerfFactory.generateCertificate(input);
            System.out.println(cerfFactory.getType());

            // get subject's public key from certificate
            PublicKey publicKey = certificate.getPublicKey();
            byte[] publicKeyEncoded = publicKey.getEncoded();

            // print public key
            System.out.println(publicKey.getFormat());
            System.out.println(publicKey.getAlgorithm());
            System.out.println(Util.toHex(publicKeyEncoded));
            System.out.println(Util.base64Encode(publicKeyEncoded));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * It doesn't work and why ?
     */
    public static void certPathBuild() throws CertificateException {
        try (InputStream input1 = new FileInputStream("D:\\idea-workspace\\root_ca.crt");
             InputStream input2 = new FileInputStream("D:\\idea-workspace\\sub_ca.crt")) {

            // load certificates from file
            CertificateFactory cerfFactory1 = CertificateFactory.getInstance("X.509");
            Certificate rootCert = cerfFactory1.generateCertificate(input1);

            CertificateFactory cerfFactory2 = CertificateFactory.getInstance("X.509");
            Certificate subCert = cerfFactory2.generateCertificate(input2);

            // init a Collection type CertStore to search
            List<Certificate> certs = Arrays.asList(subCert,rootCert);
            CollectionCertStoreParameters ccsp = new CollectionCertStoreParameters(certs);
            CertStore store = CertStore.getInstance("Collection", ccsp);
            for (Certificate certificate : store.getCertificates(null)) {
                System.out.println(((X509Certificate) certificate).getSubjectX500Principal());
                System.out.println(Util.base64Encode(certificate.getEncoded()));
            }

            // init PKIXBuilderParameters with a trustAnchor and a certSelector
            TrustAnchor trustAnchor = new TrustAnchor(((X509Certificate) rootCert), null);
            X509CertSelector targetConstraints = new X509CertSelector();

            PKIXBuilderParameters params =
                    new PKIXBuilderParameters(Collections.singleton(trustAnchor), targetConstraints);
            params.setCertStores(Collections.singletonList(store));
            params.setMaxPathLength(5);

            CertPathBuilder cpb = CertPathBuilder.getInstance("PKIX");
            CertPathBuilderResult builderResult = cpb.build(params);

            CertPath certPath = builderResult.getCertPath();
            System.out.println(certPath.getCertificates().size());

            for (Certificate certPathCertificate : certPath.getCertificates()) {
                System.out.println(certPathCertificate);
            }

        } catch (IOException | CertificateException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | CertPathBuilderException | CertStoreException e) {
            e.printStackTrace();
        }
    }

    /**
     * It works.
     * ca.p7b created through openssl crl2pkcs7 command represents a certificate chain/path:
     *
     * $ openssl crl2pkcs7 -certfile root_ca.crt -certfile sub_ca.crt -outform PEM -out
     *  ca.p7b -nocrl
     *
     * @throws Exception
     */
    public static void loadCertPath() throws Exception {
        CertificateFactory cerfFactory = CertificateFactory.getInstance("X.509");
        try (InputStream inputStream = new FileInputStream("D:\\idea-workspace\\ca.p7b")) {
            CertPath certPath = cerfFactory.generateCertPath(inputStream, "PKCS7");
            for (Certificate certificate : certPath.getCertificates()) {
                System.out.println(((X509Certificate) certificate).getSubjectX500Principal());
            }
        }
    }
}
