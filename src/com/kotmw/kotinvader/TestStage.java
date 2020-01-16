package com.kotmw.kotinvader;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class TestStage extends Stage {

    //Destroyed

    public TestStage() {
        Screen screen = Screen.getPrimary();

        //ベース(透明)
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPrefWidth(screen.getBounds().getWidth());
        root.setPrefHeight(screen.getBounds().getHeight());
        root.setFillWidth(false);
        root.getStyleClass().add("testClass");

        //アニメーションするNode
        TilePane tilePane = new TilePane();
        tilePane.setPrefWidth(1200);
        tilePane.setPrefHeight(700);
        tilePane.getStyleClass().add("testClass");

        List<Transition> transitions = new ArrayList<>();

        for (int i = 0; i < 12*7; i++) {
            Pane pane = new Pane();
            pane.setPrefSize(100, 100);
            pane.setStyle("-fx-background-color: gray");
            pane.setOpacity(0.0);

            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.1), pane);
            rotateTransition.setAxis(new Point3D(1, 1, 0));
            rotateTransition.setFromAngle(90.0);
            rotateTransition.setToAngle(0.0);
            rotateTransition.setInterpolator(Interpolator.SPLINE(0.55, 0.055, 0.675, 0.19));

            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.1), pane);
            fadeTransition.setFromValue(0.3);
            fadeTransition.setToValue(1.0);

            transitions.add(new ParallelTransition(rotateTransition, fadeTransition));
            tilePane.getChildren().add(pane);
        }

        SequentialTransition sequentialTransition = new SequentialTransition(transitions.toArray(new Transition[0]));

        root.getChildren().addAll(tilePane);
        Scene scene = new Scene(root);
        Application.setUserAgentStylesheet("MODENA");
        scene.getStylesheets().addAll(getClass().getResource("/resources/main.css").toExternalForm());
        scene.setFill(null);

        sequentialTransition.play();

        this.setScene(scene);
        this.initStyle(StageStyle.TRANSPARENT);
        this.setTitle("Invader | Play!");
        this.setResizable(false);
        this.sizeToScene();
        this.show();
    }
}
