import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoleGame extends JFrame {
    private int score;
    private int time;
    private boolean isPlaying;
    private Random random;
    private Timer gameTimer;
    private JButton playButton;
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private JLabel backgroundLabel;
    private ImageIcon moleUpIcon;
    private ImageIcon moleHitIcon;
    private ImageIcon rabbitUpIcon;
    private ImageIcon rabbitHitIcon;
    private List<Point> holePositions;
    private List<JLabel> activeEntities;
    private List<Point> rabbitPositions;

    public MoleGame() {
        score = 0;
        time = 0;
        isPlaying = false;
        random = new Random();
        holePositions = new ArrayList<>();
        activeEntities = new ArrayList<>();
        rabbitPositions = new ArrayList<>();

        setTitle("Whack-a-Mole Game");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        moleUpIcon = new ImageIcon("mole_up.png");
        moleHitIcon = new ImageIcon("mole_hit.png");
        rabbitUpIcon = new ImageIcon("rabbit_up.png");
        rabbitHitIcon = new ImageIcon("rabbit_hit.png");

        // Resize mole and rabbit images
        moleUpIcon = resizeIcon(moleUpIcon, 100, 100);
        moleHitIcon = resizeIcon(moleHitIcon, 100, 100);
        rabbitUpIcon = resizeIcon(rabbitUpIcon, 100, 100);
        rabbitHitIcon = resizeIcon(rabbitHitIcon, 100, 100);

        backgroundLabel = new JLabel(new ImageIcon("background.png"));
        backgroundLabel.setBounds(0, 0, 1000, 800);
        add(backgroundLabel);

        // Define fixed positions for holes centered on the screen
        defineFixedPositions();

        playButton = new JButton("Tap to Play");
        playButton.setBounds(450, 350, 100, 50);
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
                playButton.setVisible(false);
            }
        });
        backgroundLabel.add(playButton);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(20, 20, 100, 30);
        backgroundLabel.add(scoreLabel);

        timeLabel = new JLabel("Time: 0");
        timeLabel.setBounds(880, 20, 100, 30);
        backgroundLabel.add(timeLabel);

        setVisible(true);
    }

    private void defineFixedPositions() {
        // Define 5 fixed positions centered on the screen
        holePositions.add(new Point(200, 200));
        holePositions.add(new Point(400, 200));
        holePositions.add(new Point(600, 200));
        holePositions.add(new Point(800, 200));
        holePositions.add(new Point(500, 400));
        
        rabbitPositions.add(new Point(holePositions.get(0).x + 50, holePositions.get(0).y + 150)); //1st hole
        rabbitPositions.add(new Point(holePositions.get(1).x + 10, holePositions.get(1).y + 350)); // 4th hole
        rabbitPositions.add(new Point(holePositions.get(2).x + 50, holePositions.get(2).y + 150)); // 2nd hole
        rabbitPositions.add(new Point(holePositions.get(3).x + 50 , holePositions.get(3).y + 350)); //5th hole
        rabbitPositions.add(new Point(holePositions.get(4).x - 400, holePositions.get(4).y + 150)); //3rd hole
    }

    public void startGame() {
        score = 0;
        time = 0;
        isPlaying = true;
        activeEntities.clear();

        gameTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTimer();
                spawnEntities();
            }
        });
        gameTimer.start();
    }

    private void spawnEntities() {
        if (activeEntities.size() >= holePositions.size()) {
            return;
        }

        int randomNumber = random.nextInt(10);
        if (randomNumber < 5) { // 50% chance to spawn an entity
            int holeIndex = random.nextInt(holePositions.size());
            Point holePoint = holePositions.get(holeIndex);
            if (!isPositionOccupied(holePoint)) {
                if (randomNumber < 2) {
                    spawnMole(holeIndex);
                } else {
                    spawnRabbit(holeIndex);
                }
            }
        }
    }

    private boolean isPositionOccupied(Point point) {
        for (JLabel entity : activeEntities) {
            Rectangle entityBounds = entity.getBounds();
            if (entityBounds.contains(point)) {
                return true;
            }
        }
        return false;
    }

    private void spawnMole(int holeIndex) {
        JLabel mole = new JLabel(moleUpIcon);
        Point p = rabbitPositions.get(holeIndex);
        mole.setBounds(p.x, p.y, moleUpIcon.getIconWidth(), moleUpIcon.getIconHeight());
        
        mole.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                hitMole(mole);
            }
        });
        backgroundLabel.add(mole);
        backgroundLabel.setComponentZOrder(mole, 0);
        activeEntities.add(mole);
        revalidate();
        repaint();

        // Calculate appearance time based on game time
        int appearanceTime = getEntityAppearanceTime();

        // Remove mole after a certain time
        Timer timer = new Timer(appearanceTime, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (mole.getParent() != null) {
                    backgroundLabel.remove(mole);
                    activeEntities.remove(mole);
                    revalidate();
                    repaint();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void spawnRabbit(int holeIndex) {
        JLabel rabbit = new JLabel(rabbitUpIcon);
        Point p = rabbitPositions.get(holeIndex);
        rabbit.setBounds(p.x, p.y, rabbitUpIcon.getIconWidth(), rabbitUpIcon.getIconHeight());
        
        rabbit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                hitRabbit(rabbit);
            }
        });
        backgroundLabel.add(rabbit);
        backgroundLabel.setComponentZOrder(rabbit, 0);
        activeEntities.add(rabbit);
        revalidate();
        repaint();

        // Calculate appearance time based on game time
        int appearanceTime = getEntityAppearanceTime();

        // Remove rabbit after a certain time
        Timer timer = new Timer(appearanceTime, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (rabbit.getParent() != null) {
                    backgroundLabel.remove(rabbit);
                    activeEntities.remove(rabbit);
                    revalidate();
                    repaint();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private int getEntityAppearanceTime() {
        if (time < 20) {
            return 2000;
        } else if (time < 40) {
            return 1500;
        } else if (time < 60) {
            return 1000;
        } else if (time < 80) {
            return 800;
        } else {
            return 600;
        }
    }

    private void hitMole(JLabel mole) {
        mole.setIcon(moleHitIcon); // Set mole hit image
        score++;
        scoreLabel.setText("Score: " + score);
        revalidate();
        repaint();

        Timer timer = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                backgroundLabel.remove(mole);
                activeEntities.remove(mole);
                revalidate();
                repaint();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void hitRabbit(JLabel rabbit) {
        rabbit.setIcon(rabbitHitIcon); // Set rabbit hit image
        score--;
        scoreLabel.setText("Score: " + score);
        revalidate();
        repaint();

        Timer timer = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                backgroundLabel.remove(rabbit);
                activeEntities.remove(rabbit);
                revalidate();
                repaint();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void updateTimer() {
        time++;
        timeLabel.setText("Time: " + time);
        
        // Gradually increase difficulty by decreasing the spawn interval
        if (time >= 20) {
            gameTimer.setDelay(800);
        }
        if (time >= 40) {
            gameTimer.setDelay(600);
        }
        if (time >= 60) {
            gameTimer.setDelay(400);
        }
        if (time >= 80) {
            gameTimer.setDelay(200);
        }
        if (time >= 120) {
            endGame();
        }
    }

    private void endGame() {
        isPlaying = false;
        gameTimer.stop();
        JOptionPane.showMessageDialog(this, "Game Over!\nYour Score: " + score);
        scoreLabel.setText("Score: 0");
        removeAllEntities();
        playButton.setVisible(true);
    }

    private void removeAllEntities() {
        for (JLabel entity : activeEntities) {
            backgroundLabel.remove(entity);
        }
        activeEntities.clear();
        revalidate();
        repaint();
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    public static void main(String[] args) {
        new MoleGame();
    }
}
