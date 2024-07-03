package edu.pacific.comp55.starter;

import acm.graphics.GImage;

public class EnemyBullet extends GImage {
    private double dx;

    public EnemyBullet(String imageName, double x, double y, double width, double height, double dx) {
        super(imageName, x, y);
        setSize(width, height);
        this.dx = dx;
    }


    public double getDx() {
        return dx;
    }
}