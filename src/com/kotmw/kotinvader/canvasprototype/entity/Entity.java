package com.kotmw.kotinvader.canvasprototype.entity;

import com.kotmw.kotinvader.canvasprototype.gui.GameMain;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Entity extends Image {

    private EntityType type;
    private double x, y, speed, direction;
    private boolean alive;
    private int hitPoints;

    protected Entity(String imagePath, EntityType type, double x, double y) {
        this(imagePath, type, x, y, 0, 0, type.getDefaultHitPoints());
    }

    protected Entity(String imagePath, EntityType type, double x, double y, double speed, double direction, int hitPoints) {
        super(imagePath);

        this.type = type;
        this.speed = speed;
        this.direction = direction;
        this.hitPoints = hitPoints;
        this.x = x;
        this.y = y;
    }

    public void hit(int damage) {
        hitPoints -= damage;
        if (hitPoints < 0) alive = false;
    }

    public EntityType getType() {
        return type;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
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

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void move() {
        x = x + speed * Math.cos(Math.toRadians(direction));
        y = y + speed * Math.sin(Math.toRadians(direction));
    }

    public void draw(GraphicsContext canvas) {
        canvas.drawImage(this, x, y);
    }

    public BoundingBox getBoundingBox() {
        return new BoundingBox(x, y, getWidth(), getHeight());
    }
}
