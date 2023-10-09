package org.example.fluent;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class FluentScrollBarUI extends BasicScrollBarUI {

    @Override
    protected void configureScrollBarColors() {
        // Customize scroll bar colors
        // For example, set the track and thumb colors
        trackColor = new Color(217, 217, 217);
        thumbColor = new Color(14, 72, 126);
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        // Custom track painting code
        // Override this method to customize the appearance of the track
        // For example, you can paint a custom background or texture
        g.setColor(trackColor);
        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        // Custom thumb painting code
        // Override this method to customize the appearance of the thumb
        // For example, you can paint a custom shape or gradient
        g.setColor(thumbColor);
        g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
    }

    protected JButton createDecreaseButton(int orientation) {
        // Create a custom decrease button
        FluentButton button = new FluentButton(" ");
        button.setPreferredSize(new Dimension(16, 16));
        return button;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        // Create a custom increase button
        FluentButton button = new FluentButton(" ");
        button.setPreferredSize(new Dimension(16, 16));
        return button;
    }
}

