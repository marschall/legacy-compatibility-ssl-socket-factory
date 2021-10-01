package com.github.marschall.legacycompatibilitysslsocketfactory;

import java.security.Principal;
import java.security.cert.Certificate;
import java.util.Objects;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;

/**
 * A {@link SSLSession} that implements {@link #getPeerCertificateChain()}
 * and delegates everything else to an actual {@link SSLSession}.
 */
final class LegacyCompatibilitySSLSession implements SSLSession {

  private final SSLSession delegate;

  LegacyCompatibilitySSLSession(SSLSession delegate) {
    Objects.requireNonNull(delegate, "delegate");
    this.delegate = delegate;
  }

  @Override
  public byte[] getId() {
    return this.delegate.getId();
  }

  @Override
  public SSLSessionContext getSessionContext() {
    return this.delegate.getSessionContext();
  }

  @Override
  public long getCreationTime() {
    return this.delegate.getCreationTime();
  }

  @Override
  public long getLastAccessedTime() {
    return this.delegate.getLastAccessedTime();
  }

  @Override
  public void invalidate() {
    this.delegate.invalidate();
  }

  @Override
  public boolean isValid() {
    return this.delegate.isValid();
  }

  @Override
  public void putValue(String name, Object value) {
    this.delegate.putValue(name, value);
  }

  @Override
  public Object getValue(String name) {
    return this.delegate.getValue(name);
  }

  @Override
  public void removeValue(String name) {
    this.delegate.removeValue(name);
  }

  @Override
  public String[] getValueNames() {
    return this.delegate.getValueNames();
  }

  @Override
  public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
    return this.delegate.getPeerCertificates();
  }

  @Override
  public Certificate[] getLocalCertificates() {
    return this.delegate.getLocalCertificates();
  }

  @Override
  public javax.security.cert.X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
    Certificate[] peerCertificates = this.getPeerCertificates();
    javax.security.cert.X509Certificate[] certificateChain = new javax.security.cert.X509Certificate[peerCertificates.length];
    for (int i = 0; i < peerCertificates.length; i++) {
      java.security.cert.Certificate certificate = peerCertificates[i];
      certificateChain[i] = new CertificateAdapter((java.security.cert.X509Certificate) certificate);

    }
    return certificateChain;
  }

  @Override
  public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
    return this.delegate.getPeerPrincipal();
  }

  @Override
  public Principal getLocalPrincipal() {
    return this.delegate.getLocalPrincipal();
  }

  @Override
  public String getCipherSuite() {
    return this.delegate.getCipherSuite();
  }

  @Override
  public String getProtocol() {
    return this.delegate.getProtocol();
  }

  @Override
  public String getPeerHost() {
    return this.delegate.getPeerHost();
  }

  @Override
  public int getPeerPort() {
    return this.delegate.getPeerPort();
  }

  @Override
  public int getPacketBufferSize() {
    return this.delegate.getPacketBufferSize();
  }

  @Override
  public int getApplicationBufferSize() {
    return this.delegate.getApplicationBufferSize();
  }

}
