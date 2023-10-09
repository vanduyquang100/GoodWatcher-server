package org.example.views;

import org.example.Main;
import org.example.controllers.background.ClientSocketThread;
import org.example.fluent.FluentImage;
import org.example.fluent.colors.FluentColors;
import org.example.fluent.fonts.FluentFonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileViewer extends JList<String> {
    private final DefaultListModel<String> listModel;
    private final List<String> fileList;

    public FileViewer(int clientIndex, JPanel owner) {
//        setBackground(getBackground());
        fileList = new ArrayList<>();
        listModel = new DefaultListModel<>();
        setModel(listModel);
        setOpaque(false);

        ClientSocketThread client = Main.getClient(clientIndex);
        client.connectToFileViewer(this);
        client.setFolder("", false);

        this.setCellRenderer(new ListCellRenderer<String>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
                Path path = Paths.get(value);
                String lastFolderName = path.getFileName() != null ? path.getFileName().toString() : value;
                JTextArea itemText = new JTextArea(lastFolderName);
                itemText.setOpaque(false);
                itemText.setFont(FluentFonts.BODY);
                itemText.setFocusable(false);
                itemText.setEditable(false);
                itemText.setMargin(new Insets(0, 5, 0, 0));
                JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT));
                if (isSelected) {
                    item.setBackground(new Color(245, 245, 245));
                    ((AddWatchFolderPanel)owner).setPath(value);
                } else {
                    item.setBackground(FluentColors.FRAME_BACKGROUND);
                }
                String iconPath = "/images/folder.png";
                FluentImage icon = new FluentImage(iconPath);
                icon.setPreferredSize(new Dimension(16, 16));
                item.setPreferredSize(new Dimension(Integer.MAX_VALUE, 36));
                item.add(icon);
                item.add(itemText);
                return item;
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = locationToIndex(e.getPoint());
                    ((AddWatchFolderPanel)owner).setPath(fileList.get(index));
                    Main.getClient(clientIndex).setFolder(fileList.get(index), false);
                }
            }
        });
    }

    public void listFiles(ArrayList<String> folderNames) {
        fileList.clear();
        listModel.clear(); // Clear the model

        if (folderNames != null) {
            fileList.addAll(folderNames);
            for (String fileName : fileList) {
                listModel.addElement(fileName); // Add elements to the model
            }
        }
    }
}

