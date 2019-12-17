package com.kotmw.kotinvader.entity;

public class Invader extends Entity {

    Invader(String imagePath, double x, double y) {
        super(imagePath, x, y, EntityType.INVADER);
    }
}
