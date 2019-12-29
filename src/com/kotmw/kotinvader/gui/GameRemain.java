package com.kotmw.kotinvader.gui;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class GameRemain extends HBox {

    /*                                            1200px
    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
    ┃　　凸 凸 凸　　　                                                                                 ┃ 50px
    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
     */

    public GameRemain() {
        this.setPrefSize(GameMain.REMAIN_X, GameMain.REMAIN_Y);
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER_LEFT);

        setRemain();
    }

    private void setRemain() {
        int remain = 3;
        for (int i = 0; i < remain; i++) {
            ImageView imageView = new ImageView("resources/Cannon.png");
            this.getChildren().add(imageView);
        }
    }
}
