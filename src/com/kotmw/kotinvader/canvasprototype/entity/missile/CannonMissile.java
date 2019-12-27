package com.kotmw.kotinvader.canvasprototype.entity.missile;

import com.kotmw.kotinvader.canvasprototype.entity.EntityType;

public class CannonMissile extends Missile {

    public CannonMissile(double x, double y) {
        super("resources/CannonMissile.png", EntityType.CANNONMISSILE, x, y);

        setSpeed(10);
        setDirection(270);
    }


}
