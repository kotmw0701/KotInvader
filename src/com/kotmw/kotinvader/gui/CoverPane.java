package com.kotmw.kotinvader.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CoverPane extends VBox {

    private GameMain root;

    public CoverPane(GameMain gameMain) {
        this.root = gameMain;
        this.setPrefSize(GameMain.WINDOW_X, GameMain.WINDOW_Y);
        this.setFillWidth(false);
        this.setAlignment(Pos.CENTER);
    }

    public void showResult() {
        BorderPane container = new BorderPane();
        container.getStyleClass().add("result");
        container.setPrefSize(500, GameMain.WINDOW_Y);
        container.setPadding(new Insets(20));

        Label top = new Label("Game Over");
        BorderPane.setAlignment(top, Pos.CENTER);

        VBox scoreData = new VBox();

        HBox menu = new HBox();
        menu.setAlignment(Pos.CENTER_RIGHT);
        Button title = new Button("Title"), exit = new Button("Exit");
        title.setOnAction(event -> root.close());
        exit.setOnAction(event -> System.exit(0));

        menu.getChildren().addAll(title, exit);

        container.setTop(top);
        container.setCenter(scoreData);
        container.setBottom(menu);

        getChildren().add(container);
    }
}
