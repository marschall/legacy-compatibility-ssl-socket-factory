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
    return this.delegate.getSupportedCipherSuites();
  }

  @Override
  public String[] getEnabledCipherSuites() {
    return this.delegate.getEnabledCipherSuites();
  }

  @Override
  public void setEnabledCipherSuites(String[] suites) {
    this.delegate.setEnabledCipherSuites(suites);
  }

  @Override
  public String[] getSupportedProtocols() {
    return this.delegate.getSupportedProtocols();
  }

  @Override
  public String[] getEnabledProtocols() {
    return this.delegate.getEnabledProtocols();
  }

  @Override
  public void setEnabledProtocols(String[] protocols) {
    this.delegate.setEnabledProtocols(protocols);
  }

  @Override
  public SSLSession getSession() {
    return adaptSSLSession(this.delegate.getSession());
  }

  @Override
  public SSLSession getHandshakeSession() {
    return adaptSSLSession(this.delegate.getHandshakeSession());
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
    this.delegate.addHandshakeCompletedListener(listener);
  }

  @Override
  public void removeHandshakeCompletedListener(HandshakeCompletedListener listener) {
    this.delegate.removeHandshakeCompletedListener(listener);
  }

  @Override
  public void startHandshake() throws IOException {
    this.delegate.startHandshake();
  }

  @Override
  public void connect(SocketAddress endpoint) throws IOException {
    this.delegate.connect(endpoint);
  }

  @Override
  public void setUseClientMode(boolean mode) {
    this.delegate.setUseClientMode(mode);
  }

  @Override
  public void connect(SocketAddress endpoint, int timeout) throws IOException {
    this.delegate.connect(endpoint, timeout);
  }

  @Override
  public boolean getUseClientMode() {
    return this.delegate.getUseClientMode();
  }

  @Override
  public void setNeedClientAuth(boolean need) {
    this.delegate.setNeedClientAuth(need);
  }

  @Override
  public boolean getNeedClientAuth() {
    return this.delegate.getNeedClientAuth();
  }

  @Override
  public void bind(SocketAddress bindpoint) throws IOException {
    this.delegate.bind(bindpoint);
  }

  @Override
  public void setWantClientAuth(boolean want) {
    this.delegate.setWantClientAuth(want);
  }

  @Override
  public boolean getWantClientAuth() {
    return this.delegate.getWantClientAuth();
  }

  @Override
  public void setEnableSessionCreation(boolean flag) {
    this.delegate.setEnableSessionCreation(flag);
  }

  @Override
  public InetAddress getInetAddress() {
    return this.delegate.getInetAddress();
  }

  @Override
  public boolean getEnableSessionCreation() {
    return this.delegate.getEnableSessionCreation();
  }

  @Override
  public SSLParameters getSSLParameters() {
    return this.delegate.getSSLParameters();
  }

  @Override
  public InetAddress getLocalAddress() {
    return this.delegate.getLocalAddress();
  }

  @Override
  public void setSSLParameters(SSLParameters params) {
    this.delegate.setSSLParameters(params);
  }

  @Override
  public int getPort() {
    return this.delegate.getPort();
  }

  @Override
  public int getLocalPort() {
    return this.delegate.getLocalPort();
  }

  @Override
  public String getApplicationProtocol() {
    return this.delegate.getApplicationProtocol();
  }

  @Override
  public SocketAddress getRemoteSocketAddress() {
    return this.delegate.getRemoteSocketAddress();
  }

  @Override
  public String getHandshakeApplicationProtocol() {
    return this.delegate.getHandshakeApplicationProtocol();
  }

  @Override
  public SocketAddress getLocalSocketAddress() {
    return this.delegate.getLocalSocketAddress();
  }

  @Override
  public void setHandshakeApplicationProtocolSelector(BiFunction<SSLSocket, List<String>, String> selector) {
    this.delegate.setHandshakeApplicationProtocolSelector(selector);
  }

  @Override
  public SocketChannel getChannel() {
    return this.channel;
  }

  @Override
  public InputStream getInputStream() throws IOException {
    return this.delegate.getInputStream();
  }

  @Override
  public BiFunction<SSLSocket, List<String>, String> getHandshakeApplicationProtocolSelector() {
    return this.delegate.getHandshakeApplicationProtocolSelector();
  }

  @Override
  public OutputStream getOutputStream() throws IOException {
    return this.delegate.getOutputStream();
  }

  @Override
  public void setTcpNoDelay(boolean on) throws SocketException {
    this.delegate.setTcpNoDelay(on);
  }

  @Override
  public boolean getTcpNoDelay() throws SocketException {
    return this.delegate.getTcpNoDelay();
  }

  @Override
  public void setSoLinger(boolean on, int linger) throws SocketException {
    this.delegate.setSoLinger(on, linger);
  }

  @Override
  public int getSoLinger() throws SocketException {
    return this.delegate.getSoLinger();
  }

  @Override
  public void sendUrgentData(int data) throws IOException {
    this.delegate.sendUrgentData(data);
  }

  @Override
  public void setOOBInline(boolean on) throws SocketException {
    this.delegate.setOOBInline(on);
  }

  @Override
  public boolean getOOBInline() throws SocketException {
    return this.delegate.getOOBInline();
  }

  @Override
  public void setSoTimeout(int timeout) throws SocketException {
    this.delegate.setSoTimeout(timeout);
  }

  @Override
  public int getSoTimeout() throws SocketException {
    return this.delegate.getSoTimeout();
  }

  @Override
  public void setSendBufferSize(int size) throws SocketException {
    this.delegate.setSendBufferSize(size);
  }

  @Override
  public int getSendBufferSize() throws SocketException {
    return this.delegate.getSendBufferSize();
  }

  @Override
  public void setReceiveBufferSize(int size) throws SocketException {
    this.delegate.setReceiveBufferSize(size);
  }

  @Override
  public int getReceiveBufferSize() throws SocketException {
    return this.delegate.getReceiveBufferSize();
  }

  @Override
  public void setKeepAlive(boolean on) throws SocketException {
    this.delegate.setKeepAlive(on);
  }

  @Override
  public boolean getKeepAlive() throws SocketException {
    return this.delegate.getKeepAlive();
  }

  @Override
  public void setTrafficClass(int tc) throws SocketException {
    this.delegate.setTrafficClass(tc);
  }

  @Override
  public int getTrafficClass() throws SocketException {
    return this.delegate.getTrafficClass();
  }

  @Override
  public void setReuseAddress(boolean on) throws SocketException {
    this.delegate.setReuseAddress(on);
  }

  @Override
  public boolean getReuseAddress() throws SocketException {
    return this.delegate.getReuseAddress();
  }

  @Override
  public void close() throws IOException {
    this.delegate.close();
  }

  @Override
  public void shutdownInput() throws IOException {
    this.delegate.shutdownInput();
  }

  @Override
  public void shutdownOutput() throws IOException {
    this.delegate.shutdownOutput();
  }

  @Override
  public boolean isConnected() {
    return this.delegate.isConnected();
  }

  @Override
  public boolean isBound() {
    return this.delegate.isBound();
  }

  @Override
  public boolean isClosed() {
    return this.delegate.isClosed();
  }

  @Override
  public boolean isInputShutdown() {
    return this.delegate.isInputShutdown();
  }

  @Override
  public boolean isOutputShutdown() {
    return this.delegate.isOutputShutdown();
  }

  @Override
  public void setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
    this.delegate.setPerformancePreferences(connectionTime, latency, bandwidth);
  }

  @Override
  public <T> Socket setOption(SocketOption<T> name, T value) throws IOException {
    this.delegate.setOption(name, value);
    return this;
  }

  @Override
  public <T> T getOption(SocketOption<T> name) throws IOException {
    return this.delegate.getOption(name);
  }

  @Override
  public Set<SocketOption<?>> supportedOptions() {
    return this.delegate.supportedOptions();
  }



}
