package edu.pacific.comp55.starter;

import java.util.ArrayList;

import java.awt.event.KeyEvent;

import acm.graphics.GImage;
import acm.program.GraphicsProgram;

public class Player{

	public void setPHealth(int attackStrength) {
		this.health = health-attackStrength;
	}public void resetPHealth() {
		this.health = Constants.DEFAULT_HEALTH;
	}

	private int health;

	public int getLives() {
		return lives;
	}

	public void lossLife(){
		this.lives = lives-1;
	}
	public int giveLife(){
		if (this.lives < Constants.DEFAULT_LIVES){
			this.lives = lives+1;
		}
		return lives;
	}

	private int lives;
	
	/*public void run() {
		generatePlayer(335,450);
		addKeyListeners();
	}*/
		
	public Player() {
		health = Constants.DEFAULT_HEALTH;
		lives = Constants.DEFAULT_LIVES;
	}
	
	public int getPHealth() {
		return health;
	}

//	public static void main(String[] args) {
//	}

	public void resetLives() {
		lives = Constants.DEFAULT_LIVES;
	}
}
