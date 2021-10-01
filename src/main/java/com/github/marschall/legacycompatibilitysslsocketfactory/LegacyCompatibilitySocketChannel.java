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

  private final SocketChannel delegateChannel;
  private final Socket delegateSocket;

  LegacyCompatibilitySocketChannel(SelectorProvider provider, SocketChannel delegateChannel, Socket socket) {
    super(provider);
    this.delegateChannel = delegateChannel;
    this.delegateSocket = socket;
  }

  @Override
  public <T> T getOption(SocketOption<T> name) throws IOException {
    return this.delegateChannel.getOption(name);
  }

  @Override
  public Set<SocketOption<?>> supportedOptions() {
    return this.delegateChannel.supportedOptions();
  }

  @Override
  public SocketChannel bind(SocketAddress local) throws IOException {
    this.delegateChannel.bind(local);
    return this;
  }

  @Override
  public <T> SocketChannel setOption(SocketOption<T> name, T value) throws IOException {
    this.delegateChannel.setOption(name, value);
    return this;
  }

  @Override
  public SocketChannel shutdownInput() throws IOException {
    this.delegateChannel.shutdownInput();
    return this;
  }

  @Override
  public SocketChannel shutdownOutput() throws IOException {
    this.delegateChannel.shutdownOutput();
    return this;
  }

  @Override
  public Socket socket() {
    return this.delegateSocket;
  }

  @Override
  public boolean isConnected() {
    return this.delegateChannel.isConnected();
  }

  @Override
  public boolean isConnectionPending() {
    return this.delegateChannel.isConnectionPending();
  }

  @Override
  public boolean connect(SocketAddress remote) throws IOException {
    return this.delegateChannel.connect(remote);
  }

  @Override
  public boolean finishConnect() throws IOException {
    return this.delegateChannel.finishConnect();
  }

  @Override
  public SocketAddress getRemoteAddress() throws IOException {
    return this.delegateChannel.getRemoteAddress();
  }

  @Override
  public int read(ByteBuffer dst) throws IOException {
    return this.delegateChannel.read(dst);
  }

  @Override
  public long read(ByteBuffer[] dsts, int offset, int length) throws IOException {
    return this.delegateChannel.read(dsts, offset, length);
  }

  @Override
  public int write(ByteBuffer src) throws IOException {
    return this.delegateChannel.write(src);
  }

  @Override
  public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
    return this.delegateChannel.write(srcs, offset, length);
  }

  @Override
  public SocketAddress getLocalAddress() throws IOException {
    return this.delegateChannel.getLocalAddress();
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
