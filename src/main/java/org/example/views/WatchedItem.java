package org.example.views;

import org.example.Main;
import org.example.controllers.background.ClientSocketThread;
import org.example.fluent.*;
import org.example.fluent.colors.FluentColors;
import org.example.fluent.fonts.FluentFonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.awt.GridBagConstraints.FIRST_LINE_END;
import static java.awt.GridBagConstraints.LINE_START;
import static org.example.fluent.fonts.FluentFonts.BODY;

public class WatchedItem extends JPanel {
    public static final int PREFER_WIDTH = 380;
    public static final int PREFER_HEIGHT = 160;
    private int currentState = 0; // 0 = connected, 1 = disconnected, 2 = deleted
    private final int RADIUS = 20;
    private final int BORDER = 1;
    private final int MARGIN = 12;
    private final int clientIndex;
    private String imagePath = "/images/";
    private String notificationIconName = "notification.png";
    private String buttonIconName = "detail.png";
    private Color brightActive = new Color(51, 135, 214);
    private Color darkActive = new Color(0, 95, 184);
    private Color foreGround = new Color(255, 255, 255);
    private final JTextArea notificationText;
    private final JTextArea titleText;
    private final JTextArea subTitleText;
    private final FluentImageButton expandView;

    public WatchedItem(int clientIndex) {
        this.clientIndex = clientIndex;
        ClientSocketThread client = Main.getClient(clientIndex);
        client.connectToWatchedItem(this);
        setBackground(FluentColors.TRANSPARENT);
        setOpaque(false);
        setFocusable(false);
        setPreferredSize(new Dimension(PREFER_WIDTH, PREFER_HEIGHT));
        setMaximumSize(new Dimension(PREFER_WIDTH, PREFER_HEIGHT));
        setBorder(new EmptyBorder(MARGIN + 20, MARGIN + 20, MARGIN + 20, MARGIN + 20));
        setLayout(new GridLayout(1, 1));

        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setOpaque(false);
        JPanel mainPanel = new JPanel(new GridLayout(1, 1));
        mainPanel.setOpaque(false);
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);

        JPanel leftPanel = new JPanel(new GridLayout(1, 2));
        leftPanel.setOpaque(false);
        // Notification panel
        JPanel notificationPanel = new JPanel(new GridBagLayout());
        notificationPanel.setOpaque(false);
        notificationText = new JTextArea(client.notificationMessage);
//        notificationText = new JTextArea("An extremely long line of words that would be efficient to test the actual width of any container of it, especially ones with no background colors at all.");
        notificationText.setOpaque(false);
        notificationText.setFont(BODY);
        notificationText.setBackground(getBackground());
        notificationText.setForeground(foreGround);
        notificationText.setFocusable(false);
        notificationText.setEditable(false);
        notificationText.setLineWrap(true); // Enable line wrapping
        notificationText.setWrapStyleWord(true);
        notificationText.setMargin(new Insets(0, 6, 2, 0));
        FluentImage image = new FluentImage(imagePath + notificationIconName);
        image.setPreferredSize(new Dimension(12, 12));
        GridBagConstraints notificationConstraints;
        notificationConstraints = new GridBagConstraints();
        notificationConstraints.gridx = 0;
        notificationConstraints.gridwidth = 1;
        notificationPanel.add(image, notificationConstraints);
        notificationConstraints.gridx = 1;
        notificationConstraints.weightx = 1;
        notificationConstraints.fill = GridBagConstraints.HORIZONTAL;
        notificationConstraints.gridwidth = GridBagConstraints.REMAINDER;
        notificationPanel.add(notificationText, notificationConstraints);
        leftPanel.add(notificationPanel);

        JPanel centerPanel = new JPanel(new GridLayout(1, 1));
        centerPanel.setOpaque(false);
        JPanel infoPanel =  new JPanel(new GridBagLayout());
        infoPanel.setOpaque(false);
        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setOpaque(false);
        titleText = new JTextArea(client.clientName);
        titleText.setOpaque(false);
        titleText.setFont(FluentFonts.TITLE);
        titleText.setBackground(getBackground());
        titleText.setForeground(foreGround);
        titleText.setFocusable(false);
        titleText.setEditable(false);
        titleText.setMargin(new Insets(0, 0, 0, 0));
        titlePanel.add(titleText);

