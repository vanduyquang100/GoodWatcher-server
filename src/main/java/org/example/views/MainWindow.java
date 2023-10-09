package org.example.views;

import org.example.Main;
import org.example.fluent.FluentFrame;
import org.example.fluent.FluentImageButton;
import org.example.fluent.FluentPrimaryButton;
import org.example.fluent.colors.FluentColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import static org.example.fluent.fonts.FluentFonts.BODY;

public class MainWindow extends FluentFrame {
    private static final int BORDER = 25;

    public MainWindow() {
        super(FluentColors.FRAME_BACKGROUND, "Dash board");
        setSize(1000, 680);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Main.stopAllClients();
            }
        });

        SwingUtilities.invokeLater(()->{
            // Create the main panel with BorderLayout
            JPanel mainPanel = new JPanel(new GridLayout(1, 1));
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setBackground(FluentColors.FRAME_BACKGROUND);
            contentPanel.setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));

            JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            leftPanel.setBackground(FluentColors.FRAME_BACKGROUND);
            JTextArea instruction = new JTextArea("Make sure you don't turn off the server.");
            instruction.setFont(BODY);
            instruction.setBackground(FluentColors.FRAME_BACKGROUND);
            instruction.setFocusable(false);
            instruction.setEditable(false);
            instruction.setMargin(new Insets(0, 0, 0, 0));
            leftPanel.add(instruction);

            JPanel centerPanel = new JPanel(new GridLayout(1, 1));
            centerPanel.setBackground(FluentColors.FRAME_BACKGROUND);
            centerPanel.add(Main.watchedItemPanel);
//            centerPanel.add(new FileViewer(new File("src/main/java/org")));


            // Create the button panel with FlowLayout for right alignment
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(FluentColors.FRAME_BACKGROUND);

            FluentImageButton button = new FluentImageButton("/images/add.png");
            button.setArcRadius(-1);
            button.setPreferredSize(new Dimension(64, 64));
            final FluentFrame mainWindow = this;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ConnectionWindow(mainWindow);
                }
            });
            buttonPanel.add(button);

            // Add the panels to the main panel
            contentPanel.add(leftPanel, BorderLayout.NORTH);
            contentPanel.add(buttonPanel, BorderLayout.SOUTH);
            contentPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.add(contentPanel);

            add(mainPanel);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            this.setLocation(dim.width/2-this.getWidth()/2, dim.height/2-this.getHeight()/2);
            setVisible(true);
        });

    }

    @Override
    public void dispose() {
        super.dispose();
        Main.stopAllClients();
    }
}
