package com.code.httpserver.config;

import com.code.httpserver.util.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/* singleton class*/
public class ConfigurationManager {
    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager(){

    }

    public static ConfigurationManager getInstance(){
        if(myConfigurationManager == null)
            myConfigurationManager = new ConfigurationManager();
        return myConfigurationManager;
    }

    /* used for loading a config file by the path provider*/
    public void loadConfigurationFile(String filePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuilder sb = new StringBuilder();
        int i;
        try{
            while((i = fileReader.read()) != -1) {
                sb.append((char) i);
            }
            fileReader.close();
        }catch (IOException e){
            throw new HttpConfigurationException(e);
        }

        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing the Configuration File", e);
        }
        try {
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the Configuration File, internal", e);
        }
    }

    /* returns the current loaded config*/
    public Configuration getCurrentConfiguration(){
        if(myCurrentConfiguration == null){
            throw new HttpConfigurationException("No Current Configuration Set");
        }
        return myCurrentConfiguration;
    }
}
