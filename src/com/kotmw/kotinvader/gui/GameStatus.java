package com.kotmw.kotinvader.gui;

import com.kotmw.kotinvader.PlayStatus;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class GameStatus extends BorderPane {

    /*                                            1200px
    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
    ┃       SCORE                                HI-SCORE                                           ┃ 100px
    ┃       00000                                 00000                                             ┃
    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
     */
    private Label score;

    public GameStatus(PlayStatus player) {
        this.setPrefSize(GameMain.STATUS_X, GameMain.STATUS_Y);
        score = new Label("0000000");
        score.textProperty().bind(player.scoreProperty());
        VBox vBox = new VBox(new Label("SCORE"), score), vBox2 = new VBox(new Label("HI-SCORE"), new Label("0000000 ")), vBox3 = new VBox();
        vBox.setPrefWidth(200);
        vBox.setAlignment(Pos.CENTER);
        vBox2.setAlignment(Pos.CENTER);
        vBox3.setPrefWidth(200);
        this.setLeft(vBox);
        this.setCenter(vBox2);
        this.setRight(vBox3);
    }

    public void setScore(int amount) {
        score.setText(String.format("%07d", amount));
    }
}
