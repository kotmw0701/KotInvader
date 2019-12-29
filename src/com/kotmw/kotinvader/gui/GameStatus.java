package com.kotmw.kotinvader.gui;

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
              |150px                                |600px
     */

    private Label score;

    public GameStatus() {
        this.setPrefSize(GameMain.STATUS_X, GameMain.STATUS_Y);
        score = new Label("00000");
        VBox vBox = new VBox(new Label("SCORE"), score), vBox2 = new VBox(new Label("HI-SCORE"), new Label("00000 ")), vBox3 = new VBox();
        vBox.setPrefWidth(150);
        vBox.setAlignment(Pos.CENTER);
        vBox2.setAlignment(Pos.CENTER);
        vBox3.setPrefWidth(150);
        this.setLeft(vBox);
        this.setCenter(vBox2);
        this.setRight(vBox3);
    }

    public void setScore(int amount) {
        score.setText(String.format("%05d", amount));
    }
}
