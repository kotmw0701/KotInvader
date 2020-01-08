package com.kotmw.kotinvader;

import com.kotmw.kotinvader.gui.GameMain;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class TitleController implements Initializable {

    public Label title;
    public Pane start;
    public Pane settings;
    public Pane exit;
//    public VBox animContainer;

    /*

    Title:
        Pause → Fade → Translate → Translate → Fade

    Button:
        Translate → |
        Fade      → |
                     Translate → |
                     Fade      → |
                                  Translate → |
                                  Fade      → |
     */


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Pane[] panes = new Pane[]{start, settings, exit};
        Transition[] buttonAnimations = new Transition[3];
        for (int p = 0; p < 3; p++) {
            Pane pane = panes[p];
            Transition[] transitions = new Transition[2];
            for (int i = 0; i < 2; i++) {
                Region region = new Region();
                region.setOnMouseClicked(event -> region.getStyleClass().forEach(System.out::println));
                TranslateTransition slide = new TranslateTransition(Duration.seconds(0.5), region);
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), region);
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0.0);
                if (i == 0) {
                    region.getStyleClass().add("left");
                    slide.setFromX(0.0);
                    slide.setToX(-100.0);
                } else {
                    region.getStyleClass().add("right");
                    slide.setFromX(100.0);
                    slide.setToX(200.0);
                }
                pane.getChildren().add(region);
                transitions[i] = new ParallelTransition(slide, fadeTransition); //片扉
            }
            buttonAnimations[p] = new ParallelTransition(transitions); //両開きのアニメーション
        }

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.8), title);
        translateTransition.setFromY(130.0);
        translateTransition.setToY(0.0);
        translateTransition.setInterpolator(Interpolator.SPLINE(0.215, 0.61, 0.355, 1));
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), title);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        SequentialTransition sequentialTransition = new SequentialTransition(
                new PauseTransition(Duration.seconds(0.5)),
                fadeTransition,
                translateTransition,
                new ParallelTransition(buttonAnimations));
        sequentialTransition.play();
    }

    @FXML
    private void onStart(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        GameMain gameMain = new GameMain(stage);
        stage.hide();
    }

    public void onSettings(ActionEvent actionEvent) {
    }

    public void onExit(ActionEvent actionEvent) {
        System.exit(0);
    }

}
