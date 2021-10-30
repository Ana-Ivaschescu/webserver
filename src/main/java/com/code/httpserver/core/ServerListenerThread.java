package com.code.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread{
   private int port;
   private String webroot;
   private ServerSocket serverSocket;
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

   public ServerListenerThread(int port, String webroot) throws IOException {
       this.port = port;
       this.webroot = webroot;
       this.serverSocket = new ServerSocket(this.port);
   }

    /*listening to a certain port and accept any connections that arise*/
   public void run(){
       try {
           Socket socket = serverSocket.accept();

           LOGGER.info("Connection accepted: " + socket.getInetAddress());

           InputStream inputStream = socket.getInputStream();
           OutputStream outputStream = socket.getOutputStream();

           String html = "<html><head><title>Simple Java HTTP Server </title></head><body><h1>This page was served with my server</h1></body></html>";
           final String CRLF = "\r\n"; //13,10
           String response =
                   "HTTP/1.1 200 OK" + CRLF + //Status line: http version response_code response_message
                           "Content-Length: " + html.getBytes().length + CRLF + //header
                           CRLF +
                           html +
                           CRLF + CRLF;

           outputStream.write(response.getBytes());

           inputStream.close();
           outputStream.close();
           socket.close();
           serverSocket.close();

       } catch (IOException e) {
           e.printStackTrace();
       }
   }

}
