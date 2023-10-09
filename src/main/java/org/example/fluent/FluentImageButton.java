package org.example.fluent;

import org.example.fluent.colors.FluentColors;
import org.example.fluent.fonts.FluentFonts;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FluentImageButton extends JButton {
    private final BufferedImage image;
    private static final int OFFSET_SIZE = 2; // size change of the button during the animation
    private static final int PADDING = 9;
    private static final int BORDER = 1;
    private int arcRadius = 12;
    private int buttonState  = 0; // 0: normal; 1: hovered; 2: pressed.

    public FluentImageButton(String pathToImage) {
        super("");
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        this.setFont(FluentFonts.BODY);
        this.setBackground(FluentColors.BUTTON_BACKGROUND_NORMAL);
        this.setForeground(FluentColors.BUTTON_FOREGROUND_NORMAL);
        try {
            InputStream inputStream = getClass().getResourceAsStream(pathToImage);
            if (inputStream != null) {
                image = ImageIO.read(inputStream);
            } else {
                throw new FileNotFoundException("Image not found: " + pathToImage);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setPreferredSize(new Dimension(36, 36));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (buttonState < 1) {
                    buttonState = 1;
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                if (buttonState < 2) {
                    buttonState = 2;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (buttonState == 2) {
                    buttonState = 1;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (buttonState != 0) {
                    buttonState = 0;
                }
            }
        });
    }

    public void setArcRadius(int ARC_RADIUS) {
        if (ARC_RADIUS < 0) {
            arcRadius = this.getWidth() / 2;
            return;
        }
        this.arcRadius = ARC_RADIUS;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        g2d.drawImage(image, (getWidth() - image.getWidth()) / 2, (getHeight() - image.getHeight()) / 2, image.getWidth(), image.getHeight(), this);
        g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g2d.setColor(new Color(255, 255, 255, 20 + (buttonState % 2 - 2) * (buttonState - 1) * (-10)));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcRadius * 2, arcRadius * 2);
        g2d.dispose();
    }

}
