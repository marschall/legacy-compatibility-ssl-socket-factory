package com.github.marschall.legacycompatibilitysslsocketfactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketOption;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

import javax.net.ssl.ExtendedSSLSession;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

/**
 * A {@link SSLSocket} that delegates everything but access to {@link SSLSession} to a
 * {@link SSLSocket} and wraps the {@link SSLSession} in a {@link LegacyCompatibilitySSLSession}
 * or {@link LegacyCompatibilityExtendedSSLSession}.
 */
final class LegacyCompatibilitySSLSocket extends SSLSocket {

  private final SSLSocket delegate;
  
  private final SocketChannel channel;

  LegacyCompatibilitySSLSocket(SSLSocket delegate) {
    Objects.requireNonNull(delegate, "delegate");
    this.delegate = delegate;
    SocketChannel delegateChannel = delegate.getChannel();
    if (delegateChannel != null) {
      this.channel = new LegacyCompatibilitySocketChannel(delegateChannel.provider(), delegateChannel, this);
    } else {
      this.channel = null;
    }
    
  }

  @Override
  public String[] getSupportedCipherSuites() {
    return delegate.getSupportedCipherSuites();
  }

  @Override
  public String[] getEnabledCipherSuites() {
    return delegate.getEnabledCipherSuites();
  }

  @Override
  public void setEnabledCipherSuites(String[] suites) {
    delegate.setEnabledCipherSuites(suites);
  }

  @Override
  public String[] getSupportedProtocols() {
    return delegate.getSupportedProtocols();
  }

  @Override
  public String[] getEnabledProtocols() {
    return delegate.getEnabledProtocols();
  }

  @Override
  public void setEnabledProtocols(String[] protocols) {
    delegate.setEnabledProtocols(protocols);
  }

  @Override
  public SSLSession getSession() {
    return adaptSSLSession(delegate.getSession());
  }

  @Override
  public SSLSession getHandshakeSession() {
    return adaptSSLSession(delegate.getHandshakeSession());
  }

  private static SSLSession adaptSSLSession(SSLSession delegate) {
    if (delegate instanceof ExtendedSSLSession extendedSSLSession) {
      return new LegacyCompatibilityExtendedSSLSession(extendedSSLSession);
    } else {
      return new LegacyCompatibilitySSLSession(delegate);
    }
  }

  @Override
  public void addHandshakeCompletedListener(HandshakeCompletedListener listener) {
    delegate.addHandshakeCompletedListener(listener);
  }

  @Override
  public void removeHandshakeCompletedListener(HandshakeCompletedListener listener) {
    delegate.removeHandshakeCompletedListener(listener);
  }

  @Override
  public void startHandshake() throws IOException {
    delegate.startHandshake();
  }

  @Override
  public void connect(SocketAddress endpoint) throws IOException {
    delegate.connect(endpoint);
  }

  @Override
  public void setUseClientMode(boolean mode) {
    delegate.setUseClientMode(mode);
  }

  @Override
  public void connect(SocketAddress endpoint, int timeout) throws IOException {
    delegate.connect(endpoint, timeout);
  }

  @Override
  public boolean getUseClientMode() {
    return delegate.getUseClientMode();
  }

  @Override
  public void setNeedClientAuth(boolean need) {
    delegate.setNeedClientAuth(need);
  }

  @Override
  public boolean getNeedClientAuth() {
    return delegate.getNeedClientAuth();
  }

  @Override
  public void bind(SocketAddress bindpoint) throws IOException {
    delegate.bind(bindpoint);
  }

  @Override
  public void setWantClientAuth(boolean want) {
    delegate.setWantClientAuth(want);
  }

  @Override
  public boolean getWantClientAuth() {
    return delegate.getWantClientAuth();
  }

  @Override
  public void setEnableSessionCreation(boolean flag) {
    delegate.setEnableSessionCreation(flag);
  }

  @Override
  public InetAddress getInetAddress() {
    return delegate.getInetAddress();
  }

  @Override
  public boolean getEnableSessionCreation() {
    return delegate.getEnableSessionCreation();
  }

  @Override
  public SSLParameters getSSLParameters() {
    return delegate.getSSLParameters();
  }

  @Override
  public InetAddress getLocalAddress() {
    return delegate.getLocalAddress();
  }

  @Override
  public void setSSLParameters(SSLParameters params) {
    delegate.setSSLParameters(params);
  }

  @Override
  public int getPort() {
    return delegate.getPort();
  }

  @Override
  public int getLocalPort() {
    return delegate.getLocalPort();
  }

