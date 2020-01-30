package com.kotmw.kotinvader.gui;

import com.kotmw.kotinvader.gameobjects.entity.Invader;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class FloatsContainer extends Pane {

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
        box.getChildren().add(new Label("+"+ amount));
        this.getChildren().add(box);
        box.setTranslateX(x);
        box.setTranslateY(y);
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
        box.getChildren().addAll(rainbowLabel, new Label("+"+ amount));
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
        translateAnimation.setFromY(y);
        translateAnimation.setToX(x);
        translateAnimation.setToY(y-20);
        translateAnimation.setInterpolator(Interpolator.EASE_OUT);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), box);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        return new ParallelTransition(translateAnimation, fadeTransition, new PauseTransition(Duration.seconds(1)));
    }

    public void invaderDebug(Invader invader) {
        Rectangle rectangle = new Rectangle(invader.getWidth(), invader.getHeight(), Color.TRANSPARENT);
        if (invader.isActive()) rectangle.setStroke(Color.RED);
        else rectangle.setStroke(Color.GREEN);
        rectangle.translateXProperty().bind(invader.translateXProperty());
        rectangle.translateYProperty().bind(invader.translateYProperty());
        invader.activeProperty().addListener((a, b, newValue) -> { if (newValue) rectangle.setStroke(Color.RED);});
        rectangle.opacityProperty().bind(invader.opacityProperty());
        this.getChildren().add(rectangle);
    }
}
