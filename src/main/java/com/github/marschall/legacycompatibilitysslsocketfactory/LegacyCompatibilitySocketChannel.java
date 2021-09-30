package com.github.marschall.legacycompatibilitysslsocketfactory;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Set;

final class LegacyCompatibilitySocketChannel extends SocketChannel {

  private SocketChannel delegateChannel;
  private Socket delegateSocket;

  LegacyCompatibilitySocketChannel(SelectorProvider provider, SocketChannel delegateChannel, Socket socket) {
    super(provider);
    this.delegateChannel = delegateChannel;
    this.delegateSocket = socket;
  }

  public <T> T getOption(SocketOption<T> name) throws IOException {
    return delegateChannel.getOption(name);
  }

  public Set<SocketOption<?>> supportedOptions() {
    return delegateChannel.supportedOptions();
  }

  public SocketChannel bind(SocketAddress local) throws IOException {
    delegateChannel.bind(local);
    return this;
  }

  public <T> SocketChannel setOption(SocketOption<T> name, T value) throws IOException {
    delegateChannel.setOption(name, value);
    return this;
  }

  public SocketChannel shutdownInput() throws IOException {
    delegateChannel.shutdownInput();
    return this;
  }

  public SocketChannel shutdownOutput() throws IOException {
    delegateChannel.shutdownOutput();
    return this;
  }

  public Socket socket() {
    return delegateSocket;
  }

  public boolean isConnected() {
    return delegateChannel.isConnected();
  }

  public boolean isConnectionPending() {
    return delegateChannel.isConnectionPending();
  }

  public boolean connect(SocketAddress remote) throws IOException {
    return delegateChannel.connect(remote);
  }

  public boolean finishConnect() throws IOException {
    return delegateChannel.finishConnect();
  }

  public SocketAddress getRemoteAddress() throws IOException {
    return delegateChannel.getRemoteAddress();
  }

  public int read(ByteBuffer dst) throws IOException {
    return delegateChannel.read(dst);
  }

  public long read(ByteBuffer[] dsts, int offset, int length) throws IOException {
    return delegateChannel.read(dsts, offset, length);
  }

  public int write(ByteBuffer src) throws IOException {
    return delegateChannel.write(src);
  }

  public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
    return delegateChannel.write(srcs, offset, length);
  }

  public SocketAddress getLocalAddress() throws IOException {
    return delegateChannel.getLocalAddress();
  }

  @Override
  protected void implCloseSelectableChannel() throws IOException {
    this.delegateChannel.close();
  }

  @Override
  protected void implConfigureBlocking(boolean block) throws IOException {
    this.delegateChannel.configureBlocking(block);
  }

}
