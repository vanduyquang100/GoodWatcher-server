package org.example.fluent;

import org.example.fluent.colors.FluentColors;
import org.example.fluent.fonts.FluentFonts;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;

public class FluentImage extends JComponent {
    private final BufferedImage image;

    public FluentImage(String pathToImage) {
        setOpaque(false);
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

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
//        BufferedImage scaledImage = scaleImage(image, getWidth(), getHeight());
//        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//        g2d.drawImage(scaledImage, 0, 0, getWidth(), getHeight(), null);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        g2d.drawImage(scaledImage, 0, 0, getWidth(), getHeight(), null);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g2d.dispose();
    }

    public static BufferedImage scaleImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = scaledImage.createGraphics();

        // Set the rendering hint to enable smooth scaling
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // Scale the image using an AffineTransform
        AffineTransform transform = AffineTransform.getScaleInstance((double) targetWidth / originalImage.getWidth(),
                (double) targetHeight / originalImage.getHeight());
        g2d.drawImage(originalImage, transform, null);

        g2d.dispose();

        return scaledImage;
    }

}
