package com.kotmw.kotinvader.gui;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class CoverPane extends VBox {

    public void CoverPane() {
        this.setPrefSize(GameMain.WINDOW_X, GameMain.WINDOW_Y);
        this.setFillWidth(false);
        this.setAlignment(Pos.CENTER);
    }
}
