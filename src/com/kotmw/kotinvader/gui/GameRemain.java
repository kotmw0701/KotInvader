package com.kotmw.kotinvader.gui;

import com.kotmw.kotinvader.PlayStatus;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class GameRemain extends HBox {

    /*                                            1200px
    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
    ┃  4  凸 凸 凸　　　                                                                        　　    ┃ 50px
    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
     */

    Image cannon;

    public GameRemain(PlayStatus player) {
        cannon = new Image(getClass().getResource("/resources/Cannon.png").toExternalForm());
        this.setPrefSize(GameMain.REMAIN_X, GameMain.REMAIN_Y);
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER_LEFT);
        player.remainProperty().addListener(((observable, oldValue, newValue) -> setRemain(newValue.intValue(), oldValue.intValue() < newValue.intValue())));

        setRemain(player.getRemain(), false);
    }

    private void setRemain(int remain, boolean add) {
        this.getChildren().clear();
        this.getChildren().add(new Label(Integer.toString(remain)));
        for (int i = 0; i < remain; i++) {
            ImageView imageView = new ImageView(cannon);
            this.getChildren().add(imageView);
            if (add && i == remain-1) {
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.1), imageView);
                fadeTransition.setFromValue(0.0);
                fadeTransition.setToValue(1.0);
                fadeTransition.setCycleCount(5);
                fadeTransition.play();
            }
        }
    }
}
