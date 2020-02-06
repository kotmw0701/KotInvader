package com.kotmw.kotinvader.gameobjects.entity;

public enum EntityType {
    UNKNOWN("", -1, 0),
    CANNON("/resources/Cannon.png", 1, 1),
    INVADER("/resources/Invader%d.png", 1, 10),
    UFO("/resources/UFO.png", 1, 10),
    CANNONMISSILE("/resources/CannonMissile.png", 1, 5),
    INVADERMISSILE("/resources/InvaderMissile.png", 1, 10),
    Temp1("", 10, 10),
    Temp2("", 50, 10),
    Temp3("", 100, 10);

    private String path;
    private int defaultHitPoints, damage;

    EntityType(String path, int defaultHitPoints, int damage) {
        this.path = path;
        this.defaultHitPoints = defaultHitPoints;
        this.damage = damage;
    }

    public String getPath() { return path; }

    public int getDefaultHitPoints() {
        return defaultHitPoints;
    }

    public int getDamage() {
        return damage;
    }
}
