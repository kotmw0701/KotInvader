package com.kotmw.kotinvader.entity;

public class Invader extends Enemy {

    Invader(String imagePath, double x, double y) {
        super(imagePath, x, y, EntityType.INVADER);
    }
}
