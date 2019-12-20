package com.kotmw.kotinvader.canvasprototype.entity;

import com.kotmw.kotinvader.canvasprototype.entity.missile.CannonMissile;
import com.kotmw.kotinvader.canvasprototype.entity.missile.Missile;

public class Cannon extends Entity implements Shooter {

    protected Cannon(double x, double y) {
        super("resources/Cannon.png", EntityType.CANNON, x, y);
    }

    @Override
    public Missile shoot() {
        return new CannonMissile(getPoint().getX()/2, getPoint().getY());
    }
}
