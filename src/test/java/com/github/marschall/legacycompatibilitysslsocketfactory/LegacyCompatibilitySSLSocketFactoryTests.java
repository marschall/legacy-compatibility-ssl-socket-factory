package com.github.marschall.legacycompatibilitysslsocketfactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.security.cert.X509Certificate;

import org.junit.jupiter.api.Test;

class LegacyCompatibilitySSLSocketFactoryTests {

  @Test
  void getPeerCertificateChain() throws IOException {
    URL badssl = new URL("https://badssl.com");
    HttpsURLConnection urlConnection = (HttpsURLConnection) badssl.openConnection();
    SSLSocketFactory socketFactory = new LegacyCompatibilitySSLSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());
    urlConnection.setSSLSocketFactory(socketFactory);
    urlConnection.connect();
    try (InputStream inputStream = urlConnection.getInputStream()) {
      Optional<SSLSession> maybeSslSession = urlConnection.getSSLSession();
      assertTrue(maybeSslSession.isPresent());
      SSLSession sslSession = maybeSslSession.get();
      X509Certificate[] peerCertificateChain = sslSession.getPeerCertificateChain();
      assertNotNull(peerCertificateChain);
      assertTrue(peerCertificateChain.length > 0);

      byte[] allBytes = inputStream.readAllBytes();
      assertNotNull(allBytes);
      assertTrue(allBytes.length > 0);
    }
  }

}
