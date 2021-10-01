package com.github.marschall.legacycompatibilitysslsocketfactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * A {@link SSLSocketFactory} that delegates to a {@link SSLSocketFactory} but provides a
 * {@link SSLSession} that implements {@link SSLSession#getPeerCertificateChain()}.
 */
public final class LegacyCompatibilitySSLSocketFactory extends SSLSocketFactory {

  private final SSLSocketFactory delegate;

  public LegacyCompatibilitySSLSocketFactory() {
    this((SSLSocketFactory) SSLSocketFactory.getDefault());
  }

  public LegacyCompatibilitySSLSocketFactory(SSLSocketFactory delegate) {
    Objects.requireNonNull(delegate, "delegate");
    this.delegate = delegate;
  }

  @Override
  public String[] getDefaultCipherSuites() {
    return delegate.getDefaultCipherSuites();
  }

  @Override
  public Socket createSocket() throws IOException {
    return new LegacyCompatibilitySSLSocket((SSLSocket) delegate.createSocket());
  }

  @Override
  public String[] getSupportedCipherSuites() {
    return delegate.getSupportedCipherSuites();
  }

  @Override
  public Socket createSocket(String host, int port) throws IOException {
    return new LegacyCompatibilitySSLSocket((SSLSocket) delegate.createSocket(host, port));
  }

  @Override
  public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
    return new LegacyCompatibilitySSLSocket((SSLSocket) delegate.createSocket(s, host, port, autoClose));
  }

  @Override
  public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
    return new LegacyCompatibilitySSLSocket((SSLSocket) delegate.createSocket(host, port, localHost, localPort));
  }

  @Override
  public Socket createSocket(Socket s, InputStream consumed, boolean autoClose) throws IOException {
    return new LegacyCompatibilitySSLSocket((SSLSocket) delegate.createSocket(s, consumed, autoClose));
  }

  @Override
  public Socket createSocket(InetAddress host, int port) throws IOException {
    return new LegacyCompatibilitySSLSocket((SSLSocket) delegate.createSocket(host, port));
  }

  @Override
  public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
    return new LegacyCompatibilitySSLSocket((SSLSocket) delegate.createSocket(address, port, localAddress, localPort));
  }

}
