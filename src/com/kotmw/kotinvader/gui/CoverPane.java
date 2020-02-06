package com.kotmw.kotinvader.gui;

import com.kotmw.kotinvader.PlayStatus;
import com.kotmw.kotinvader.gameobjects.entity.Entity;
import com.kotmw.kotinvader.gameobjects.entity.EntityType;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CoverPane extends StackPane {

    private GameMain root;
    private Pane freePane;

    public CoverPane(GameMain gameMain, Node... children) {
        super(children);
        this.root = gameMain;
        this.setPrefSize(GameMain.WINDOW_X, GameMain.WINDOW_Y);
        this.getChildren().add(freePane = new Pane());
    }

    public void Operation() {

    }

    public void showResult(PlayStatus playStatus) {
        BorderPane container = new BorderPane();
        container.getStyleClass().add("result");
        container.setCursor(Cursor.DEFAULT);
        container.setPadding(new Insets(20));
        StackPane.setMargin(container, new Insets(0, GameMain.MAIN_X/4, 0, GameMain.MAIN_X/4));

        Label top = new Label("Game Over");
        BorderPane.setAlignment(top, Pos.CENTER);

        VBox scoreData = new VBox();
        scoreData.setAlignment(Pos.CENTER);
        scoreData.setSpacing(30);
        scoreData.getStyleClass().add("score");
        for (int type = 1; type <= 3; type++) {
            Image image = new Image(
                    getClass().getResource(String.format(EntityType.INVADER.getPath(), type)).toExternalForm(),
                    128,
                    32,
                    true,
                    false
            );
            ImageView imageView = new ImageView(image);
            long data = playStatus.getKillInvaders(type);
            imageView.setViewport(new Rectangle2D(0, 0, imageView.getImage().getWidth()/2, 32));
            Label label = new Label(String.format("x %-3d = %d ", data, type * 10 * data));
            VBox vBox = new VBox(imageView);
            vBox.setPrefWidth(64.0);
            vBox.setAlignment(Pos.CENTER);
            HBox hBox = new HBox(vBox, label);
            hBox.setPadding(new Insets(0, 0, 0, 100));
            hBox.setSpacing(50);
            hBox.setAlignment(Pos.CENTER_LEFT);
            scoreData.getChildren().add(hBox);
        }

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
            Rectangle rectangle = new Rectangle(100, 700, level < 6 ? Color.web("#7A7C7D") : Color.FIREBRICK);
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

}
