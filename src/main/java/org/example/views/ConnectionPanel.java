package org.example.views;

import org.example.fluent.FluentButton;
import org.example.fluent.FluentFrame;
import org.example.fluent.colors.FluentColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.example.fluent.fonts.FluentFonts.*;
import static org.example.helpers.TextProcess.copyToClipboard;

public class ConnectionPanel extends JPanel {
    private static final int BORDER = 25;
    private final JTextArea codeTextArea;
    public ConnectionPanel() {
        // Create the main panel with BorderLayout
        setLayout(new BorderLayout());
        setBackground(FluentColors.FRAME_BACKGROUND);
        setBorder(new EmptyBorder(0, BORDER, BORDER, BORDER));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(FluentColors.FRAME_BACKGROUND);
        JTextArea instruction = new JTextArea("Enter this code to your client's app.");
        instruction.setFont(BODY);
        instruction.setBackground(FluentColors.FRAME_BACKGROUND);
        instruction.setFocusable(false);
        instruction.setEditable(false);
        instruction.setMargin(new Insets(0, 0, 0, 0));
        leftPanel.add(instruction);

// Create the center panel with BoxLayout to center the text field
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(FluentColors.FRAME_BACKGROUND);
        codeTextArea = new JTextArea("Waiting...");
        codeTextArea.setFont(SUBTITLE);
        codeTextArea.setBackground(FluentColors.FRAME_BACKGROUND);
        codeTextArea.setFocusable(false);
        codeTextArea.setEditable(false);
        codeTextArea.setMargin(new Insets(0, 50, 0, 0));
        FluentButton copyButton = new FluentButton("Copy");
        centerPanel.add(codeTextArea, new GridBagConstraints());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 20, 0, 0);
        centerPanel.add(copyButton, constraints);

// Create the button panel with FlowLayout for right alignment
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(FluentColors.FRAME_BACKGROUND);

        FluentButton button = new FluentButton("Cancel");
        buttonPanel.add(button);

// Add the panels to the main panel
        add(leftPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
//            ClientSocketThread finalClient = client;
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                    assert finalClient != null;
//                    finalClient.stop();
                ((JDialog) SwingUtilities.getWindowAncestor(button)).dispose();
            }
        });

        JTextArea finalTextArea = codeTextArea;
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = finalTextArea.getText();
                copyToClipboard(text);
            }
        });
        setVisible(true);
    }

    public void setCode(String s) {
        codeTextArea.setText(s);
    }
}
