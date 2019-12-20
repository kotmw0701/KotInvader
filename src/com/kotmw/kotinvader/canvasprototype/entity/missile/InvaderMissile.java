package com.kotmw.kotinvader.canvasprototype.entity.missile;

import com.kotmw.kotinvader.canvasprototype.entity.EntityType;

public class InvaderMissile extends Missile {

    protected InvaderMissile(String imagePath, double x, double y, double speed, double direction, int hitPoints) {
        super(imagePath, EntityType.INVADERMISSILE, x, y, speed, direction, hitPoints);

        setSpeed(8);
        setDirection(90);
    }
}
