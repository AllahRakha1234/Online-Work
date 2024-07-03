package edu.pacific.comp55.starter;
import java.awt.*;
import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import static acm.util.JTFTools.pause;

public class Attacks extends GraphicsProgram {
	private static final int START_Y = 300;
	private static final int START_X = 300;
	public static final int PROGRAM_HEIGHT = 600;
	public static final int PROGRAM_WIDTH = 800;

	//<<<<<<< HEAD
//	
////	Location currLoc = getLoc();
////	private static final int currentX = currLoc.x;
////	private static final int currentY = currLoc.y;
//
//=======
//>>>>>>> branch 'main' of https://github.com/comp55/final-project-group-a3.git
	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
		requestFocus();
	}

	void specialAttack() {
		// TODO set start location to character's current location: getLoc()
		GOval special = new GOval(400, START_Y, 7, 7);
		special.setColor(Color.blue);
		special.setFilled(true);
		add(special);
		// TODO make shape move from character location upwards
	}

	void normalShot() {
		// TODO set start location to character's current location: getLoc()
		GOval shot = new GOval(350, START_Y, 7, 7);
		shot.setColor(Color.red);
		shot.setFilled(true);
		add(shot);
		// TODO make shape move from character location upwards
	}

	void laserShot() {
		// TODO set start location to enemy location: getLoc()
		GRect laser = new GRect(START_X, START_Y, 3, 15);
		laser.setColor(Color.green);
		laser.setFilled(true);
		add(laser);
		// TODO make shape move from character location upwards
	}

	void areaAttack() {
		// TODO set location & size
		GRect areaAttack = new GRect(450, START_Y, 300, 100);
		areaAttack.setColor(Color.orange);
		areaAttack.setFilled(false);
		add(areaAttack);
		// TODO make shape move from character location upwards
	}


	//TO_TEST_SHAPES
	@Override
	public void run() {
		// TODO Auto-generated method stub
		GOval special = new GOval(400, START_Y, 7, 7);
		special.setColor(Color.blue);
		special.setFilled(true);
		add(special);

		GOval shot = new GOval(350, START_Y, 7, 7);
		shot.setColor(Color.red);
		shot.setFilled(true);
		add(shot);
//		for (int i = 0; i < 20; i++) {
//			shot.move(0, -10);
//			pause(100);
//		}

		GRect laser = new GRect(START_X, START_Y, 3, 15);
		laser.setColor(Color.green);
		laser.setFilled(true);
		add(laser);

		GRect areaAttack = new GRect(450, START_Y, 300, 100);
		areaAttack.setColor(Color.orange);
		areaAttack.setFilled(false);
		add(areaAttack);

		for (int i = 0; i < 20; i++) {
			shot.move(0, -10);
			pause(100);
		}
	}
}

//	public static void main(String[] args) {
//		new Attacks().start();
//	}
//
//}

