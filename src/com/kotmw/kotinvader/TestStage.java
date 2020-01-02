package com.kotmw.kotinvader;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class TestStage extends Stage {

    //Destroyed

    public TestStage() {
        //ベース(透明)
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPrefWidth(1200);
        root.setPrefHeight(700);
        root.setFillWidth(false);
        root.getStyleClass().add("testClass");

        //アニメーションするNode
        Pane pane = new Pane();
        pane.setPrefWidth(1200);
        pane.setPrefHeight(700);
        pane.getStyleClass().add("testClass2");

        Circle circle = new Circle(100, 100, 50, Color.BLACK);

        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(10), circle);
        rotateTransition.setAxis(new Point3D(0, 1, 0));
        rotateTransition.setFromAngle(0.0);
        rotateTransition.setToAngle(360.0*10);
        rotateTransition.setCycleCount(1);
        rotateTransition.setInterpolator(Interpolator.LINEAR);

        //拡大のアニメーション、X広がりとY広がりを順番にやりたいため2つに分割
        ScaleTransition animationX = new ScaleTransition(Duration.seconds(0.25), pane), animationY = new ScaleTransition(Duration.seconds(0.25), pane);
        animationX.setFromX(0.001);
        animationX.setToX(1.0);
        animationY.setFromY(0.005);
        animationY.setToY(1.0);
        animationY.setInterpolator(Interpolator.EASE_OUT);

        SequentialTransition animation = new SequentialTransition(animationX, animationY, rotateTransition);

        pane.getChildren().add(circle);
        root.getChildren().addAll(pane);
        Scene scene = new Scene(root);
        Application.setUserAgentStylesheet("MODENA");
        scene.getStylesheets().addAll(getClass().getResource("/resources/main.css").toExternalForm());
        scene.setFill(null);

        animation.play();

        this.setScene(scene);
        this.initStyle(StageStyle.TRANSPARENT);
        this.setTitle("Invader | Play!");
        this.setResizable(false);
        this.sizeToScene();
        this.show();
    }
}
