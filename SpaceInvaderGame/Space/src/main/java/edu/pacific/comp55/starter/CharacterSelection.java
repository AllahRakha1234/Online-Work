package edu.pacific.comp55.starter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

public class CharacterSelection extends JFrame {
	private static final int SPACESHIP_SIZE = 100;
	private static final int SPACESHIP_MARGIN = 20;


	private JLabel selectionLabel;
	private JLabel startButton;
	private JPanel spaceshipsPanel;
	private int selectedSpaceship = -1;
	private boolean mode;
	private Board boardInstance = null;


	public CharacterSelection() {
		setTitle("Character Selection");
		setSize(Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		setupMenu();
	}
	public CharacterSelection(Board boardSelection, boolean mode) {
		this.boardInstance = boardSelection;
		setTitle("Character Selection");
		setSize(Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		this.mode = mode;

		setupMenu();
	}

	private void setupMenu() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBackground(Color.BLACK);

		selectionLabel = createLabel("Select Your Spaceship", Color.WHITE);
		mainPanel.add(selectionLabel);

		spaceshipsPanel = new JPanel();
		spaceshipsPanel.setOpaque(false);
		spaceshipsPanel.setLayout(new FlowLayout());

		for (int i = 0; i < 3; i++) {
			JImagePanel spaceshipPanel = createSpaceshipPanel(i + 1);
			spaceshipsPanel.add(spaceshipPanel);
		}

		mainPanel.add(spaceshipsPanel);

		startButton = createLabel("Start Game", Color.WHITE);
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				startGame();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				highlightLabel(startButton, Color.YELLOW);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				highlightLabel(startButton, Color.WHITE);
			}
		});

		mainPanel.add(startButton);

		add(mainPanel, BorderLayout.CENTER);
	}

	private JLabel createLabel(String text, Color color) {
		JLabel label = new JLabel(text);
		label.setForeground(color);
		label.setFont(loadCustomFont("space_invaders.ttf").deriveFont(Font.PLAIN, 24));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		return label;
	}

	private JImagePanel createSpaceshipPanel(int spaceshipNumber) {
		JImagePanel spaceshipPanel = new JImagePanel("Ship" + spaceshipNumber + ".png", 120, 120);
		spaceshipPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				highlightSelection(spaceshipPanel, spaceshipNumber);
			}
		});
		return spaceshipPanel;
	}

	private void highlightSelection(JImagePanel spaceshipPanel, int spaceshipNumber) {
		selectedSpaceship = spaceshipNumber;
		for (Component component : spaceshipsPanel.getComponents()) {
			((JImagePanel) component).setHighlighted(false);
		}
		spaceshipPanel.setHighlighted(true);

		updateSelectionLabel(spaceshipNumber);
	}

	private void updateSelectionLabel(int spaceshipNumber) {
		String labelText = "Selected Spaceship: " + spaceshipNumber;
		selectionLabel.setText(labelText);
	}

	private void startGame() {
		if (selectedSpaceship != -1) {
			// Start the game with the selected spaceship
			// Add your game initialization logic here
			if (boardInstance == null) {
				// Board instance is not created, create a new one
//				int highScore = getHighScore();
				boardInstance = new Board(selectedSpaceship, mode);
			}
			else {
				// Now, you can use the existing boardInstance
				boardInstance.resetGame(selectedSpaceship, mode);
				dispose();
				return;
			}

				dispose();
				boardInstance.start();
			// For demonstration purposes, I'm just closing the character selection window
			// You might want to transition to the game window instead
//			dispose();
//			new Board(selectedSpaceship).start();
		}
	}

	private Font loadCustomFont(String fontFileName) {
		try {
			URL fontUrl = getClass().getClassLoader().getResource(fontFileName);

			if (fontUrl != null) {
				return Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream()).deriveFont(Font.PLAIN, 24);
			} else {
				System.err.println("Font file not found: " + fontFileName);
				return new Font("Arial", Font.PLAIN, 24);
			}
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
			return new Font("Arial", Font.PLAIN, 24);
		}
	}

	private void highlightLabel(JLabel label, Color color) {
		label.setForeground(color);
	}

//	public static void main(String[] args) {
//		SwingUtilities.invokeLater(() -> {
//			new CharacterSelection().setVisible(true);
//		});
//	}

	
}
