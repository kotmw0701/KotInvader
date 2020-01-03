package com.kotmw.kotinvader.entity;

import com.kotmw.kotinvader.gui.GameMain;

public class UFO extends Enemy {

    public UFO() {
        super("/resources/UFO.png", 0, 20, EntityType.UFO);

        double random = Math.random();

        setTranslateX(random > 0.5 ? 0 : GameMain.MAIN_X);

        setScore(100);
        setSpeed(1.2);
        setDirection(random > 0.5 ? 0 : 180);
    }

    @Override
    protected boolean dead() {
        return true;
    }
}
