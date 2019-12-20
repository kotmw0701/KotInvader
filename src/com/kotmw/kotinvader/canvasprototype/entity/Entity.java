package com.kotmw.kotinvader.canvasprototype.entity;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Entity extends Image {

    private EntityType type;
    private double speed, direction;
    private boolean alive;
    private int hitPoints;
    private Point2D point;

    protected Entity(String imagePath, EntityType type, double x, double y) {
        this(imagePath, type, x, y, 0, 0, type.getDefaultHitPoints());
    }

    protected Entity(String imagePath, EntityType type, double x, double y, double speed, double direction, int hitPoints) {
        super(imagePath);

        this.type = type;
        this.speed = speed;
        this.direction = direction;
        this.hitPoints = hitPoints;
        point = new Point2D(x, y);
    }

    public void hit(int damage) {
        hitPoints -= damage;
        if (hitPoints < 0) alive = false;
    }

    public EntityType getType() {
        return type;
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

    public int getHitPoints() {
        return hitPoints;
    }

    public Point2D getPoint() {
        return point;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void move() {
        this.point.add(
                speed * Math.cos(Math.toRadians(direction)),
                speed * Math.sin(Math.toRadians(direction)));
    }

    public BoundingBox getBoundingBox() {
        return new BoundingBox(point.getX(), point.getY(), getWidth(), getHeight());
    }
}
