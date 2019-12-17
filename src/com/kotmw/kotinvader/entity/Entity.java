package com.kotmw.kotinvader.entity;

import javafx.scene.image.ImageView;

public class Entity extends ImageView {

    private EntityType entityType;
    private double speed, direction;
    public boolean alive = true;
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

    public void move() {
        this.setTranslateX(this.getTranslateX() + speed * Math.cos(Math.toRadians(direction)));
        this.setTranslateY(this.getTranslateY() + speed * Math.sin(Math.toRadians(direction)));
    }
}
