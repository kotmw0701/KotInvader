package com.kotmw.kotinvader.entity.missiles;

import com.kotmw.kotinvader.entity.Entity;
import com.kotmw.kotinvader.entity.EntityType;

public class Missile extends Entity {

    public Missile(String imagePath, double x, double y, EntityType entityType) {
        super(imagePath, x, y, entityType);
    }

    @Override
    protected boolean dead() {
        return true;
    }
}
