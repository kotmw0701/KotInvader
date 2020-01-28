package com.kotmw.kotinvader.gameobjects.entity;

import com.kotmw.kotinvader.gameobjects.entity.missiles.CannonMissile;
import com.kotmw.kotinvader.gameobjects.entity.missiles.Missile;
import com.kotmw.kotinvader.gameobjects.entity.missiles.Shooter;
import javafx.animation.FadeTransition;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Cannon extends Entity implements Shooter {

    private double initX;
    private double initY;
    private CannonMissile missile;

    public Cannon(double x, double y) {
        super("/resources/Cannon.png", x, y, EntityType.CANNON);
        this.initX = x;
        this.initY = y;

        setColor(Color.AQUA);
        setLeave(false);
    }

    @Override
    public Missile shoot() {
        if (missile != null && missile.isAlive()) return null;
        return missile = new CannonMissile(getTranslateX()+this.getWidth()/2, getTranslateY()-this.getHeight()/2);
    }

    @Override
    protected boolean dead() {
        return true;
    }

    public Cannon respawn() {
        Cannon cannon = new Cannon(initX, initY);
        cannon.setInvincible(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.1), cannon);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(5);
        fadeTransition.setOnFinished(event -> cannon.setInvincible(false));
        fadeTransition.play();
        return cannon;
    }
}
