package com.code.httpserver.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationManagerTest {

    @Test
     void loadConfigurationFileTest() {

        Assertions.assertThrows(HttpConfigurationException.class, () -> {
            String filePath = "de_negasit";
            final ConfigurationManager configManager = ConfigurationManager.getInstance();
            configManager.loadConfigurationFile(filePath);
        });

    }

    @Test
    void getCurrentConfigTest(){
        HttpConfigurationException exception = Assertions.assertThrows(HttpConfigurationException.class, () -> {
            final ConfigurationManager configManager = ConfigurationManager.getInstance();
            //final Field field = configManager.getClass().getDeclaredField("myCurrentConfiguration");
            configManager.getCurrentConfiguration();
        });
        Assertions.assertEquals("No Current Configuration Set", exception.getMessage());
    }

}