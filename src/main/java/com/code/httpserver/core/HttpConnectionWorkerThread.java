package com.code.httpserver.core;

import com.code.http.Code;
import com.code.http.HttpParser;
import com.code.http.HttpParsingException;
import com.code.http.HttpRequest;

import com.code.httpserver.config.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread{

    private Socket socket;
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    public HttpConnectionWorkerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        Code code = Code.getInstance();

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            HttpParser parser = new HttpParser();
            HttpRequest request = parser.parseHttpRequest(inputStream);

            //System.out.println(request.getRequestTarget());

            outputStream = socket.getOutputStream();

            if(!request.getMethod().toString().equals("GET") && !request.getMethod().toString().equals("HEAD")){
                String result = getFileContent("not_supported.html");
                final String CRLF = "\r\n"; //13,10
                    String response =
                            "HTTP/1.1 501 Not Implemented" + CRLF + //Status line: http version response_code response_message
                                    "Content-Length: " + result.getBytes().length + CRLF + //header
                                    CRLF +
                                    result +
                                    CRLF + CRLF;

                    code.setCode(501);
                    outputStream.write(response.getBytes());
            } else {
                if(request.getRequestTarget() == null || request.getRequestTarget().isEmpty() || request.getRequestTarget().equals("/")){
                    String html = getFileContent("index.html");
                    final String CRLF = "\r\n"; //13,10
                    String response =
                            "HTTP/1.1 200 OK" + CRLF + //Status line: http version response_code response_message
                            "Content-Length: " + html.getBytes().length + CRLF + //header
                            CRLF +
                            html +
                            CRLF + CRLF;

                    code.setCode(200);
                    outputStream.write(response.getBytes());
                }
                else{
                    String filePath = ConfigurationManager.getInstance().getCurrentConfiguration().getWebroot() + request.getRequestTarget();
                    if(fileExists(filePath)){
                        FileReader fileReader = new FileReader(filePath);
                        StringBuilder html = new StringBuilder();
                        try{
                            BufferedReader bReader = new BufferedReader(fileReader);
                            String val;
                            while((val = bReader.readLine())!= null){
                                html.append(val);
                            }
                            String result = html.toString();
                            bReader.close();
                            fileReader.close();
                            final String CRLF = "\r\n"; //13,10
                            String response =
                                    "HTTP/1.1 200 OK" + CRLF + //Status line: http version response_code response_message
                                            "Content-Length: " + result.getBytes().length + CRLF + //header
                                            CRLF +
                                            result +
                                            CRLF + CRLF;

                           code.setCode(200);
                           outputStream.write(response.getBytes());
                        }catch(Exception e){
                            System.out.println(e);
                        }
                    } else{
                        String result = getFileContent("file_not_found.html");
                        final String CRLF = "\r\n"; //13,10
                        String response =
                                "HTTP/1.1 404 File Not Found" + CRLF + //Status line: http version response_code response_message
                                        "Content-Length: " + result.getBytes().length + CRLF + //header
                                        CRLF +
                                        result +
                                        CRLF + CRLF;
                        code.setCode(404);
                        outputStream.write(response.getBytes());
                    }

                }
            }
            LOGGER.info("Connection Processing Finished");
        } catch (IOException | HttpParsingException e) {
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

    public String getFileContent(String path){
        StringBuilder html = new StringBuilder();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inpStream = classloader.getResourceAsStream(path);
        InputStreamReader inpStreamReader = new InputStreamReader(inpStream);
        String result = new String();
        try{
            BufferedReader bReader = new BufferedReader(inpStreamReader);
            String val;
            while((val = bReader.readLine())!= null){
                html.append(val);
            }
            result = html.toString();
            bReader.close();
            inpStream.close();

        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }

    public boolean fileExists(String path){
        File file = new File(path);
        return file.exists();
    }
}
