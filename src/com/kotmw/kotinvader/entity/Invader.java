package com.kotmw.kotinvader.entity;

import com.kotmw.kotinvader.entity.missiles.InvaderMissile;
import com.kotmw.kotinvader.entity.missiles.Missile;
import com.kotmw.kotinvader.entity.missiles.Shooter;

public class Invader extends Enemy implements Shooter {

    private boolean active;
    private Invader abobeInvader;

    public Invader(double x, double y) {
        this(x, y, null, false);
    }

    public Invader(double x, double y, Invader abobeInvader) {
        this(x, y, abobeInvader, false);
    }

    public Invader(double x, double y, Invader abobeInvader, boolean active) {
        super("resources/Invader.png", x, y, EntityType.INVADER);
        this.abobeInvader = abobeInvader;
        this.active = active;
    }

    private void changeActive() {
        if (!isAlive()) {
            if (abobeInvader != null) abobeInvader.changeActive();
        } else active = true;
    }

    @Override
    public Missile shoot() {
        return new InvaderMissile(getTranslateX()+getImage().getWidth()/2, getTranslateY()+getImage().getHeight());
    }

    @Override
    protected boolean dead() {
        if (abobeInvader != null && active) abobeInvader.changeActive();
        return false;
    }
}
