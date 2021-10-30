package com.code.httpserver;

import com.code.httpserver.config.Configuration;
import com.code.httpserver.config.ConfigurationManager;
import com.code.httpserver.core.ServerListenerThread;

import java.io.IOException;

/* Driver class for the Http Server*/
public class HttpServer {

    public static void main(String[] args){
        System.out.println("hei");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        System.out.println("Using Port: " + conf.getPort());
        System.out.println("Using Webroot: " + conf.getWebroot());

        try{
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
