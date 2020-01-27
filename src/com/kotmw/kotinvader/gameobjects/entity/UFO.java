package com.kotmw.kotinvader.gameobjects.entity;

import com.kotmw.kotinvader.gui.GameMain;
import javafx.scene.paint.Color;

public class UFO extends Enemy {

    public UFO() {
        super("/resources/UFO.png", 0, 20, EntityType.UFO);

        double random = Math.random();

        setTranslateX(random > 0.5 ? 0 : GameMain.MAIN_X);

        setColor(Color.DARKMAGENTA);
        setScore(100);
        setSpeed(1.2);
        setDirection(random > 0.5 ? 0 : 180);
    }

    @Override
    protected boolean dead() {
        return true;
    }
}
