package edu.pacific.comp55.starter;

public class Stats {
    public int getScore() {
        return score;
    }

    public void incScore(int newScore) {
        this.score = score + newScore;
    }

    public void resetScore() {
        this.score = 0;
    }

    private int score = 0;

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    private int highscore = Constants.DEFAULT_HIGHSCORE;

    int getHealth() {
        //return health;
        return 0;
    }

    int getLoc() {
        //return location;
        return 0;
    }

    boolean getCoolDown() {

        return false;
    }

}
