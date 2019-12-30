package com.kotmw.kotinvader.entity;

import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Entity extends ImageView {

    private EntityType entityType;
    private double speed, direction;
    private boolean alive = true;
    private int hitPoints;

    protected Entity(String imagePath, double x, double y, EntityType entityType) {
        this(imagePath, x, y, entityType, 0.0, entityType.getDefaultHitPoints());
    }

    protected Entity(String imagePath, double x, double y, EntityType entityType, double speed, int hitPoints) {
        this.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
        this.setCache(true);
        this.setCacheHint(CacheHint.SPEED);
        this.setTranslateX(x);
        this.setTranslateY(y);

        this.entityType = entityType;
        this.speed = speed;
        this.hitPoints = hitPoints;
    }

    public boolean hit(int damage) {
        hitPoints -= damage;
        if (hitPoints < 0)
            alive = dead();
        return alive;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDirection() {
        return direction;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    protected abstract boolean dead();

    public void move() {
        this.setTranslateX(this.getTranslateX() + speed * Math.cos(Math.toRadians(direction)));
        this.setTranslateY(this.getTranslateY() + speed * Math.sin(Math.toRadians(direction)));
    }
}
