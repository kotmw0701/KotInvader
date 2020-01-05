package com.kotmw.kotinvader.gameobjects.block;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {

    protected Block(double x, double y, double w, double h, Color color) {
        super(w, h, color);

        this.setTranslateX(x);
        this.setTranslateY(y);
    }
}
