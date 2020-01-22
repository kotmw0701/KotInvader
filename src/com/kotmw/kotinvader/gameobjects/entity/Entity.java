package com.kotmw.kotinvader.gameobjects.entity;

import com.kotmw.kotinvader.event.MissileHitEvent;
import com.kotmw.kotinvader.gui.GameMain;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Entity extends ImageView {

    private EntityType entityType;
    private double speed, direction;
    private boolean alive, invincible, leave;
    private int hitPoints;

    protected Entity(String imagePath, double x, double y, EntityType entityType) {
        this(imagePath, x, y, entityType, 0.0, 0.0, entityType.getDefaultHitPoints());
    }

    protected Entity(String imagePath, double x, double y, EntityType entityType, double speed, double direction, int hitPoints) {
        this.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
        this.setCache(true);
        this.setCacheHint(CacheHint.SPEED);
        this.setTranslateX(x);
        this.setTranslateY(y);

        this.entityType = entityType;
        this.speed = speed;
        this.direction = direction;
        this.hitPoints = hitPoints;

        this.alive = true;
        this.leave = true;
    }

    public void setOnHitEvent(EventHandler<MissileHitEvent> value) {
        this.addEventHandler(MissileHitEvent.MISSILE_HIT, value);
    }

    public void hit(int damage) {
        this.fireEvent(new MissileHitEvent());
        if (invincible) return;
        hitPoints -= damage;
        if (hitPoints <= 0)
            alive = !dead();
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

    public boolean isInvincible() {
        return invincible;
    }

    public boolean isLeave() {
        return leave;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public void setLeave(boolean leave) {
        this.leave = leave;
    }

    protected abstract boolean dead();

    public void move() {
        if (!leave) {
            if (direction == Direction.RIGHT && getTranslateX()+getImage().getWidth() >= GameMain.MAIN_X) return;
            else if (direction == Direction.LEFT && getTranslateX() <= 0) return;
            else if (direction == Direction.UP && getTranslateY() <= 0) return;
            else if (direction == Direction.DOWN && getTranslateY()+getImage().getHeight() >= GameMain.MAIN_Y) return;
        }
        this.setTranslateX(this.getTranslateX() + speed * Math.cos(Math.toRadians(direction)));
        this.setTranslateY(this.getTranslateY() + speed * Math.sin(Math.toRadians(direction)));
    }

    public interface Direction {
        double RIGHT =  0.0;
        double DOWN =   90.0;
        double LEFT =   180.0;
        double UP =     270.0;
    }
}
