package edu.pacific.comp55.starter;

import acm.graphics.GImage;

public class PowerUp extends GImage {
    private String type;
    private int duration;

    public PowerUp(String image, String type, double x, double y, int duration) {
        super(image, x, y);
        this.type = type;
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }
}
