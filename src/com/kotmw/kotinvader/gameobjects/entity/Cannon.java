package com.kotmw.kotinvader.gameobjects.entity;

import com.kotmw.kotinvader.gameobjects.entity.missiles.CannonMissile;
import com.kotmw.kotinvader.gameobjects.entity.missiles.Missile;
import com.kotmw.kotinvader.gameobjects.entity.missiles.Shooter;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class Cannon extends Entity implements Shooter {

    private double initX;
    private double initY;

    public Cannon(double x, double y) {
        super("/resources/Cannon.png", x, y, EntityType.CANNON);
        this.initX = x;
        this.initY = y;

        setLeave(false);
    }

    @Override
    public Missile shoot() {
        return new CannonMissile(getTranslateX()+getImage().getWidth()/2, getTranslateY()-getImage().getHeight()/2);
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
