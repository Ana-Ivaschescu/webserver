package com.code.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpVersionTest {

    @Test
    void getBestCompatibleVersionExactMatch(){
        HttpVersion version = null;
        try {
            version = HttpVersion.getBestCompatibleVersion("HTTP/1.1");
        } catch (BadHttpParsingException e) {
            fail();
        }

        assertNotNull(version);
        assertEquals( HttpVersion.HTTP_1_1, version);

    }

    @Test
    void getBestCompatibleVersionBadFormat(){
        HttpVersion version = null;
        try {
            version = HttpVersion.getBestCompatibleVersion("http/1.1");
            fail();
        } catch (BadHttpParsingException e) {

        }

    }

    @Test
    void getBestCompatibleVersionHigherVersion(){
        HttpVersion version = null;
        try {
            version = HttpVersion.getBestCompatibleVersion("HTTP/1.2");
            assertNotNull(version);
            assertEquals( HttpVersion.HTTP_1_1, version);

        } catch (BadHttpParsingException e) {
            fail();
        }
    }
}
