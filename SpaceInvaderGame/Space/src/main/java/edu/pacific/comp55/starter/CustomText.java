package edu.pacific.comp55.starter;

import acm.graphics.GLabel;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class CustomText extends GLabel {

    // Default font and color
    static Font customFont = loadCustomFont("space_invaders.ttf");

    private static Font loadCustomFont(String fontFileName) {
        try {
            // Get the URL of the font file in the resources directory
            URL fontUrl = CustomText.class.getClassLoader().getResource(fontFileName);

            if (fontUrl != null) {
                // Create a Font object from the font file URL
                return Font.createFont(Font.TRUETYPE_FONT, new File(fontUrl.toURI())).deriveFont(Font.PLAIN, 16);
            } else {
                System.err.println("Font file not found: " + fontFileName);
                // Return a default font in case of an error
                return new Font("Arial", Font.PLAIN, 24);
            }
        } catch (IOException | FontFormatException | java.net.URISyntaxException e) {
            e.printStackTrace();
            // Return a default font in case of an error
            return new Font("Arial", Font.PLAIN, 24);
        }
    }
    private static final Font DEFAULT_FONT = customFont;
    private static final Color DEFAULT_COLOR = Color.WHITE;

    public CustomText(String label) {
        // Call the GLabel constructor with the label text
        super(label);

        // Set the default font and color
        setFont(DEFAULT_FONT);
        setColor(DEFAULT_COLOR);
    }

    public CustomText(String label, int i, int i1) {
        super(label, i, i1);
        // Set the default font and color
        setFont(DEFAULT_FONT);
        setColor(DEFAULT_COLOR);

    }


    // You can add additional constructors or methods if needed
}
