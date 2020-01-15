package com.kotmw.kotinvader.gameobjects.block;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {

    private final int baseHitPoint;
    private double hitPoint;

    protected Block(double x, double y, double w, double h, int hitPoint) {
        super(w, h, Color.GREEN);

        this.setTranslateX(x);
        this.setTranslateY(y);

        this.baseHitPoint = hitPoint;
        this.hitPoint = hitPoint;
    }

    public void hit(double damage) {
        hitPoint -= damage;
        double percent = hitPoint / baseHitPoint;
        if (percent >= 0.75) this.setFill(Color.GREEN);
        else if (percent >= 0.50) this.setFill(Color.YELLOW);
        else if (percent >= 0.25) this.setFill(Color.ORANGE);
        else if (percent > 0.00) this.setFill(Color.RED);
        else this.setFill(Color.TRANSPARENT);
    }

    /*
    100% -> GREEN
    75% -> YELLOW
    50% -> ORANGE
    25% -> RED
    0% -> TRANSPARENT
     */
}
