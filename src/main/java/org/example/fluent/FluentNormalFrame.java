package org.example.fluent;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FluentNormalFrame extends JFrame {
    public FluentNormalFrame(Color backgroundColor, String frameName) {
        super(frameName);
        setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
        getContentPane().setBackground(backgroundColor);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }

    @Override
    public void add(Component comp, Object constraints ) {
        super.add(comp, BorderLayout.CENTER);
    }
}
