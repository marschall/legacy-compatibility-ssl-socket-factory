package com.github.marschall.legacycompatibilitysslsocketfactory;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.security.cert.X509Certificate;

import org.junit.jupiter.api.Test;

class LegacyCompatibilitySSLSocketFactoryTests {

  @Test
  void urlConnection() throws IOException {
    URL badssl = new URL("https://badssl.com");
    HttpsURLConnection urlConnection = (HttpsURLConnection) badssl.openConnection();
    SSLSocketFactory socketFactory = new LegacyCompatibilitySSLSocketFactory();
    urlConnection.setSSLSocketFactory(socketFactory);
    urlConnection.connect();
    try (InputStream inputStream = urlConnection.getInputStream()) {
      Optional<SSLSession> maybeSslSession = urlConnection.getSSLSession();
      assertTrue(maybeSslSession.isPresent());
      SSLSession sslSession = maybeSslSession.get();
      assertSame(LegacyCompatibilityExtendedSSLSession.class, sslSession.getClass(), "SSLSession class");
      X509Certificate[] peerCertificateChain = sslSession.getPeerCertificateChain();
      assertNotNull(peerCertificateChain);
      assertTrue(peerCertificateChain.length > 0);

      byte[] allBytes = inputStream.readAllBytes();
      assertNotNull(allBytes);
      assertTrue(allBytes.length > 0);
    }
  }

  @Test
  void createSocket() throws Exception {
    SSLSocketFactory socketFactory = new LegacyCompatibilitySSLSocketFactory();
    ReentrantLock lock = new ReentrantLock();
    // HandshakeCompletedEvent seems to be delivered asynchronously in a different thread
    // is condition is signaled when the event is delivered to avoid unnecessary waiting
    Condition isSet = lock.newCondition();
    AtomicReference<HandshakeCompletedEvent> eventHolder = new AtomicReference();
    try (var sslSocket = (SSLSocket) socketFactory.createSocket("badssl.com", 443)) {
      HandshakeCompletedListener listener = event -> {
        eventHolder.set(event);
        lock.lock();
        try {
          isSet.signal();
        } finally {
          lock.unlock();
        }
      };
      sslSocket.addHandshakeCompletedListener(listener);
      sslSocket.startHandshake();
      var sslSession = sslSocket.getSession();
      X509Certificate[] peerCertificateChain = sslSession.getPeerCertificateChain();
      assertNotNull(peerCertificateChain);
      assertTrue(peerCertificateChain.length > 0);
      
      lock.lock();
      try {
        // wait at most a second for the event
        assertTrue(isSet.await(1L, TimeUnit.SECONDS));
      } finally {
        lock.unlock();
      }

      HandshakeCompletedEvent event = eventHolder.get();
      assertNotNull(event);
      assertSame(sslSocket, event.getSocket());
      assertArrayEquals(peerCertificateChain, event.getPeerCertificateChain());
      // will throw if instance is not registered
      sslSocket.removeHandshakeCompletedListener(listener);
    }
  }

}
