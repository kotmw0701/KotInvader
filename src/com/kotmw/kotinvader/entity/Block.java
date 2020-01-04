package com.kotmw.kotinvader.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {

    public Block(double x, double y, double w, double h, Color color) {
        super(w, h, color);

        this.setTranslateX(x);
        this.setTranslateY(y);
    }
}
