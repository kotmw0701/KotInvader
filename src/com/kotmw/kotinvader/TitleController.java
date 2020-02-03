package com.kotmw.kotinvader;

import com.kotmw.kotinvader.gui.GameMain;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class TitleController implements Initializable {

    @FXML
    private Label title;
    @FXML
    private Button start, settings, exit;
    @FXML
    private Rectangle startBack, settingsBack, exitBack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Button[] buttons = new Button[]{start, settings, exit};//Buttons
        ParallelTransition buttonParallel = new ParallelTransition();
        for (int p = 0; p < 3; p++) {
            Button button = buttons[p];
            button.setOpacity(0.0);
            TranslateTransition translate = new TranslateTransition(Duration.seconds(0.3), button);
            translate.setFromX(-40.0);
            translate.setToX(0.0);
            translate.setInterpolator(Interpolator.EASE_OUT);
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), button);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            buttonParallel.getChildren().addAll(
                    new SequentialTransition(new PauseTransition(Duration.seconds(0.2*p)), new ParallelTransition(fadeIn, translate)));
            TranslateTransition backTranslate = new TranslateTransition(Duration.seconds(0.1));
            backTranslate.setInterpolator(Interpolator.EASE_OUT);
            ScaleTransition scale = new ScaleTransition(Duration.seconds(0.1));
            scale.setInterpolator(Interpolator.EASE_OUT);
            ParallelTransition parallel = new ParallelTransition(backTranslate, scale);
            button.setOnMouseEntered(event -> {
                parallel.stop();
                switch (((Button)event.getSource()).getId()) {
                    case "start":
                        backTranslate.setNode(startBack);
                        break;
                    case "settings":
                        backTranslate.setNode(settingsBack);
                        break;
                    case "exit":
                        backTranslate.setNode(exitBack);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + ((Button) event.getSource()).getId());
                }
                backTranslate.setFromX(-150);
                backTranslate.setToX(0.0);
                scale.setNode(backTranslate.getNode());
                scale.setFromX(0.0);
                scale.setToX(1.0);
                parallel.play();
            });
            button.setOnMouseExited(event -> {
                parallel.stop();
                switch (((Button)event.getSource()).getId()) {
                    case "start":
                        backTranslate.setNode(startBack);
                        break;
                    case "settings":
                        backTranslate.setNode(settingsBack);
                        break;
                    case "exit":
                        backTranslate.setNode(exitBack);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + ((Button) event.getSource()).getId());
                }
                backTranslate.setFromX(0.0);
                backTranslate.setToX(-150);
                scale.setNode(backTranslate.getNode());
                scale.setFromX(1.0);
                scale.setToX(0.0);
                parallel.play();
            });
        }

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.8), title);
        translateTransition.setFromY(130.0);
        translateTransition.setToY(0.0);
        translateTransition.setInterpolator(Interpolator.SPLINE(0.215, 0.61, 0.355, 1)); //easeOutCubic
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), title);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        SequentialTransition sequentialTransition = new SequentialTransition(
                new PauseTransition(Duration.seconds(0.5)),
                fadeTransition,
                translateTransition,
                buttonParallel);
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
