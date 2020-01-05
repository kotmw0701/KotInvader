package com.kotmw.kotinvader.gameobjects.entity.missiles;

import com.kotmw.kotinvader.gameobjects.entity.EntityType;

public class InvaderMissile extends Missile {

    public InvaderMissile(double x, double y) {
        super("/resources/InvaderMissile.png", x, y, EntityType.INVADERMISSILE);
        setSpeed(3);
        setDirection(Direction.DOWN);
    }
}