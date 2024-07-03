package edu.pacific.comp55.starter;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;


public class Board extends GraphicsProgram implements ActionListener, KeyListener {
    public ArrayList<GImage> Player;
    public ArrayList<GImage> lives;
    public ArrayList<GImage> hps;
    public GImage player;
    public GImage shieldSprite;
    public Stats stats;
    public CustomText labelScore;
    ArrayList<GImage> removeAttack = new ArrayList<>();
    ArrayList<GImage> removeEnemyBullets = new ArrayList<>();
    private boolean doubleFire;
    private Set<Integer> pressedKeys = new HashSet<>();
    private JFrame jFrame;
    private Level currLevel;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    private int level = Constants.START_LEVEL;
    private int wave = Constants.START_WAVE;
    private int timeRemaining = Constants.DEFAULT_TIMER;
    private int bosstime = 0;
    private boolean endless = true;
    private String playerFace = "Ship1.png";
    private Timer movement;

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    private Timer timer;
    private boolean moveLeft = true;
    private ArrayList<GImage> attack;
    private ArrayList<EnemyBullet> enemyAttack;
    private ArrayList<GImage> lvlenemy;
    private CustomText labelLevel;
    private CustomText labelWave;
    private CustomText labelLife;
    private CustomText labelPHealth;
    private CustomText labelHighscore;
    private CustomText labelTimer;
    private CustomText labelMenu;
    private YouWinWindow winWindow;
    private YouLoseWindow loseWindow;
    private PauseWindow pauseWindow;
    private boolean shield;
    private int remainingShield;
    private int remainingDoubleFire;
    private GImage laser;
    private boolean laserActivated;

    public Board(int selectedSpaceship, boolean mode) {
        this.playerFace = "Ship" + selectedSpaceship + ".png";
        this.endless = mode;
    }

//    public static void main(String[] args) {
//        new Board(1, true).start();
//    }

    public void run() {
        timeRemaining = Constants.DEFAULT_TIMER; // set the initial time in seconds
        currLevel = new Level(this, level, wave);
        movement = new Timer(50, this);
        attack = new ArrayList<GImage>();
        enemyAttack = new ArrayList<EnemyBullet>();
        Player = new ArrayList<GImage>();
        lives = new ArrayList<GImage>();
        hps = new ArrayList<GImage>();
        timer = new Timer(1000, this);


        movement.start();

        if (!endless) {// Initialize the timer
            timer.start();
        }

        generateAll();
        initializeUI();
        initializePowerUps();

        addMouseListeners();
        addKeyListeners();
    }

    private void initializePowerUps() {
        shield = false;
        doubleFire = false;
        laserActivated = false;
        remainingShield = Constants.SHIELD_DURATION;
        remainingDoubleFire = Constants.DFIRE_DURATION;
    }

