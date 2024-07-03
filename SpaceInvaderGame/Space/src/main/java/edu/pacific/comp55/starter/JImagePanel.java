package edu.pacific.comp55.starter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class JImagePanel extends JPanel {
    private BufferedImage originalSpaceshipImage;
    private BufferedImage resizedSpaceshipImage;
    private boolean highlighted;

    public JImagePanel(String imagePath, int targetWidth, int targetHeight) {
        this.originalSpaceshipImage = loadImage(imagePath);
        this.resizedSpaceshipImage = resizeImage(originalSpaceshipImage, targetWidth, targetHeight);
        setPreferredSize(new Dimension(targetWidth, targetHeight));
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
        repaint();
    }

    private BufferedImage loadImage(String path) {
        URL imgUrl = getClass().getClassLoader().getResource(path);
        if (imgUrl != null) {
            try {
                return ImageIO.read(imgUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.err.println("Could not find image: " + path);
        return null;
    }
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g.dispose();
        return resizedImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Fill the panel with a black background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (highlighted) {
            g.setColor(Color.YELLOW);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
        g.drawImage(resizedSpaceshipImage, 0, 0, this);
    }
}