        // Subtitle panel
        JPanel subTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        subTitlePanel.setOpaque(false);
        subTitleText = new JTextArea(client.IpAddress);
        subTitleText.setOpaque(false);
        subTitleText.setFont(FluentFonts.BODY_LARGE);
        subTitleText.setBackground(getBackground());
        subTitleText.setForeground(foreGround);
        subTitleText.setFocusable(false);
        subTitleText.setEditable(false);
        subTitleText.setMargin(new Insets(0, 0, 0, 0));
        subTitlePanel.add(subTitleText);
        GridBagConstraints infoConstraints;
        infoConstraints = new GridBagConstraints();
        infoConstraints.gridy = 0;
        infoConstraints.gridheight = 1;
        infoConstraints.weighty = 1;
        infoConstraints.weightx = 1;
        infoConstraints.fill = GridBagConstraints.HORIZONTAL;
        infoConstraints.anchor = LINE_START;
        infoPanel.add(titlePanel, infoConstraints);
        infoConstraints.gridy = 1;
        infoConstraints.gridwidth = GridBagConstraints.REMAINDER;
        infoPanel.add(subTitlePanel, infoConstraints);
        centerPanel.add(infoPanel);

        expandView = new FluentImageButton(imagePath + buttonIconName);
        expandView.addActionListener(e -> SwingUtilities.invokeLater(()->
                new FullListWindow(clientIndex)));
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints buttonConstraints;
        buttonConstraints = new GridBagConstraints();
        buttonConstraints.anchor = FIRST_LINE_END;
        buttonConstraints.fill =GridBagConstraints.NONE;
        buttonConstraints.weighty = 1;
        buttonPanel.add(expandView, buttonConstraints);

        // Add the panels to the main panel
        contentPanel.add(leftPanel, BorderLayout.NORTH);
        contentPanel.add(centerPanel, BorderLayout.CENTER);

        mainPanel.add(contentPanel);
        itemPanel.add(mainPanel, BorderLayout.CENTER);
        itemPanel.add(buttonPanel, BorderLayout.EAST);
        // Button
//
//        mainPanel.add(buttonPanel);
        add(itemPanel);

    }


    public void changeNotification(String newOne) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                notificationText.setText(newOne);
//                Main.listItems.revalidate();
//                Main.listItems.repaint();
                Main.watchedItemPanel.revalidate();
                Main.watchedItemPanel.repaint();
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        // Set rendering hints for smoother edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int topOpacity = 6;
        for (int i = 0; i <= MARGIN; i++) {
            g.setColor(new Color(21, 46, 95, (topOpacity / (6))));
            g.fillRoundRect(i, i, this.getWidth() - (i * 2), this.getHeight() - (i * 2), RADIUS * 2, RADIUS * 2);
        }
        // Set the gradient paint as the fill color
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(MARGIN, MARGIN - 1, getWidth() - MARGIN * 2, getHeight() - MARGIN * 2, RADIUS * 2, RADIUS * 2);
        // Fill the panel with rounded corners
        GradientPaint gradient = new GradientPaint((float) getWidth() / 2, (float) getHeight() / 2, brightActive, getWidth(), getHeight(), darkActive);
        g2d.setPaint(gradient);
        g2d.fillRoundRect(BORDER + MARGIN, BORDER + MARGIN,
                getWidth() - (BORDER + MARGIN) * 2,
                getHeight() - (BORDER + MARGIN) * 2,
                (RADIUS - BORDER) * 2,
                (RADIUS - BORDER) * 2);
        g2d.dispose();
    }

    public void updateInfo() {
        ClientSocketThread client = Main.getClient(clientIndex);
        client.connectToWatchedItem(this);
        SwingUtilities.invokeLater(()->{
            notificationText.setText(client.notificationMessage);
            notificationText.setForeground(foreGround);
            titleText.setText(client.clientName);
            titleText.setForeground(foreGround);
            subTitleText.setText(client.IpAddress);
            subTitleText.setForeground(foreGround);
            Main.watchedItemPanel.revalidate();
            Main.watchedItemPanel.repaint();
        });
    }
}
