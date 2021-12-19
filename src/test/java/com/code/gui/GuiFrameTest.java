package com.code.gui;

import com.code.http.Code;
import com.code.httpserver.config.ConfigurationManager;
import com.code.httpserver.core.ServerListenerThread;
import org.junit.jupiter.api.*;

import javax.swing.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
class GuiFrameTest {
    private GuiFrame gui;

    @BeforeAll
    public void setup(){
        gui = new GuiFrame();
        gui.port = new JLabel();
        gui.root = new JLabel();
        gui.rootButton = new JButton();
        gui.portButton = new JButton();
        gui.link = new JTextField("http://localhost:");
        gui.message = new JLabel();
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
    }

    @Test
    void generalTest(){
        assertNotNull(gui.port);
        assertNotNull(gui.root);
        assertNotNull(gui.portButton);
        assertNotNull(gui.rootButton);
        assertNotNull(gui.link);
        int height = gui.getHeight();
        int width = gui.getWidth();
        assertEquals(320, height);
        assertEquals(320, width);
        assertEquals("Webserver", gui.getTitle());
    }

    @Test
    void rootButtonPressedTest(){
        gui.rootButtonPressed();
        assertEquals( "C:\\xampp\\htdocs\\site-html", gui.root.getText());
    }

    @Test
    void portButtonPressedTest(){
        gui.portButtonPressed();
        assertEquals( "Port: 1234", gui.port.getText());
    }

    @Test
    void linkValueTest(){
        assertEquals("http://localhost:", gui.link.getText());
    }

    @Test
    void startTest() throws URISyntaxException, IOException, InterruptedException {
        gui.link.setText("http://localhost:1234");
        gui.startButtonPressed();
        try{
            ServerListenerThread serverListenerThread = new ServerListenerThread(ConfigurationManager.getInstance().getCurrentConfiguration().getPort(), ConfigurationManager.getInstance().getCurrentConfiguration().getWebroot());
            serverListenerThread.start();
        }catch (IOException e){
            e.printStackTrace();
        }
        TimeUnit.SECONDS.sleep(3);
        //assertEquals("Http Status Code: 200", gui.message.getText());
        assertEquals(200, Code.getInstance().getCode());
    }
}