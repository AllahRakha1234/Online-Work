package edu.pacific.comp55.starter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PauseWindow extends JFrame {
    private JLabel resumeLabel;
    private JLabel quitLabel;
    private Board boardInstance;
    private boolean mode;


    static Font customFont = loadCustomFont("space_invaders.ttf");
    private int savedLevel;

    public PauseWindow() {
        initializeUI();
    }public PauseWindow(Board boardInst) {
        this.boardInstance = boardInst;
        this.savedLevel = boardInst.getLevel();
        boardInst.setLevel(-1);
        if ( boardInst.getTimer() != null){
            boardInst.getTimer().stop();
        }
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Game Paused");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);

        JLabel loseLabel = new JLabel("GAME PAUSED");
        loseLabel.setBounds(73, 50, 300, 50);
        loseLabel.setFont(customFont.deriveFont(30.0f));
        loseLabel.setForeground(Color.GREEN);
        add(loseLabel);

        resumeLabel = createLabel("Resume", 125, 150);
        quitLabel = createLabel("Quit", 150, 200);

        setLayout(null);
        setVisible(true);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 300, 50);
        label.setForeground(Color.BLUE);
        label.setFont(customFont);

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
        if ("Resume".equals(labelText)) {
            // Implement the logic for retrying the game
            System.out.println("Retrying...");
            // For demonstration purposes, just closing the You Lose screen
            boardInstance.setLevel(savedLevel);
            if (boardInstance.getTimer() != null){
                boardInstance.getTimer().start();
            }            dispose();

        } else if ("Quit".equals(labelText)) {
            System.out.println("Quitting...");
            dispose();
            new MainMenu(boardInstance);
        }
    }


    private static Font loadCustomFont(String fontFileName) {
        try {
            URL fontUrl = PauseWindow.class.getClassLoader().getResource(fontFileName);

            if (fontUrl != null) {
                return Font.createFont(Font.TRUETYPE_FONT, new File(fontUrl.toURI())).deriveFont(Font.PLAIN, 24);
            } else {
                System.err.println("Font file not found: " + fontFileName);
                return new Font("Arial", Font.PLAIN, 24);
            }
        } catch (IOException | FontFormatException | java.net.URISyntaxException e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, 24);
        }
    }

//    public static void main(String[] args) {
//        new PauseWindow();
//    }
}

