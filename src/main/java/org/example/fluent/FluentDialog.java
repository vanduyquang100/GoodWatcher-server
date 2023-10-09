package org.example.fluent;

import org.example.fluent.colors.FluentColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import static org.example.fluent.fonts.FluentFonts.BODY;
import static org.example.fluent.fonts.FluentFonts.TITLE_LARGE;

public class FluentDialog extends JDialog {
    JTextArea textArea;
    public FluentDialog(Color backgroundColor, String frameName) {
        getContentPane().setBackground(backgroundColor);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        headerPanel.setBackground(backgroundColor);

        textArea = new JTextArea(frameName);
        textArea.setFont(TITLE_LARGE);
        textArea.setBackground(backgroundColor);
        textArea.setFocusable(false);
        textArea.setEditable(false);
        textArea.setMargin(new Insets(0, 0, 0, 0));
        int leftMargin = 25;
        int topMargin = 45;
        textArea.setBorder(new EmptyBorder(topMargin, leftMargin, 0, 0));
        headerPanel.add(textArea);

        int headerPanelMaxHeight = 150;
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, headerPanelMaxHeight));

        super.add(headerPanel, BorderLayout.NORTH);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }

    public FluentDialog(FluentFrame owner, Color backgroundColor, String frameName) {
        super(owner, frameName, true);
        getContentPane().setBackground(backgroundColor);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        headerPanel.setBackground(backgroundColor);

        textArea = new JTextArea(frameName);
        textArea.setFont(TITLE_LARGE);
        textArea.setBackground(backgroundColor);
        textArea.setFocusable(false);
        textArea.setEditable(false);
        textArea.setMargin(new Insets(0, 0, 0, 0));
        int leftMargin = 25;
        int topMargin = 45;
        textArea.setBorder(new EmptyBorder(topMargin, leftMargin, 0, 0));
        headerPanel.add(textArea);

        int headerPanelMaxHeight = 150;
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, headerPanelMaxHeight));

        super.add(headerPanel, BorderLayout.NORTH);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }



    @Override
    public void add(Component comp, Object constraints ) {
        super.add(comp, BorderLayout.CENTER);
    }

    public void setTitle(String newName) {
        textArea.setText(newName);
    }
}
