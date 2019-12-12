package com.kotmw.kotinvader.entity;

import javafx.scene.image.ImageView;

public abstract class Entity extends ImageView {

    private EntityType entityType;
    private double speed;
    private boolean alive;
    private int hitPoints;

    public Entity(String imagePath, double x, double y, EntityType entityType) {
        this(imagePath, x, y, entityType, 0.0, entityType.getDefaultHitPoints());
    }

    public Entity(String imagePath, double x, double y, EntityType entityType, double speed, int hitPoints) {
        super(imagePath);
        this.setTranslateX(x);
        this.setTranslateY(y);

        this.entityType = entityType;
        this.speed = speed;
        this.hitPoints = hitPoints;
    }

    public boolean hit(int damage) {
        hitPoints -= damage;
        if (hitPoints < 0)
            alive = false;
        return alive;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
