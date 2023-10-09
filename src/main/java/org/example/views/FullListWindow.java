package org.example.views;

import org.example.Main;
import org.example.controllers.background.ClientSocketThread;
import org.example.controllers.background.GetClient;
import org.example.fluent.FluentDialog;
import org.example.fluent.FluentImage;
import org.example.fluent.colors.FluentColors;
import org.example.fluent.fonts.FluentFonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FullListWindow extends FluentDialog {
    private static final int BORDER = 25;

    public FullListWindow(int clientIndex)  {
        super(FluentColors.FRAME_BACKGROUND, Main.getClient(clientIndex).clientName);
        ClientSocketThread client = Main.getClient(clientIndex);
        setSize(500, 640);
        setResizable(false);
        JPanel mainPanel = new JPanel(new GridLayout(1, 1));
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(FluentColors.FRAME_BACKGROUND);
        contentPanel.setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));

        JPanel centerPanel = new JPanel(new GridLayout(1, 1));
        JList<String> events = new JList<>(client.messages);
        events.setBackground(FluentColors.FRAME_BACKGROUND);
        events.setCellRenderer(new ListCellRenderer<String>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
                JTextArea itemText = new JTextArea(ClientSocketThread.decodeToString(value));
                itemText.setOpaque(false);
                itemText.setFont(FluentFonts.BODY);
                itemText.setFocusable(false);
                itemText.setEditable(false);
                itemText.setMargin(new Insets(0, 5, 0, 0));
                JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT));
                item.setBackground(FluentColors.FRAME_BACKGROUND);
                String iconPath = "/images/";
                if (value.charAt(0) == '$') {
                    iconPath += "folder_deleted.png";
                } else if (value.charAt(0) == '^') {
                    iconPath += "folder";
                } else {
                    iconPath += "file";
                }

                if (value.charAt(1) == '+') {
                    iconPath += "_add.png";
                } else if (value.charAt(1) == '-') {
                    iconPath += "_delete.png";
                } else if (value.charAt(1) == value.charAt(0)) {
                    iconPath += "_edit.png";
                }

                FluentImage icon = new FluentImage(iconPath);
                icon.setPreferredSize(new Dimension(16, 16));
                item.setPreferredSize(new Dimension(Integer.MAX_VALUE, 36));
                item.add(icon);
                item.add(itemText);
                return item;
            }
        });
        centerPanel.add(events);


        contentPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(contentPanel);
        add(mainPanel);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getWidth()/2, dim.height/2-this.getHeight()/2);
        setVisible(true);
    }
}
