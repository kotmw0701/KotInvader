package com.kotmw.kotinvader.gui;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GameContainer extends Pane {

    public GameContainer() {
        this.setPrefSize(GameMain.MAIN_X, GameMain.MAIN_Y);
        this.setBackground(new Background(new BackgroundFill(Color.web("#080808"), CornerRadii.EMPTY, Insets.EMPTY)));
    }

}
