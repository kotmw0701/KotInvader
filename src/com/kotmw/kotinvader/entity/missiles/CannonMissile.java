package com.kotmw.kotinvader.entity.missiles;

import com.kotmw.kotinvader.entity.EntityType;

public class CannonMissile extends Missile {

    public CannonMissile(double x, double y) {
        super("resources/CannonMissile.png", x, y, EntityType.CANNONMISSILE);
        setSpeed(10);
        setDirection(270);
    }
}
