package com.code.httpserver.config;

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
    public void loadConfigurationFile(String filePath){

    }

    /* returns the current loaded config*/
    public void getCurrentConfiguration(){

    }
}
