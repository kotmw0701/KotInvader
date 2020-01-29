package com.kotmw.kotinvader.gameobjects.entity;

import com.kotmw.kotinvader.gui.GameMain;
import com.sun.istack.internal.NotNull;
import javafx.geometry.Rectangle2D;
import javafx.scene.CacheHint;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public abstract class Entity extends ImageView {

    private EntityType entityType;
    private double width, height, speed, direction;
    private boolean alive, invincible, leave;
    private int hitPoints;
    private Rectangle2D[] viewPorts;
    private int viewPortCount;

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
        this.width = getImage().getWidth();
        this.height = getImage().getHeight();

        this.alive = true;
        this.leave = true;
    }

    public void hit(int damage) {
        if (invincible) return;
        hitPoints -= damage;
        if (hitPoints <= 0)
            alive = !dead();
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
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

    public void setViewPorts(Rectangle2D... viewPorts) {
        this.width = viewPorts[0].getWidth();
        this.height = viewPorts[0].getHeight();
        this.viewPorts = viewPorts;
        this.viewPortCount = 1;
        this.setViewport(viewPorts[0]);
    }

    //0.0~1.0
    //-1.0~1.0
    public void setColor(Color color) {
        double hue = color.getHue();
        double brightness = color.getBrightness()*2;
        if (hue > 180.0) hue -= 360.0;

//        System.out.printf("%-15s: Hue=%f, Saturation=%f, Brightness=%f, Contrast=%f\n", this.entityType.toString(), hue/180, color.getSaturation(), brightness-2.0, 1.0);
        this.setEffect(new ColorAdjust(hue/180, color.getSaturation(), 0.0, 1.0));
    }

    protected abstract boolean dead();

    public void move() {
        if (viewPorts != null) {
            if (viewPortCount >= viewPorts.length) viewPortCount = 0;
            this.setViewport(viewPorts[viewPortCount++]);
        }
        if (!leave) {
            if (direction == Direction.RIGHT && getTranslateX()+this.getWidth() >= GameMain.MAIN_X) return;
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
