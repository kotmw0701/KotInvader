package com.kotmw.kotinvader.entity;

import com.kotmw.kotinvader.entity.missiles.InvaderMissile;
import com.kotmw.kotinvader.entity.missiles.Missile;
import com.kotmw.kotinvader.entity.missiles.Shooter;

public class Invader extends Enemy implements Shooter {

    private boolean active;
    private Invader aboveInvader;

    public Invader(double x, double y) {
        this(x, y, null, false);
    }

    public Invader(double x, double y, Invader aboveInvader) {
        this(x, y, aboveInvader, false);
    }

    public Invader(double x, double y, Invader aboveInvader, boolean active) {
        super("/resources/Invader.png", x, y, EntityType.INVADER);
        this.aboveInvader = aboveInvader;
        this.active = active;

        setSpeed(5.0);
    }

    public boolean isActive() {
        return active;
    }

    private void changeActive() {
        if (!isAlive()) {
            if (aboveInvader != null) aboveInvader.changeActive();
        } else active = true;
    }

    @Override
    public Missile shoot() {
        return new InvaderMissile(getTranslateX()+getImage().getWidth()/2, getTranslateY()+(getImage().getHeight()*2));
    }

    @Override
    protected boolean dead() {
        if (aboveInvader != null && active) aboveInvader.changeActive();
        return false;
    }
}
