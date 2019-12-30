package com.kotmw.kotinvader.gui;

import com.kotmw.kotinvader.PlayStatus;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class GameRemain extends HBox {

    /*                                            1200px
    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
    ┃　　凸 凸 凸　　　                                                                                 ┃ 50px
    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
     */

    Image cannon;

    public GameRemain(PlayStatus player) {
        cannon = new Image(getClass().getResource("/resources/Cannon.png").toExternalForm());
        this.setPrefSize(GameMain.REMAIN_X, GameMain.REMAIN_Y);
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER_LEFT);
        player.remainProperty().addListener(((observable, oldValue, newValue) -> setRemain(newValue.intValue())));

        setRemain(3);
    }

    private void setRemain(int remain) {
        getChildren().clear();
        for (int i = 0; i < remain; i++)
            this.getChildren().add(new ImageView(getClass().getResource("/resources/Cannon.png").toExternalForm()));
    }
}
