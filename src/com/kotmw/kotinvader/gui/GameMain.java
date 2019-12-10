package com.kotmw.kotinvader.gui;

import com.kotmw.kotinvader.managers.KeyManager;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameMain extends Stage {

    public GameMain() {

        KeyManager keyManager = new KeyManager();
        Scene scene = new Scene(new GameContainer(), 1200, 600);
        scene.setOnKeyPressed(keyManager);
        scene.setOnKeyReleased(keyManager);

        this.setScene(scene);
        this.setTitle("Invader | Play!");
        this.setResizable(false);
        this.sizeToScene();
        this.show();
    }

}
