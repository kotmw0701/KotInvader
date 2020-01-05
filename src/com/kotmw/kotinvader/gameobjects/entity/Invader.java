package com.kotmw.kotinvader.gameobjects.entity;

import com.kotmw.kotinvader.gameobjects.entity.missiles.InvaderMissile;
import com.kotmw.kotinvader.gameobjects.entity.missiles.Missile;
import com.kotmw.kotinvader.gameobjects.entity.missiles.Shooter;

public class Invader extends Enemy implements Shooter {

    private boolean active;
    private Invader aboveInvader;
    private int invaderType;

    public Invader(double x, double y, int num) {
        this(x, y, null, false, num);
    }

    public Invader(double x, double y, Invader aboveInvader, int num) {
        this(x, y, aboveInvader, false, num);
    }

    public Invader(double x, double y, Invader aboveInvader, boolean active, int num) {
        super("/resources/Invader"+num+".png", x, y, EntityType.INVADER);
        this.aboveInvader = aboveInvader;
        this.active = active;
        this.invaderType = num;

        setScore(num*10);
        setSpeed(5.0);
    }

    public boolean isActive() {
        return active;
    }

    public int getInvaderType() {
        return invaderType;
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
        return true;
    }
}
