package org.example.fluent;

import org.example.fluent.colors.FluentColors;

import javax.swing.*;
import java.awt.*;

public class FluentScrollPane extends JScrollPane {

    public FluentScrollPane(Component view) {
        super(view);
        initialize();
    }

    private void initialize() {
        // Customization code here
        // Modify scroll pane properties, add custom components, etc.
        // For example, you can customize the scroll bar appearance:
        getVerticalScrollBar().setUI(new FluentScrollBarUI());
        getHorizontalScrollBar().setUI(new FluentScrollBarUI());
        setOpaque(false);
        setBackground(FluentColors.FRAME_BACKGROUND);
    }

    // Override any other methods as needed
}

