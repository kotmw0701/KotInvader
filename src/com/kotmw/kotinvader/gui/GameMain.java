package com.kotmw.kotinvader.gui;

import com.kotmw.kotinvader.managers.KeyManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameMain extends Stage {

    public static final double WINDOW_X = 1200.0;
    public static final double WINDOW_Y = 700.0;
    public static final double STATUS_X = WINDOW_X;
    public static final double STATUS_Y = 100.0;
    public static final double REMAIN_X = WINDOW_X;
    public static final double REMAIN_Y = 50.0;
    public static final double MAIN_X = WINDOW_X;
    public static final double MAIN_Y = 550.0;

    public GameMain() {
        KeyManager keyManager = new KeyManager();
        BorderPane root = new BorderPane();
        root.setPrefSize(WINDOW_X, WINDOW_Y);
        root.setTop(new GameStatus());
        root.setBottom(new GameRemain());
        root.setCenter(new GameContainer());
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        scene.setOnKeyPressed(keyManager);
        scene.setOnKeyReleased(keyManager);

        this.setScene(scene);
        this.initStyle(StageStyle.TRANSPARENT);
        this.setTitle("Invader | Play!");
        this.setResizable(false);
        this.sizeToScene();
        this.show();
    }

}
