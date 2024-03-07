package com.colak.springreactivewebclienttutorial.quote.config.sslpinningwebclient;

import javax.net.ssl.X509TrustManager;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Formatter;

public class PinningTrustManager implements X509TrustManager {
    private final String pinnedSha256;

    public PinningTrustManager(String pinnedSha256) {
        this.pinnedSha256 = pinnedSha256;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        if (chain == null || chain.length == 0 || chain[0] == null) {
            throw new CertificateException("Certificate chain is not valid");
        }
        X509Certificate cert = chain[0];
        String sha256 = getSha256(cert);
        if (!sha256.equals(pinnedSha256)) {
            throw new CertificateException("Certificate pinning failed");
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    private String getSha256(X509Certificate cert) throws CertificateException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encoded = digest.digest(cert.getEncoded());
            return bytesToHex(encoded);
        } catch (Exception e) {
            throw new CertificateException("Could not generate SHA-256", e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
