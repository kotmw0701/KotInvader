package com.kotmw.kotinvader.entity;

public abstract class Enemy extends Entity {

    public Enemy(String imagePath, double x, double y, EntityType entityType) {
        super(imagePath, x, y, entityType);
    }
}
