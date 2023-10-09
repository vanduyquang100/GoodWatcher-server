package org.example;

import org.example.controllers.background.ClientSocketThread;
import org.example.fluent.colors.FluentColors;
import org.example.fluent.fonts.FluentFonts;
import org.example.views.MainWindow;
import org.example.views.WatchedItem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {

    private static DefaultListModel<ClientSocketThread> clientList;
    public static JPanel watchedItemPanel;
    public static ArrayList<JPanel> watchedItems;
    private static void init() {
            new FluentFonts();
            System.setProperty("sun.java2d.uiScale", "1.0");
            clientList = new DefaultListModel<>();
            watchedItems = new ArrayList<>();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    watchedItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    watchedItemPanel.setBackground(FluentColors.FRAME_BACKGROUND);
                    watchedItemPanel.setOpaque(false);
                    watchedItemPanel.setFocusable(false);
                }
            });
    }

    public static int addClient(ClientSocketThread client) {
        if (client.MacValue.isEmpty() || client.clientName.isEmpty()) {
            return -1;
        }

        for (int index = 0; index < clientList.size(); ++index) {
            if (client.MacValue.equals(clientList.get(index).MacValue)) {
                final int finalIndex = index;
                SwingUtilities.invokeLater(()->{
                    clientList.set(finalIndex, client);
                    ((WatchedItem)watchedItems.get(finalIndex)).updateInfo();
                });
                return finalIndex;
            }
        }

        final int newIndex = clientList.size();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                clientList.add(clientList.size(), client);
                WatchedItem newWatchedItem = new WatchedItem(newIndex);
                watchedItems.add(newWatchedItem);
                watchedItemPanel.add(newWatchedItem);
                watchedItemPanel.revalidate();
                watchedItemPanel.repaint();
            }
        });
        return newIndex;
    }


    public static ClientSocketThread getClient(int clientIndex) {
        return clientList.get(clientIndex);
    }

    public static void main(String[] args) {
        init();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow();
            }
        });

    }


    public static void stopAllClients() {
        for (int i = 0; i  < clientList.size(); ++i) {
            clientList.get(i).stop();
        }
    }
}