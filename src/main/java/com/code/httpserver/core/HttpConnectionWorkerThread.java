package com.code.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread{

    private Socket socket;
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    public HttpConnectionWorkerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            String html = "<html><head><title>Simple Java HTTP Server </title></head><body><h1>This page was served with my server</h1></body></html>";
            final String CRLF = "\r\n"; //13,10
            String response =
                    "HTTP/1.1 200 OK" + CRLF + //Status line: http version response_code response_message
                            "Content-Length: " + html.getBytes().length + CRLF + //header
                            CRLF +
                            html +
                            CRLF + CRLF;

            outputStream.write(response.getBytes());



            LOGGER.info("Connection Processing Finished");
        } catch (IOException e) {
            LOGGER.error("Problem with communication", e);
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {}
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
    }
}
