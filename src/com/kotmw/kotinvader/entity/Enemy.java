package com.kotmw.kotinvader.entity;

import com.kotmw.kotinvader.entity.Entity;
import com.kotmw.kotinvader.entity.EntityType;

public class Enemy extends Entity {

    public Enemy(String imagePath, double x, double y, EntityType entityType) {
        super(imagePath, x, y, entityType);
    }
}
