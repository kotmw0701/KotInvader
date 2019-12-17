package com.kotmw.kotinvader.entity;

import com.kotmw.kotinvader.entity.missiles.CannonMissile;
import com.kotmw.kotinvader.entity.missiles.Missile;
import com.kotmw.kotinvader.entity.missiles.Shooter;

public class Cannon extends Entity implements Shooter {

    public Cannon(double x, double y) {
        super("resources/Cannon.png", x, y, EntityType.CANNON);
    }

    @Override
    public Missile shoot() {
        return new CannonMissile(getTranslateX()+getImage().getWidth()/2, getTranslateY());
    }
}
