package edu.pacific.comp55.starter;

import java.util.ArrayList;

import acm.graphics.GImage;

public class Level {//calls enemies, board will call level!
    private ArrayList<GImage> lvlEnemy;
    private final Enemy enemy;
    private ArrayList<GImage> playerList;
    private final Player player;

    public Level(Board board, int x, int y) {
        ArrayList<GImage> lvlEnemy = new ArrayList<GImage>();
        player = new Player();
        enemy = new Enemy(board);
        setLvlEnemy(x, y);
    }

    public boolean levelStatus() {
        return lvlEnemy.isEmpty();
    }

    public void setLvlEnemy(int level, int wave) {
        enemy.formation(level, wave);
        lvlEnemy = enemy.getEnemies();
    }

    public ArrayList<GImage> getLvlEnemy() {
        return lvlEnemy;
    }

    // Add this method to remove enemies
    public void removeEnemy(GImage enemyToRemove) {
        lvlEnemy.remove(enemyToRemove);
    }

    public void removeALLEnemy() {
        lvlEnemy.clear();
        enemy.removeAllEnemy();
    }

    public int getBossStat() {
        return enemy.getBossHealth();
    }

    public int getPlayerHealth() {
        return player.getPHealth();
    }

    public void setPlayerHealth(int attack) {
        player.setPHealth(attack);
    }

    public void resetPlayerHealth() {
        player.resetPHealth();
    }

    public void lossPLife() {
        player.lossLife();
    }

    public int givePLife() {
        return player.giveLife();
    }

    public int getPlayerLife() {
        return player.getLives();
    }

    public void resetPlayerLife() {
        player.resetLives();
    }

    public int getBossHealth() {
        return enemy.getBossHealth();
    }

    public void setBossHealth(int attack) {
        enemy.bossHealth = enemy.getBossHealth() - attack;
    }
    public void resetBossHealth() {
        enemy.resetBoss();
    }

//    public static void main(String[] args) {
//        //new Level().start();
//    }
}
