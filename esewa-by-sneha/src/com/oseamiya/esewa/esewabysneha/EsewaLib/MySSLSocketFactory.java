package com.oseamiya.esewa.esewabysneha.EsewaLib;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class MySSLSocketFactory extends SSLSocketFactory {
   SSLSocketFactory sslSocketFactory;

   public MySSLSocketFactory(SSLSocketFactory sslSocketFactory) {
      this.sslSocketFactory = sslSocketFactory;
   }

   public String[] getDefaultCipherSuites() {
      return this.sslSocketFactory.getDefaultCipherSuites();
   }

   public String[] getSupportedCipherSuites() {
      return this.sslSocketFactory.getSupportedCipherSuites();
   }

   public SSLSocket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
      SSLSocket socket = (SSLSocket)this.sslSocketFactory.createSocket(s, host, port, autoClose);
      socket.setEnabledProtocols(new String[]{"TLSv1.2"});
      return socket;
   }

   public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
      SSLSocket socket = (SSLSocket)this.sslSocketFactory.createSocket(host, port);
      socket.setEnabledProtocols(new String[]{"TLSv1.2"});
      return socket;
   }

   public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
      SSLSocket socket = (SSLSocket)this.sslSocketFactory.createSocket(host, port, localHost, localPort);
      socket.setEnabledProtocols(new String[]{"TLSv1.2"});
      return socket;
   }

   public Socket createSocket(InetAddress host, int port) throws IOException {
      SSLSocket socket = (SSLSocket)this.sslSocketFactory.createSocket(host, port);
      socket.setEnabledProtocols(new String[]{"TLSv1.2"});
      return socket;
   }

   public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
      SSLSocket socket = (SSLSocket)this.sslSocketFactory.createSocket(address, port, localAddress, localPort);
      socket.setEnabledProtocols(new String[]{"TLSv1.2"});
      return socket;
   }
}