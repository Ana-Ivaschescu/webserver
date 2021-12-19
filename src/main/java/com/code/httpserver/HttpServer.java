package com.code.httpserver;

import com.code.gui.GuiFrame;
import com.code.httpserver.config.Configuration;
import com.code.httpserver.config.ConfigurationManager;
import com.code.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/* Driver class for the Http Server*/
public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args){
        LOGGER.info("Server starting");
        GuiFrame gui = new GuiFrame();
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        LOGGER.info("Using Port: " + conf.getPort());
        LOGGER.info("Using Webroot: " + conf.getWebroot());

        try{
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
