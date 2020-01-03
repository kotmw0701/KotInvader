package com.kotmw.kotinvader.entity;

public abstract class Enemy extends Entity {

    private int score = 0;

    public Enemy(String imagePath, double x, double y, EntityType entityType) {
        super(imagePath, x, y, entityType);
    }

    public int getScore() {
        return score;
    }

    protected void setScore(int score) {
        this.score = score;
    }
}
