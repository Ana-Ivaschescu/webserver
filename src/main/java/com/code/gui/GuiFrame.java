package com.code.gui;

import com.code.http.Code;
import com.code.httpserver.config.Configuration;
import com.code.httpserver.config.ConfigurationManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class GuiFrame extends JFrame {
    JPanel p;
    JLabel root, port, message;
    JButton rootButton, portButton, startButton;
    JTextField link;

    public GuiFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Webserver");
        this.setSize(320, 320);
        this.setVisible(true);

        p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        BoxLayout boxlayout = new BoxLayout(p, BoxLayout.Y_AXIS);
        p.setLayout(boxlayout);

        rootButton = new JButton("Get root");
        rootButton.addActionListener(e -> rootButtonPressed());
        portButton = new JButton("Get port");
        portButton.addActionListener(e -> portButtonPressed());
        startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            try {
                startButtonPressed();
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        root = new JLabel();
        port = new JLabel();
        message = new JLabel();
        link = new JTextField("http://localhost:");


        p.add(rootButton);
        p.add(root);
        p.add(portButton);
        p.add(port);
        p.add(link);
        p.add(startButton);
        p.add(message);

        this.add(p);
    }

    void rootButtonPressed(){
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        root.setText(conf.getWebroot());
    }

    void portButtonPressed(){
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        port.setText("Port: " + conf.getPort());
    }

    void startButtonPressed() throws URISyntaxException, IOException, InterruptedException {
        String s = link.getText();
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(new URI(s));
        TimeUnit.SECONDS.sleep(3);
        message.setText("Http Status Code: " + Code.getInstance().getCode());
    }

}
