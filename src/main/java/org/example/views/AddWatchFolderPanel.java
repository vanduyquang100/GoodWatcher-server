package org.example.views;

import org.example.Main;
import org.example.controllers.background.ClientSocketThread;
import org.example.fluent.FluentButton;
import org.example.fluent.FluentPrimaryButton;
import org.example.fluent.FluentScrollPane;
import org.example.fluent.FluentTextField;
import org.example.fluent.colors.FluentColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static org.example.fluent.fonts.FluentFonts.BODY;
import static org.example.fluent.fonts.FluentFonts.SUBTITLE_EXTRA_BOLD;

public class AddWatchFolderPanel extends JPanel {
    private final static int BORDER = 25;
    private FluentTextField textField;

    public AddWatchFolderPanel(int clientIndex) {
        ClientSocketThread client = Main.getClient(clientIndex);
        // Create the main panel with BorderLayout
        setLayout(new BorderLayout());
        setBackground(FluentColors.FRAME_BACKGROUND);
        setBorder(new EmptyBorder(0, BORDER, BORDER, BORDER));

        JPanel leftPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel1.setBackground(FluentColors.FRAME_BACKGROUND);
        JTextArea PcNameTextArea = new JTextArea(client.clientName);
        PcNameTextArea.setFont(SUBTITLE_EXTRA_BOLD);
        PcNameTextArea.setBackground(FluentColors.FRAME_BACKGROUND);
        PcNameTextArea.setFocusable(false);
        PcNameTextArea.setEditable(false);
        PcNameTextArea.setMargin(new Insets(10, 0, 10, 0));
        leftPanel1.add(PcNameTextArea);

        JPanel leftPanel2 = new JPanel(new GridLayout(1, 1));
        leftPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel2.setBackground(FluentColors.FRAME_BACKGROUND);
        textField = new FluentTextField("Enter the folder path");
        textField.setPreferredSize(new Dimension(500 - 2 * (BORDER + 10), 32));
        leftPanel2.add(textField);

// Create the center panel for the text field
        JPanel centerPanel = new JPanel(new GridLayout(1, 1));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.setBackground(FluentColors.FRAME_BACKGROUND);
        centerPanel.add(new FluentScrollPane(new FileViewer(clientIndex, this)));

// Create the button panel with FlowLayout for right alignment
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(FluentColors.FRAME_BACKGROUND);

        FluentButton backButton = new FluentButton("Back");
        FluentPrimaryButton button = new FluentPrimaryButton("Add");
        buttonPanel.add(backButton);
        buttonPanel.add(button);

// Add the panels to the main panel
        add(leftPanel1, BorderLayout.NORTH);
        add(leftPanel2, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> client.setFolder("./", false));
        button.addActionListener(e -> client.setFolder(textField.getText(), true));

        setVisible(true);
    }

    public void setPath(String name) {
        SwingUtilities.invokeLater(()->{
            textField.setText(name);
        });
    }
}
