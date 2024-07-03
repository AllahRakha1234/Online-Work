package edu.pacific.comp55.starter;

import acm.graphics.GImage;
import acm.util.RandomGenerator;

import java.util.ArrayList;
import java.util.Random;

public class Enemy {

	private ArrayList<GImage> enemies;
	private ArrayList<Bullet> bullets;
	private Board board;
	int bossHealth;

	public Enemy() {
		enemies = new ArrayList<>();
		bossHealth = 0;
		bullets = new ArrayList<>();

	}
	public Enemy(Board board) {
		enemies = new ArrayList<>();
		bossHealth = 0;
		bullets = new ArrayList<>();
		this.board = board;
	}

	public void formation(int level, int wave) {
		enemies.clear(); // Clear existing enemies

		if (level == 1) {
			generateWave1(wave);
		} else if (level == 2) {
			generateWave2(wave);
		} else if (level == 3) {
			generateWave3(wave);
		} else if (level == 4) {
			generateWave4(wave);
		} else if (level == 5) {
			generateBoss(400, 25);
			bossHealth = Constants.BOSS_HEALTH;
		} else {
			// Handle additional levels or random formations
			formation(RandomGenerator.getInstance().nextInt(1, 5), wave);
		}
	}

	private void generateBoss(double x, double y) {
		GImage enemy = new GImage("boss.png", x - 75, y + 70);
		enemy.setSize(150, 100);
		enemies.add(enemy);
		bossHealth = Constants.BOSS_HEALTH;
	}

	private void generateEnemy(double x, double y) {
		GImage enemy = new GImage("enemy.png", x, y);
		enemy.setSize(Constants.ENEMY_SIZE, Constants.ENEMY_SIZE);
		enemies.add(enemy);
	}

	private void generateWave1(int wave) {
		for (int i = 0; i < wave; i++) {
			generateEnemy(75 + (150 * i), 100);
			generateEnemy(75 + (150 * i), 200);
			generateEnemy(75 + (150 * i), 300);
		}
	}

	private void generateWave2(int wave) {
		for (int i = 1; i <=wave; i++) {
			generateEnemy(150 + 525 - (i * 100), 100);
			generateEnemy(150 + 525 - (i * 100), 200);
			generateEnemy(150 + 525 - (i * 100), 300);
		}
		for (int i = 1; i <= wave; i++) {
			generateEnemy(100 + 525 - (i * 100), 150);
			generateEnemy(100 + 525 - (i * 100), 250);
		}
	}

	private void generateWave3(int wave) {
		for (int i = 1; i <= wave; i++) {
			generateEnemy(150 + 550 - (i * 100), 100);
			generateEnemy(150 + 550 - (i * 100), 200);
			generateEnemy(150 + 550 - (i * 100), 300);
		}
	}

	private void generateWave4(int wave) {
		for (int i = 1; i <= wave; i++) {
			generateEnemy(150 + 525 - (i * 100), 100);
			generateEnemy(150 + 525 - (i * 100), 200);
			generateEnemy(150 + 525 - (i * 100), 300);
		}
		for (int i = 1; i <= wave; i++) {
			generateEnemy(100 + 525 - (i * 100), 150);
			generateEnemy(100 + 525 - (i * 100), 250);
		}
	}

	public int getBossHealth() {
		return bossHealth;
	}

	public ArrayList<GImage> getEnemies() {
		return enemies;
	}

	public void removeAllEnemy (){
		for (GImage i: enemies){
			board.remove(i);
		}
	}

	public void shootBullet(int difficulty) {
		for (GImage enemy : enemies) {
			if (new Random().nextInt(100) < difficulty) {
				Bullet bullet = new Bullet(enemy.getX() + enemy.getWidth() / 2, enemy.getY() + enemy.getHeight());
				bullets.add(bullet);
				// Assuming you have a method to add bullets to the GraphicsProgram
				board.add(bullet);
			}
		}
	}

	public void moveBullets() {
		ArrayList<Bullet> removeBullets = new ArrayList<>();
		for (Bullet bullet : bullets) {
			bullet.move();
			if (bullet.getY() < 0) {
				removeBullets.add(bullet);
			}
		}
		for (Bullet bullet : removeBullets) {
			bullets.remove(bullet);
			// Assuming you have a method to remove bullets from the GraphicsProgram
			board.remove(bullet);
		}
	}

	public void resetBoss() {
		bossHealth = Constants.BOSS_HEALTH;
	}
}
