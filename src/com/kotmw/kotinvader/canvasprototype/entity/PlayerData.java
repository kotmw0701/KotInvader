package com.kotmw.kotinvader.canvasprototype.entity;

import com.kotmw.kotinvader.canvasprototype.gui.GameMain;

public class PlayerData {

    private final Cannon cannon;
    private int remain;
    private int score;

    public PlayerData() {
        cannon = new Cannon(GameMain.MAIN_X/2, 500);
        remain = 3;
    }

    public Cannon getCannon() {
        return cannon;
    }

    public int getRemain() {
        return remain;
    }

    public int increaseRemain() {
        return ++remain;
    }

    public int decreaseRemain() {
        return --remain;
    }

    public int getScore() {
        return score;
    }

    public int addScore(int amount) {
        score += amount;
        return score;
    }
}

