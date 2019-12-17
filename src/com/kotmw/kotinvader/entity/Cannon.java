package com.kotmw.kotinvader.entity;

import com.kotmw.kotinvader.entity.missiles.Shooter;

import java.io.File;

public class Cannon extends Entity implements Shooter {

    public Cannon(double x, double y) {
        super(new File("Cannon.png").getPath(), x, y, EntityType.CANNON);
    }
}
