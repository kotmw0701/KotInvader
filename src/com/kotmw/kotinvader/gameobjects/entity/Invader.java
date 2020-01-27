package com.kotmw.kotinvader.gameobjects.entity;

import com.kotmw.kotinvader.gameobjects.entity.missiles.InvaderMissile;
import com.kotmw.kotinvader.gameobjects.entity.missiles.Missile;
import com.kotmw.kotinvader.gameobjects.entity.missiles.Shooter;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;

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

        if (num == 1) setColor(Color.PURPLE);
        else if (num == 2) setColor(Color.RED);
        else if (num == 3) setColor(Color.AQUA);
        double width = getImage().getWidth()/2;
        setViewPorts(new Rectangle2D(0, 0, width, 16), new Rectangle2D(width, 0, width, 16));
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
        return new InvaderMissile(getTranslateX()+this.getWidth()/2, getTranslateY()+(this.getHeight()*2));
    }

    @Override
    protected boolean dead() {
        if (aboveInvader != null && active) aboveInvader.changeActive();
        return true;
    }
}
