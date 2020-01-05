package com.kotmw.kotinvader.gui;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class CoverPane extends Pane {

    private GameMain root;

    public CoverPane(GameMain gameMain) {
        this.root = gameMain;
        this.setPrefSize(GameMain.WINDOW_X, GameMain.WINDOW_Y);
    }

    public void showResult() {
        BorderPane container = new BorderPane();
        container.getStyleClass().add("result");
        container.setCursor(Cursor.DEFAULT);
        container.setPrefSize(500, GameMain.WINDOW_Y);
        container.setPadding(new Insets(20));
        container.setTranslateX((GameMain.WINDOW_X-container.getPrefWidth())/2);

        Label top = new Label("Game Over");
        BorderPane.setAlignment(top, Pos.CENTER);

        VBox scoreData = new VBox();

        HBox menu = new HBox();
        menu.setAlignment(Pos.CENTER_RIGHT);
        menu.setSpacing(20);
        Button title = new Button("Title"), exit = new Button("Exit");
        title.setOnAction(event -> root.close());
        exit.setOnAction(event -> System.exit(0));

        menu.getChildren().addAll(title, exit);

        container.setTop(top);
        container.setCenter(scoreData);
        container.setBottom(menu);

        getChildren().add(container);
    }

    public void showScore(double x, double y, int amount) {
        showScore(x, y, amount, null);
    }

    public void showScore(double x, double y, int amount, String text) {
        VBox box = new VBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.getStyleClass().add("bonus");
        if (text != null && !text.trim().isEmpty()) {
            Label label = new Label(text);
            label.getStyleClass().add("bonusTitle");
            box.getChildren().add(label);
        }
        box.getChildren().add(new Label(String.valueOf(amount)));
        this.getChildren().add(box);
        Transition transition = text == null || text.trim().isEmpty() ? _bonusBaseAnimation(x, y, box) : new SequentialTransition(_bonusBaseAnimation(x, y, box), new PauseTransition(Duration.seconds(0.5)));
        transition.setOnFinished(event -> this.getChildren().remove(box));
        transition.play();
    }

    public void rainbow(double x, double y, int amount) {
        VBox box = new VBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.getStyleClass().add("bonus");
        Label rainbowLabel = new Label("RAINBOW!");
        rainbowLabel.getStyleClass().add("rainbow");
        box.getChildren().addAll(rainbowLabel, new Label(String.valueOf(amount)));
        this.getChildren().add(box);
        FadeTransition flash = new FadeTransition(Duration.seconds(0.1), box);
        flash.setFromValue(0.0);
        flash.setToValue(1.0);
        flash.setCycleCount(3);
        Transition transition = new SequentialTransition(_bonusBaseAnimation(x, y, box), new PauseTransition(Duration.seconds(0.2)), flash);
        transition.setOnFinished(event -> this.getChildren().remove(box));
        transition.play();
    }

    private ParallelTransition _bonusBaseAnimation(double x, double y, VBox box) {
        TranslateTransition translateAnimation = new TranslateTransition(Duration.seconds(1), box);
        translateAnimation.setFromX(x);
        translateAnimation.setFromY(y+100);
        translateAnimation.setToX(x);
        translateAnimation.setToY(y+100-20);
        translateAnimation.setInterpolator(Interpolator.EASE_OUT);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), box);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        return new ParallelTransition(translateAnimation, fadeTransition);
    }
}