    public void initializeUI() {
        stats = new Stats();
        stats.setHighscore(Constants.DEFAULT_HIGHSCORE);
        labelLevel = new CustomText("Level " + level, Constants.WINDOW_WIDTH - 120, Constants.WINDOW_HEIGHT - 70);
        labelWave = new CustomText("Wave  " + wave, Constants.WINDOW_WIDTH - 200, Constants.WINDOW_HEIGHT - 70);
        labelLife = new CustomText("lives", 30, Constants.WINDOW_HEIGHT - 70);
        labelScore = new CustomText("Score " + stats.getScore(), 30, 50);
        labelHighscore = new CustomText("Highscore " + stats.getHighscore(), Constants.WINDOW_WIDTH / 2 - 50, 50);
        labelPHealth = new CustomText("HP", Constants.WINDOW_WIDTH - 170, 80);
        labelMenu = new CustomText("Menu", Constants.WINDOW_WIDTH - 100, 50);
        labelTimer = new CustomText("Time: " + timeRemaining + "s", Constants.WINDOW_WIDTH / 2 - 50, Constants.WINDOW_HEIGHT - 70);

        labelMenu.setColor(Color.BLUE);

        labelMenu.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseEntered(MouseEvent e) {
               labelMenu.setColor(Color.YELLOW);
           }

           @Override
           public void mouseExited(MouseEvent e) {
               labelMenu.setColor(Color.BLUE);
           }

           @Override
           public void mouseClicked(MouseEvent e) {
               pauseWindow = new PauseWindow(Board.this);
           }
        }
        );


        shieldSprite = new GImage("shield.png");
        shieldSprite.setSize(170, 100);

        if (!endless) {
            add(labelTimer);
        }

        add(labelPHealth);
        add(labelMenu);
        add(labelScore);
        add(labelHighscore);
        add(labelLevel);
        add(labelWave);
        add(labelLife);
        drawRemainingLives();
        drawRemainingHP();
    }

    public void generateAll() {
        laser = new GImage("laser.png");
        laser.setSize(800, 50);
        generatePlayer(335, Constants.WINDOW_HEIGHT - 200);
        lvlenemy = currLevel.getLvlEnemy();
        showEnemy();
    }

    public void showEnemy() {
        for (GImage i : lvlenemy) {
            add(i);
            //System.out.println("enemy printed");
        }
    }


    public void resetGame(int newChar, boolean endless) {
        this.endless = endless;
        timeRemaining = Constants.DEFAULT_TIMER;
        currLevel.resetBossHealth();
        shootTimer.stop();
        movementTimer.stop();
        stats.resetScore();
        if (!endless) {
            add(labelTimer);
            labelTimer.setColor(Color.WHITE);
        }        // Reset game state to starting level
        // You may need to reset other game-related variables here
        // For now, I'm just printing a message for demonstration purposes
        for (GImage enemy : lvlenemy) {
            remove(enemy);
        }
        for (GImage life : lives) {
            remove(life);
        }
        for (GImage hp : hps) {
            remove(hp);
        }

        currLevel.removeALLEnemy();
        requestFocus();
        currLevel.resetPlayerHealth();
        currLevel.resetPlayerLife();
        this.level = Constants.START_LEVEL;
        this.wave = Constants.START_WAVE;
        currLevel.setLvlEnemy(level, wave);
        showEnemy();
        labelLevel.setLabel("Level " + level);
        labelWave.setLabel("Wave  " + wave);
        labelScore.setLabel("Score " + stats.getScore());
        labelHighscore.setLabel("Highscore " + stats.getHighscore());

        if (!endless) {
            labelTimer.setLabel("Timer: " + timeRemaining);
        }
        if (endless) {
            if (timer != null) {
                timer.stop();
            }
            if (labelTimer != null) {
                remove(labelTimer);
            }
        }

        lives.clear();

        drawRemainingLives();
        drawRemainingHP();

        for (GImage i : attack) {
            remove(i);
        }
        for (GImage i : enemyAttack) {
            remove(i);
        }
        attack.clear();
        enemyAttack.clear();
        initializePowerUps();

        player.setImage("Ship" + newChar + ".png");

        player.setSize(100, 100);
        player.setLocation(335, Constants.WINDOW_HEIGHT - 200);

        if (!endless) {
            timer.start();
        }
        System.out.println("Game reset to starting level.");
    }

    private void drawRemainingLives() {
        // Adjust these values based on your layout
        int startX = 100;
        int startY = Constants.WINDOW_HEIGHT - 90;
        int lifeSpacing = 40;

        for (int i = 0; i < currLevel.getPlayerLife(); i++) {
            // Draw the spaceship image for each life
            GImage life = new GImage(playerFace, (startX + i * lifeSpacing), startY);
            life.setSize(30, 30);
            lives.add(life);
            add(life);

        }
    }

    private void drawRemainingHP() {
        // Adjust these values based on your layout
        int startX = Constants.WINDOW_WIDTH - 140;
        int startY =   60;
        int lifeSpacing = 30;

        for (int i = 0; i < currLevel.getPlayerHealth(); i++) {
            // Draw the spaceship image for each life
            GImage hp = new GImage("heart.png", (startX + i * lifeSpacing), startY);
            hp.setSize(30, 30);
            hps.add(hp);
            add(hp);

        }
    }

    private void checkCollisions() {
        Iterator<GImage> iterator = attack.iterator();
        while (iterator.hasNext()) {
            GImage shot = iterator.next();

            // Synchronize access to the list
            synchronized (lvlenemy) {
                GObject obj = getElementAt(shot.getX() + shot.getWidth() / 2, shot.getY() - 1);

                if (obj instanceof GImage && lvlenemy.contains(obj)) {
                    remove(shot);
                    iterator.remove();
                    stats.incScore(10);
                    if (stats.getScore() > stats.getHighscore()) {
                        stats.setHighscore(stats.getScore());
                    }
                    labelScore.setLabel("Score " + stats.getScore());
                    // Collision with enemy detected
                    if (level == 5 && currLevel.getBossHealth()>0) {
                        currLevel.setBossHealth(1);
                        System.out.println(currLevel.getBossStat());

                        return;
                    }
                    removeEnemy((GImage) obj);
                    if (new Random().nextInt(1000) < 10 && !shield) {
                        remainingShield = Constants.SHIELD_DURATION;
                        shield = true;
                        shieldSprite.setLocation(player.getX() - 35, player.getY() - 40);
                        add(shieldSprite);
                    }
                    if (new Random().nextInt(1000) < 10 && !doubleFire) {
                        remainingDoubleFire = Constants.DFIRE_DURATION;
                        doubleFire = true;
                    }
                    if (new Random().nextInt(1000) < 10 && level != 5 && !laserActivated) {
                        laserAttack();
                    }
                    if (new Random().nextInt(1000) < 10) {
                        System.out.println("Adding Life");
                        if (currLevel.getPlayerLife() < 3) {
                            int startX = 100;
                            int startY = Constants.WINDOW_HEIGHT - 90;
                            int lifeSpacing = 40;
                            GImage life = new GImage(playerFace, (startX + (currLevel.getPlayerLife()) * lifeSpacing), startY);
                            life.setSize(30, 30);
                            lives.add(life);
                            add(life);
                        }
                        currLevel.givePLife();
                        System.out.println("Lives now " + currLevel.getPlayerLife());

                    }
                }
            }
            if (currLevel.levelStatus()) {
                nextLevel();

                if (level == 5 && wave == 2) {
                    if (endless) {
                        wave = 1;
                        level = 1;
                        return;
                    }
                    stats.resetScore();
                    this.level = -1;
                    timer.stop();
                    winWindow = new YouWinWindow(this, endless);
                    return;
                }

            }
        }
    }

    private void nextLevel() {
        wave++;
        timeRemaining = Constants.DEFAULT_TIMER;

        if (!endless) {
            if (level == 5) {
                bosstime = 0;
                labelTimer.setColor(Color.RED);
                labelTimer.setLabel("Boss Time: " + bosstime + "s");
            } else {
                labelTimer.setColor(Color.WHITE);
                labelTimer.setLabel("Time: " + timeRemaining + "s");
            }
        }

        if (wave > 5) {
            level++;
            wave = 1;
        }

        if (level == 5 && wave == 2 && endless) {
            level = 1;
            wave = 1;
        }

        labelLevel.setLabel("Level " + level);
        labelWave.setLabel("Wave " + wave);

        currLevel.setLvlEnemy(level, wave);
        for (GImage i : lvlenemy) {
            add(i);
        }

    }

    private void laserAttack() {
        add(laser, -5, 400);
        laserActivated = true;
    }

    private void checkPlayerCollision() {
        Iterator<EnemyBullet> iterator = enemyAttack.iterator();
        while (iterator.hasNext()) {
            EnemyBullet bullet = iterator.next();

            if (bullet.getBounds().intersects(player.getBounds())) {
                // Collision with player detected
                remove(bullet);
                iterator.remove();

                if (!shield) {
                    if (level == 5) {
                        handlePlayerDeath();

                    } else {
                        currLevel.setPlayerHealth(1); // Assuming you have a method in Level to decrement player health
                        if (!hps.isEmpty()) {
                            remove(hps.remove(hps.size() - 1)); // Remove a life icon from UI
                        }

                    }
                }

                if (currLevel.getPlayerHealth() <= 0) {
                    handlePlayerDeath();
                    return;
                }
            }
        }
    }

    private void handlePlayerDeath() {
        // Perform actions when player health reaches 0
        currLevel.lossPLife();
        remove(lives.remove(lives.size() - 1)); // Remove a life icon from UI

        if (currLevel.getPlayerLife() > 0) {
            // Player has remaining lives, respawn the player
            respawnPlayer();
        } else {
            // No lives left, handle game over
            gameOver();
        }
    }

    private void respawnPlayer() {
        player.setLocation(335, Constants.WINDOW_HEIGHT - 200); // Respawn at the initial position
        remainingShield = Constants.SPAWN_SHIELD;
        shield = true;
        shieldSprite.setLocation(player.getX() - 35, player.getY() - 40);
        add(shieldSprite);

        currLevel.resetPlayerHealth(); // Reset player health for the new life
        drawRemainingHP();
    }

    private void gameOver() {
        if (timer != null) {
            timer.stop();
        }
        // Implement game over logic, e.g., display a game over message
        stats.resetScore();


        this.level = -1;
        loseWindow = new YouLoseWindow(this, endless);
        System.out.println("Game Over");
        // You can add more game over actions here
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == movement) {
            // Handle movement logic
            moveEnemies();
            moveVariables();
            updatePowerUps();
            if (laserActivated) {
                moveLaser();
            }
        } else if (timer != null && e.getSource() == timer) {
            // Handle timer events
            updateTime();
        }
    }

    private void moveLaser() {
        laser.move(0, -10);

        ArrayList<GImage> enemiesToRemove = new ArrayList<>();

        // Iterate over the enemies
        synchronized (currLevel.getLvlEnemy()) {
            for (GImage enemy : currLevel.getLvlEnemy()) {
                if (enemy.getBounds().intersects(laser.getBounds())) {
                    enemiesToRemove.add(enemy);
                }
            }
        }

        // Remove the enemies outside the synchronized block
        for (GImage enemy : enemiesToRemove) {
            removeEnemy(enemy);
            currLevel.removeEnemy(enemy);
            remove(enemy);
        }

        // Clear the list of enemies to remove
        enemiesToRemove.clear();

        if (currLevel.getLvlEnemy().isEmpty()) {
            remove(laser);
            laserActivated = false;
            nextLevel();
        }
    }

    private void updatePowerUps() {
        if (remainingShield > 0) {
            remainingShield--;
        } else {
            remainingShield = 0;
            shield = false;
            if (shieldSprite != null) {
                remove(shieldSprite);
            }
        }
        if (remainingDoubleFire > 0) {
            remainingDoubleFire--;
        } else {
            remainingDoubleFire = 0;
            doubleFire = false;
        }
    }

    private void updateTime() {
        // Update the timer and display it
        if (level == 5) {
            bosstime++;
            labelTimer.setColor(Color.RED);
            labelTimer.setLabel("Boss Time: " + bosstime + "s");
            return;
        } else {
            timeRemaining--;
        }
        if (timeRemaining == 10) {
            labelTimer.setColor(Color.RED);
        }
        if (timeRemaining >= 0) {
            labelTimer.setLabel("Time: " + timeRemaining + "s");
        } else {
            timer.stop();
            // Handle game over due to time running out
            gameOver();
        }
    }

    public void generatePlayer(double x, double y) {
        player = new GImage(playerFace, x, y);
        player.setSize(100, 100);
        Player.add(player);
        add(player);
        //System.out.println("player added");
    }

    // Add this method to remove enemies
    private synchronized void removeEnemy(GImage enemy) {
        currLevel.removeEnemy(enemy); // Assuming you have a method in Level class to remove enemies
        remove(enemy);
    }



    private final Timer movementTimer = new Timer(Constants.MOVEMENT_TIMER_DELAY, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleKeyPresses();
            if (shield) {
                shieldSprite.setLocation(player.getX() - 35, player.getY() - 40);
            }
        }

    });

    private final Timer shootTimer = new Timer(Constants.SHOOT_TIMER_DELAY, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            GImage player = Player.get(Player.size() - 1);
            if (pressedKeys.contains(KeyEvent.VK_SPACE)) {
                shoot();
            }

        }
    });


    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (!pressedKeys.contains(keyCode)) {
            pressedKeys.add(keyCode);
            if (keyCode == KeyEvent.VK_SPACE) {
                shoot();
                shootTimer.start();
            } else {
                // If the key pressed is for movement, restart the timer with a shorter delay
                handleKeyPresses();
                movementTimer.setDelay(2);
                movementTimer.start();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        pressedKeys.remove(keyCode);
        if (keyCode == KeyEvent.VK_SPACE) {
            shootTimer.stop();
        } else {
            handleKeyPresses();
            // If the key released is for movement, stop the timer
            movementTimer.stop();
            // Reset the timer delay to the original value
            movementTimer.setDelay(5);
        }
    }

    private void handleKeyPresses() {
        if (Player != null && !Player.isEmpty() && level != -1) {
            GImage player = Player.get(Player.size() - 1);
            int playerWidth = 150;

            if (player.getX() <= 40) {
                player.setLocation(40, player.getY());
            } else if (player.getX() + playerWidth >= Constants.WINDOW_WIDTH) {
                player.setLocation(Constants.WINDOW_WIDTH - playerWidth, player.getY());
            }

            if (pressedKeys.contains(KeyEvent.VK_LEFT) && pressedKeys.contains(KeyEvent.VK_RIGHT)) {
                // Both left and right keys are pressed simultaneously
                // Handle this case if needed
            } else if (pressedKeys.contains(KeyEvent.VK_LEFT)) {
                movePlayer(player, -Constants.PLAYER_SPEED, 0);
            } else if (pressedKeys.contains(KeyEvent.VK_RIGHT)) {
                movePlayer(player, Constants.PLAYER_SPEED, 0);
            }
        }
    }



    private void shoot() {
//		GRect shot = new GRect(player.getX()+48,player.getY()-51,10,10);
        if (doubleFire) {
            GImage shot = new GImage("bullets.png", player.getX() + 30, player.getY() - 51);
            shot.setSize(15, 50);
            attack.add(shot);
            add(shot);
            GImage shot2 = new GImage("bullets.png", player.getX() + 50, player.getY() - 51);
            shot2.setSize(15, 50);
            attack.add(shot2);
            add(shot2);
        } else {
            GImage shot = new GImage("bullets.png", player.getX() + 40, player.getY() - 51);
            shot.setSize(15, 50);
            attack.add(shot);
            add(shot);
        }
    }

    // Modify moveVariables method to include collision check
    private void moveVariables() {
        if (level != -1) {
//			ArrayList<GImage> removeAttack = new ArrayList<GImage>();
            for (GImage i : attack) {
                if (i.getY() - 50 <= 0) {
                    removeAttack.add(i);
                }
                i.move(0, -30);

                // Check collision with enemy bullets
                for (GImage enemyBullet : enemyAttack) {
                    if (i.getBounds().intersects(enemyBullet.getBounds())) {
                        removeAttack.add(i);
                        removeEnemyBullets.add(enemyBullet);
                    }
                }
            }
            for (GImage j : removeAttack) {
                attack.remove(j);
                remove(j);
            }

            // Remove collided bullets
            for (GImage playerBullet : removeAttack) {
                attack.remove(playerBullet);
                remove(playerBullet);
            }
            for (GImage enemyBullet : removeEnemyBullets) {
                enemyAttack.remove(enemyBullet);
                remove(enemyBullet);
            }

            checkCollisions();
            checkPlayerCollision();
        }
    }

    private void movePlayer(GImage player, double dx, double dy) {
        player.move(dx, dy);
    }

    private void moveEnemies() {
        if (level != -1) {
//		if (getElementAt(player.getX() + player.getWidth() / 2, player.getY() - 1) instanceof GImage) {
//			removeAll();
//			CustomText lose = new CustomText("YOU SUCK LOL",250,250);
//			add(lose);
//		}
//		ArrayList<GImage> removeEnemy = new ArrayList<GImage>();
            synchronized (lvlenemy) {
                for (GImage i : lvlenemy) {
			/*if(getElementAt(i.getX()+i.getWidth()+1,i.getY()+i.getHeight()/2) instanceof GRect == true){
				lvlEnemy.remove(getElementAt(i.getX()+i.getWidth()+1,i.getY()+i.getHeight()/2));
				remove(getElementAt(i.getX()+i.getWidth()+1,i.getY()+i.getHeight()/2));
			}
			if (i.getX()+i.getWidth()+1 == 300) {
				removeEnemy.add(i);
			}*/
                    if (i.getX() + i.getWidth() + 15 >= Constants.WINDOW_WIDTH) {
                        moveLeft = true;
                        i.move(0, 0);
                    } else if (i.getX() - 1 <= 0) {
                        moveLeft = false;
                        i.move(0, 0);
                    }
                    if (moveLeft) {
                        i.move(-(Constants.SPEED + wave), 0);
                    } else {
                        i.move(Constants.SPEED + wave, 0);
                    }

                    if (level == 5) {
                        if (new Random().nextInt(5000) < 100) {
                            // Shoot a bullet from a random enemy
                            shootBulletFromEnemy(i);
                        }
                    }
                    if (new Random().nextInt(10000) < Constants.BULLET_SHOOT_DIFFICULTY * (wave * level)) {
                        // Shoot a bullet from a random enemy
                        shootBulletFromEnemy(i);
                    }
                }
            }
		/*for(GImage i:removeEnemy) {
			lvlEnemy.remove(i);
			remove(i);
		}*/
            moveEnemyBullets();
        }

    }

    private void shootBulletFromEnemy(GImage enemy) {
        if (level == 5) {
            // Boss on level 5 shoots bullets in three directions

            // Downwards
            EnemyBullet bulletDown = new EnemyBullet("enemy_bullet.png", enemy.getX() + enemy.getWidth() / 2,
                    enemy.getY() + enemy.getHeight(), 15, 50, 0);
            enemyAttack.add(bulletDown);
            add(bulletDown);

            // Left diagonal
            EnemyBullet bulletLeftDiagonal = new EnemyBullet("enemy_bullet.png", enemy.getX(),
                    enemy.getY() + enemy.getHeight(), 15, 50, -5);
            bulletLeftDiagonal.rotate(-30);

            enemyAttack.add(bulletLeftDiagonal);

            add(bulletLeftDiagonal);

            // Right diagonal
            EnemyBullet bulletRightDiagonal = new EnemyBullet("enemy_bullet.png", enemy.getX() + enemy.getWidth(),
                    enemy.getY() + enemy.getHeight(), 15, 50, 5);
            bulletRightDiagonal.rotate(30);

            enemyAttack.add(bulletRightDiagonal);
            add(bulletRightDiagonal);

        } else {
            // Regular shooting logic for other levels
            EnemyBullet bullet = new EnemyBullet("enemy_bullet.png", enemy.getX() + enemy.getWidth() / 2,
                    enemy.getY() + enemy.getHeight(), 5, 20, 0);
            enemyAttack.add(bullet);
            add(bullet);
        }
    }

    private void moveEnemyBullets() {
        if (level != -1) {
            ArrayList<EnemyBullet> removeBullets = new ArrayList<>();

            for (EnemyBullet bullet : enemyAttack) {
                double dy = 10 + wave; // Adjust the speed as needed

                bullet.move(bullet.getDx(), dy);

                if (bullet.getY() > Constants.WINDOW_HEIGHT || bullet.getX() < 0 || bullet.getX() > Constants.WINDOW_WIDTH) {
                    removeBullets.add(bullet);
                }
            }

            // Remove bullets outside the screen
            for (EnemyBullet bullet : removeBullets) {
                enemyAttack.remove(bullet);
                remove(bullet);
            }
        }
    }

    public void init() {
        setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        GRect back = new GRect(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        back.setFillColor(Color.BLACK);
        back.setFilled(true);
        add(back);
        requestFocus();
    }

}
