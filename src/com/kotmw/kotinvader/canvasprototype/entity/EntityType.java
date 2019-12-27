package com.kotmw.kotinvader.canvasprototype.entity;

public enum EntityType {
    UNKNOWN(-1, 0),
    CANNON(1, 1),
    INVADER(1, 10),
    UFO(1, 10),
    CANNONMISSILE(1, 5),
    INVADERMISSILE(1, 10),
    Temp1(10, 10),
    Temp2(50, 10),
    Temp3(100, 10);

    private int defaultHitPoints, damage;

    EntityType(int defaultHitPoints, int damage) {
        this.defaultHitPoints = defaultHitPoints;
        this.damage = damage;
    }

    public int getDefaultHitPoints() {
        return defaultHitPoints;
    }

    public int getDamage() {
        return damage;
    }
}
