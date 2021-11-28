package com.code.httpserver.config;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {

    @Test
    void webroot_getValue() throws NoSuchFieldException, IllegalAccessException{
        final Configuration config = new Configuration();
        final Field field = config.getClass().getDeclaredField("webroot");
        field.setAccessible(true);
        field.set(config, "testWebroot");

        final String result = config.getWebroot();

        assertEquals( "testWebroot", result);
    }

    @Test
    void port_getValue() throws NoSuchFieldException, IllegalAccessException{
        final Configuration config = new Configuration();
        final Field field = config.getClass().getDeclaredField("port");
        field.setAccessible(true);
        field.set(config, 1234);

        final int result = config.getPort();

        assertEquals(1234, result);
    }

    @Test
    void webroot_setValue() throws NoSuchFieldException, IllegalAccessException{
        final Configuration config = new Configuration();
        config.setWebroot("testWebroot");
        final Field field = config.getClass().getDeclaredField("webroot");
        field.setAccessible(true);
        assertEquals("testWebroot", field.get(config));
    }

    @Test
    void port_setValue() throws NoSuchFieldException, IllegalAccessException{
        final Configuration config = new Configuration();
        config.setPort(1234);
        final Field field = config.getClass().getDeclaredField("port");
        field.setAccessible(true);
        assertEquals( 1234, field.get(config));
    }
}