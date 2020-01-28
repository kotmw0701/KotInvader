package com.kotmw.kotinvader.gui;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CoverPane extends StackPane {

    private GameMain root;
    private Pane freePane;

    public CoverPane(GameMain gameMain) {
        this.root = gameMain;
        this.setPrefSize(GameMain.WINDOW_X, GameMain.WINDOW_Y);
    }

    public void showResult() {
        BorderPane container = new BorderPane();
        container.getStyleClass().add("result");
        container.setCursor(Cursor.DEFAULT);
        container.setPadding(new Insets(20));
        StackPane.setMargin(container, new Insets(0, GameMain.MAIN_X/4, 0, GameMain.MAIN_X/4));

        Label top = new Label("Game Over");
        BorderPane.setAlignment(top, Pos.CENTER);

        VBox scoreData = new VBox(); //460, 546

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

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), container);
        translateTransition.setFromY(-GameMain.WINDOW_Y);
        translateTransition.setToY(0);
        translateTransition.setInterpolator(Interpolator.SPLINE(0.215, 0.61, 0.355, 1));
        translateTransition.play();

        getChildren().add(container);
    }

    public void nextLevel(int level, EventHandler<ActionEvent> onFinished) {
        Label label = new Label("Level "+level);
        label.getStyleClass().add("levelTitle");
        label.setTranslateY(GameMain.WINDOW_Y);

        HBox hBox = new HBox();
        ParallelTransition transition = new ParallelTransition();
        transition.setOnFinished(event -> this.getChildren().remove(hBox));

        for (int i = 0; i < 12; i++) {
            Rectangle rectangle = new Rectangle(100, 700, level < 5 ? Color.web("#7A7C7D") : Color.FIREBRICK);
            rectangle.setTranslateY(700);

            TranslateTransition beforeTransition = new TranslateTransition(Duration.seconds(0.5), rectangle);
            beforeTransition.setFromY(700.0);
            beforeTransition.setToY(0.0);
            beforeTransition.setInterpolator(Interpolator.SPLINE(0.39, 0.575, 0.565, 1));
            TranslateTransition afterTransition = new TranslateTransition(Duration.seconds(0.5), rectangle);
            afterTransition.setFromY(0.0);
            afterTransition.setToY(-700.0);
            afterTransition.setInterpolator(Interpolator.SPLINE(0.47, 0, 0.745, 0.715));
            transition.getChildren().add(new SequentialTransition(new PauseTransition(Duration.seconds(0.05*i)), beforeTransition, new PauseTransition(Duration.seconds(2.0)), afterTransition));

            hBox.getChildren().add(rectangle);
        }

        this.getChildren().addAll(hBox, label);
        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(0.75), label);
        transition1.setFromY(GameMain.WINDOW_Y);
        transition1.setToY(0.0);
        transition1.setInterpolator(Interpolator.SPLINE(0.215, 0.61, 0.355, 1));
        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(0.75), label);
        transition2.setFromY(0.0);
        transition2.setToY(-GameMain.WINDOW_Y);
        transition2.setInterpolator(Interpolator.SPLINE(0.55, 0.055, 0.675, 0.19));
        transition2.setOnFinished(event -> this.getChildren().remove(label));
        ParallelTransition parallelTransition = new ParallelTransition(transition, new SequentialTransition(new PauseTransition(Duration.seconds(0.25)), transition1, new PauseTransition(Duration.seconds(1.5)), transition2));
        parallelTransition.setOnFinished(onFinished);
        parallelTransition.play();
    }

    public void showScore(double x, double y, int amount) {
        showScore(x, y, amount, null);
    }

    public void showScore(double x, double y, int amount, String text) {
        if (freePane == null) this.getChildren().add(freePane = new Pane());
        VBox box = new VBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.getStyleClass().add("bonus");
        if (text != null && !text.trim().isEmpty()) {
            Label label = new Label(text);
            label.getStyleClass().add("bonusTitle");
            box.getChildren().add(label);
        }
        box.getChildren().add(new Label("+"+ amount));
        this.freePane.getChildren().add(box);
        box.setTranslateX(x);
        box.setTranslateY(y);
        Transition transition = text == null || text.trim().isEmpty() ? _bonusBaseAnimation(x, y, box) : new SequentialTransition(_bonusBaseAnimation(x, y, box), new PauseTransition(Duration.seconds(0.5)));
        transition.setOnFinished(event -> this.freePane.getChildren().remove(box));
        transition.play();
    }

    public void rainbow(double x, double y, int amount) {
        if (freePane == null) this.getChildren().add(freePane = new Pane());
        VBox box = new VBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.getStyleClass().add("bonus");
        Label rainbowLabel = new Label("RAINBOW!");
        rainbowLabel.getStyleClass().add("rainbow");
        box.getChildren().addAll(rainbowLabel, new Label(String.valueOf(amount)));
        this.freePane.getChildren().add(box);
        FadeTransition flash = new FadeTransition(Duration.seconds(0.1), box);
        flash.setFromValue(0.0);
        flash.setToValue(1.0);
        flash.setCycleCount(3);
        Transition transition = new SequentialTransition(_bonusBaseAnimation(x, y, box), new PauseTransition(Duration.seconds(0.2)), flash);
        transition.setOnFinished(event -> this.freePane.getChildren().remove(box));
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
        return new ParallelTransition(translateAnimation, fadeTransition, new PauseTransition(Duration.seconds(1)));
    }
}
