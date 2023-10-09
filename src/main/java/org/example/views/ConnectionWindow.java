package org.example.views;

import org.example.Main;
import org.example.controllers.background.GetClient;
import org.example.fluent.FluentDialog;
import org.example.fluent.FluentFrame;
import org.example.fluent.colors.FluentColors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ConnectionWindow extends FluentDialog {
    public String MacAddress = "";
    private JPanel mainPanel;
    private ConnectionPanel connectPanel;
    private final GetClient getClient = new GetClient(this);

    public void changeToAddPanel() {
        SwingUtilities.invokeLater(() -> {
            setTitle("Add folder");
            AddWatchFolderPanel addWatchFolderPanel = new AddWatchFolderPanel(getClient.getClientIndex());
            MacAddress = Main.getClient(getClient.getClientIndex()).MacValue;
            mainPanel.add(addWatchFolderPanel, "Add Folder");
            CardLayout layout = (CardLayout) mainPanel.getLayout();
            layout.show(mainPanel, "Add Folder");
            System.out.println("Layout changed successfully.");
        });
    }

    public ConnectionWindow(FluentFrame owner) {
        super(owner, FluentColors.FRAME_BACKGROUND, "Connect");
        Runnable mainFrame = () -> {
            mainPanel = new JPanel(new CardLayout());
            setSize(550, 500);
//            setResizable(false);
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            Thread getClientsThread = new Thread(getClient);
            getClientsThread.start();

            connectPanel = new ConnectionPanel();
            connectPanel.setVisible(true);
            mainPanel.add(connectPanel, "Connection");
            add(mainPanel);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            this.setLocation(dim.width/2-this.getWidth()/2, dim.height/2-this.getHeight()/2);
            setVisible(true);

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    getClient.stop();
                }
            });
        };
        SwingUtilities.invokeLater(mainFrame);
    }

    public void setCode(String s) {
        connectPanel.setCode(s);
    }

    @Override
    public void dispose() {
        super.dispose();
        getClient.stop();
    }
}