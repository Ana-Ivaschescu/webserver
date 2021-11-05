package com.code.httpserver.config;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {

    @Test
    public void webroot_getValue() throws NoSuchFieldException, IllegalAccessException{
        final Configuration config = new Configuration();
        final Field field = config.getClass().getDeclaredField("webroot");
        field.setAccessible(true);
        field.set(config, "testWebroot");

        final String result = config.getWebroot();

        assertEquals(result, "testWebroot");
    }

    @Test
    public void port_getValue() throws NoSuchFieldException, IllegalAccessException{
        final Configuration config = new Configuration();
        final Field field = config.getClass().getDeclaredField("port");
        field.setAccessible(true);
        field.set(config, 1234);

        final int result = config.getPort();

        assertEquals(result, 1234);
    }

    @Test
    public void webroot_setValue() throws NoSuchFieldException, IllegalAccessException{
        final Configuration config = new Configuration();
        config.setWebroot("testWebroot");
        final Field field = config.getClass().getDeclaredField("webroot");
        field.setAccessible(true);
        assertEquals(field.get(config), "testWebroot");
    }

    @Test
    public void port_setValue() throws NoSuchFieldException, IllegalAccessException{
        final Configuration config = new Configuration();
        config.setPort(1234);
        final Field field = config.getClass().getDeclaredField("port");
        field.setAccessible(true);
        assertEquals(field.get(config), 1234);
    }
}