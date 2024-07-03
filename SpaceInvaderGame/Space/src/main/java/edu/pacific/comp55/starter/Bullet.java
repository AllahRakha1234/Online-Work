package edu.pacific.comp55.starter;

import acm.graphics.GImage;

public class Bullet extends GImage {

    public Bullet(double x, double y) {
        super("bullets.png", x, y);
        setSize(10, 10);
        rotate(180);

    }

    public void move() {
        move(0, -Constants.BULLET_SPEED);
    }
}