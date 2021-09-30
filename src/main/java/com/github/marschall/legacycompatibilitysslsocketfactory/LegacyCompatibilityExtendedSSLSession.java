package com.github.marschall.legacycompatibilitysslsocketfactory;

import java.security.Principal;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Objects;

import javax.net.ssl.ExtendedSSLSession;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSessionContext;

/**
 * A {@link ExtendedSSLSession} that implements {@link #getPeerCertificateChain()}
 * and delegates everything else to an actual {@link ExtendedSSLSession}.
 */
final class LegacyCompatibilityExtendedSSLSession extends ExtendedSSLSession {

  private final ExtendedSSLSession delegate;

  LegacyCompatibilityExtendedSSLSession(ExtendedSSLSession delegate) {
    Objects.requireNonNull(delegate, "delegate");
    this.delegate = delegate;
  }

  @Override
  public byte[] getId() {
    return delegate.getId();
  }

  @Override
  public SSLSessionContext getSessionContext() {
    return delegate.getSessionContext();
  }

  @Override
  public long getCreationTime() {
    return delegate.getCreationTime();
  }

  @Override
  public long getLastAccessedTime() {
    return delegate.getLastAccessedTime();
  }

  @Override
  public void invalidate() {
    delegate.invalidate();
  }

  @Override
  public boolean isValid() {
    return delegate.isValid();
  }

  @Override
  public void putValue(String name, Object value) {
    delegate.putValue(name, value);
  }

  @Override
  public Object getValue(String name) {
    return delegate.getValue(name);
  }

  @Override
  public void removeValue(String name) {
    delegate.removeValue(name);
  }

  @Override
  public String[] getValueNames() {
    return delegate.getValueNames();
  }

  @Override
  public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
    return delegate.getPeerCertificates();
  }

  @Override
  public Certificate[] getLocalCertificates() {
    return delegate.getLocalCertificates();
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
    return delegate.getPeerPrincipal();
  }

  @Override
  public Principal getLocalPrincipal() {
    return delegate.getLocalPrincipal();
  }

  @Override
  public String getCipherSuite() {
    return delegate.getCipherSuite();
  }

  @Override
  public String getProtocol() {
    return delegate.getProtocol();
  }

  @Override
  public String getPeerHost() {
    return delegate.getPeerHost();
  }

  @Override
  public int getPeerPort() {
    return delegate.getPeerPort();
  }

  @Override
  public int getPacketBufferSize() {
    return delegate.getPacketBufferSize();
  }

  @Override
  public int getApplicationBufferSize() {
    return delegate.getApplicationBufferSize();
  }

  @Override
  public String[] getLocalSupportedSignatureAlgorithms() {
    return delegate.getLocalSupportedSignatureAlgorithms();
  }

  @Override
  public String[] getPeerSupportedSignatureAlgorithms() {
    return delegate.getPeerSupportedSignatureAlgorithms();
  }

  @Override
  public List<SNIServerName> getRequestedServerNames() {
    return delegate.getRequestedServerNames();
  }

  @Override
  public List<byte[]> getStatusResponses() {
    return delegate.getStatusResponses();
  }

}
