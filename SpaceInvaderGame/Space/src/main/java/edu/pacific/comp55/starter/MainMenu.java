package edu.pacific.comp55.starter;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.*;

public class MainMenu extends JFrame {
    private JLabel levelsLabel;
    private JLabel menuLabel;
    private JLabel quitLabel;
    private JLabel characterLabel;
    private JLabel endlessLabel;
    private JImagePanel menuSprite;
    private Board boardInstance;

    static Font customFont = loadCustomFont("space_invaders.ttf");

    public MainMenu() {
        initializeUI();
    }
    public MainMenu(Board boardInstance) {
        initializeUI();
        this.boardInstance = boardInstance;
    }

    private void initializeUI() {
        setTitle("Menu");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(Color.BLACK);

        JLabel titleLabel1 = new JLabel("ORBITAL");
        titleLabel1.setBounds( 115, 50,300, 50);

        JLabel titleLabel2 = new JLabel("ONSLAUGHT:");
        titleLabel2.setBounds( 92, 90,300, 50);

        JLabel titleLabel3 = new JLabel("COSMIC");
        titleLabel3.setBounds(  120, 130,300, 50);

        JLabel titleLabel4 = new JLabel("CONQUEST");
        titleLabel4.setBounds( 98, 170,300, 50);

        Font headFont = customFont.deriveFont(30.0f);

        titleLabel1.setForeground(Color.WHITE);
        titleLabel1.setFont(headFont);titleLabel2.setForeground(Color.WHITE);
        titleLabel2.setFont(headFont);titleLabel3.setForeground(Color.WHITE);
        titleLabel3.setFont(headFont);titleLabel4.setForeground(Color.WHITE);
        titleLabel4.setFont(headFont);

        add(titleLabel1);
        add(titleLabel2);
        add(titleLabel3);
        add(titleLabel4);

        menuLabel = new JLabel("MAIN MENU");
        menuLabel.setBounds(95, 250, 300, 50);
        menuLabel.setFont(headFont);
        menuLabel.setForeground(Color.ORANGE);
        add(menuLabel);

        levelsLabel = createLabel("Play Levels", 90, 300);
        endlessLabel = createLabel("Endless Mode", 85, 340);
        quitLabel = createLabel("Quit", 150, 380);

        menuSprite  = new JImagePanel("menu_sprite.png", 150,150);
        add(menuSprite);

        setLayout(null);
        setVisible(true);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 300, 50);
        label.setForeground(Color.BLUE);
        label.setFont(customFont);

        // Add a MouseListener for the hover effect
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(Color.BLUE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                onLabelClick(text);
            }
        });

        add(label);
        return label;
    }

    private void onLabelClick(String labelText) {
        System.out.println("Label Clicked: " + labelText);
        if ("Play Levels".equals(labelText)) {
            openLevelSelect();
        } else if ("Endless Mode".equals(labelText)) {
            openEndlessLabel();
        } else if ("Quit".equals(labelText)) {
            System.exit(0);
        }

    }


    private void openLevelSelect() {
        // Implement the logic for opening the LevelSelect
        new CharacterSelection(boardInstance, false).setVisible(true);
        dispose();
    }

    private void openEndlessLabel() {
        new CharacterSelection(boardInstance, true).setVisible(true);
        dispose();
    }

    private static Font loadCustomFont(String fontFileName) {
        try {
            // Get the URL of the font file in the resources directory
            URL fontUrl = CustomText.class.getClassLoader().getResource(fontFileName);

            if (fontUrl != null) {
                // Create a Font object from the font file URL
                return Font.createFont(Font.TRUETYPE_FONT, new File(fontUrl.toURI())).deriveFont(Font.PLAIN, 24);
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

//    public static void main(String[] args) {
//        new MainMenu();
//    }
}
