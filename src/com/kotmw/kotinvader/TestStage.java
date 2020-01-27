package com.kotmw.kotinvader;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class TestStage extends Stage {

    //Destroyed

    public TestStage() {

        //ベース(透明)
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);
        root.setPrefWidth(1200);
        root.setPrefHeight(700);
        root.setStyle("-fx-background-color: transparent");

        //アニメーションするNode
        HBox hbox = new HBox();
        hbox.setPrefWidth(1200);
        hbox.setPrefHeight(700);
        hbox.setStyle("-fx-background-color: transparent");

        ParallelTransition transition = new ParallelTransition();

        for (int i = 0; i < 12; i++) {
            Rectangle rectangle = new Rectangle(100, 700, Color.GRAY);
            rectangle.setTranslateY(700);

            TranslateTransition beforeTransition = new TranslateTransition(Duration.seconds(0.5), rectangle);
            beforeTransition.setFromY(700.0);
            beforeTransition.setToY(0.0);
            beforeTransition.setInterpolator(Interpolator.SPLINE(0.39, 0.575, 0.565, 1));
            TranslateTransition afterTransition = new TranslateTransition(Duration.seconds(0.5), rectangle);
            afterTransition.setFromY(0.0);
            afterTransition.setToY(-700.0);
            afterTransition.setInterpolator(Interpolator.SPLINE(0.47, 0, 0.745, 0.715));
            transition.getChildren().add(new SequentialTransition(new PauseTransition(Duration.seconds(0.05*i)), beforeTransition, new PauseTransition(Duration.seconds(3)), afterTransition));

            hbox.getChildren().add(rectangle);
        }

        root.getChildren().addAll(hbox);
        Scene scene = new Scene(root);
        scene.setFill(null);

        transition.play();
        transition.setOnFinished(event -> this.close());

        this.setScene(scene);
        this.initStyle(StageStyle.TRANSPARENT);
        this.setResizable(false);
        this.sizeToScene();
        this.show();
    }
}