  @Override
  public String getApplicationProtocol() {
    return delegate.getApplicationProtocol();
  }

  @Override
  public SocketAddress getRemoteSocketAddress() {
    return delegate.getRemoteSocketAddress();
  }

  @Override
  public String getHandshakeApplicationProtocol() {
    return delegate.getHandshakeApplicationProtocol();
  }

  @Override
  public SocketAddress getLocalSocketAddress() {
    return delegate.getLocalSocketAddress();
  }

  @Override
  public void setHandshakeApplicationProtocolSelector(BiFunction<SSLSocket, List<String>, String> selector) {
    delegate.setHandshakeApplicationProtocolSelector(selector);
  }

  @Override
  public SocketChannel getChannel() {
    return this.channel;
  }

  @Override
  public InputStream getInputStream() throws IOException {
    return delegate.getInputStream();
  }

  @Override
  public BiFunction<SSLSocket, List<String>, String> getHandshakeApplicationProtocolSelector() {
    return delegate.getHandshakeApplicationProtocolSelector();
  }

  @Override
  public OutputStream getOutputStream() throws IOException {
    return delegate.getOutputStream();
  }

  @Override
  public void setTcpNoDelay(boolean on) throws SocketException {
    delegate.setTcpNoDelay(on);
  }

  @Override
  public boolean getTcpNoDelay() throws SocketException {
    return delegate.getTcpNoDelay();
  }

  @Override
  public void setSoLinger(boolean on, int linger) throws SocketException {
    delegate.setSoLinger(on, linger);
  }

  @Override
  public int getSoLinger() throws SocketException {
    return delegate.getSoLinger();
  }

  @Override
  public void sendUrgentData(int data) throws IOException {
    delegate.sendUrgentData(data);
  }

  @Override
  public void setOOBInline(boolean on) throws SocketException {
    delegate.setOOBInline(on);
  }

  @Override
  public boolean getOOBInline() throws SocketException {
    return delegate.getOOBInline();
  }

  @Override
  public void setSoTimeout(int timeout) throws SocketException {
    delegate.setSoTimeout(timeout);
  }

  @Override
  public int getSoTimeout() throws SocketException {
    return delegate.getSoTimeout();
  }

  @Override
  public void setSendBufferSize(int size) throws SocketException {
    delegate.setSendBufferSize(size);
  }

  @Override
  public int getSendBufferSize() throws SocketException {
    return delegate.getSendBufferSize();
  }

  @Override
  public void setReceiveBufferSize(int size) throws SocketException {
    delegate.setReceiveBufferSize(size);
  }

  @Override
  public int getReceiveBufferSize() throws SocketException {
    return delegate.getReceiveBufferSize();
  }

  @Override
  public void setKeepAlive(boolean on) throws SocketException {
    delegate.setKeepAlive(on);
  }

  @Override
  public boolean getKeepAlive() throws SocketException {
    return delegate.getKeepAlive();
  }

  @Override
  public void setTrafficClass(int tc) throws SocketException {
    delegate.setTrafficClass(tc);
  }

  @Override
  public int getTrafficClass() throws SocketException {
    return delegate.getTrafficClass();
  }

  @Override
  public void setReuseAddress(boolean on) throws SocketException {
    delegate.setReuseAddress(on);
  }

  @Override
  public boolean getReuseAddress() throws SocketException {
    return delegate.getReuseAddress();
  }

  @Override
  public void close() throws IOException {
    delegate.close();
  }

  @Override
  public void shutdownInput() throws IOException {
    delegate.shutdownInput();
  }

  @Override
  public void shutdownOutput() throws IOException {
    delegate.shutdownOutput();
  }

  @Override
  public boolean isConnected() {
    return delegate.isConnected();
  }

  @Override
  public boolean isBound() {
    return delegate.isBound();
  }

  @Override
  public boolean isClosed() {
    return delegate.isClosed();
  }

  @Override
  public boolean isInputShutdown() {
    return delegate.isInputShutdown();
  }

  @Override
  public boolean isOutputShutdown() {
    return delegate.isOutputShutdown();
  }

  @Override
  public void setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
    delegate.setPerformancePreferences(connectionTime, latency, bandwidth);
  }

  @Override
  public <T> Socket setOption(SocketOption<T> name, T value) throws IOException {
    delegate.setOption(name, value);
    return this;
  }

  @Override
  public <T> T getOption(SocketOption<T> name) throws IOException {
    return delegate.getOption(name);
  }

  @Override
  public Set<SocketOption<?>> supportedOptions() {
    return delegate.supportedOptions();
  }



}
