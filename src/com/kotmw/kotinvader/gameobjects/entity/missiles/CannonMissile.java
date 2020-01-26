package com.kotmw.kotinvader.gameobjects.entity.missiles;

import com.kotmw.kotinvader.gameobjects.entity.EntityType;
import javafx.scene.paint.Color;

public class CannonMissile extends Missile {

    public CannonMissile(double x, double y) {
        super("/resources/CannonMissile.png", x, y, EntityType.CANNONMISSILE);
        setColor(Color.AQUA);
        setSpeed(10);
        setDirection(Direction.UP);
    }
}
