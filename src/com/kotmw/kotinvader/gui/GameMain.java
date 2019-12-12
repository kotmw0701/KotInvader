package com.kotmw.kotinvader.gui;

import com.kotmw.kotinvader.managers.KeyManager;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
        root.setCenter(new GameContainer());
        root.setTop(new GameStatus());
        root.setBottom(new GameRemain());
        root.setStyle("-fx-backgroud-color: #000");
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
