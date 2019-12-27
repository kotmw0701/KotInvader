package com.kotmw.kotinvader.canvasprototype.gui;

import com.kotmw.kotinvader.canvasprototype.entity.Entity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GameContainer extends Canvas {

    private Timeline timeline;

    private List<Entity> entities;

    public GameContainer() {
        super(GameMain.MAIN_X, GameMain.MAIN_Y);
        entities = new ArrayList<>();
        this.setCache(true);
        this.setCacheHint(CacheHint.SPEED);
    }

    public void addEntities(Entity entity) {
        entities.add(entity);
        entity.draw(this.getGraphicsContext2D());

        timerCreate();
        play();
    }

    public List<Entity> getEntities() {
        return entities;
    }



    private void timerCreate() {
        timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0.017),
                        event -> {
                            this.getGraphicsContext2D().clearRect(0, 0, GameMain.MAIN_X, GameMain.MAIN_Y);
                            this.getEntities().forEach(entity -> {
                                entity.move();
                                entity.draw(this.getGraphicsContext2D());
                            });
                        }
                )
        );
        timeline.setCycleCount(6000);
    }

    private void play() {
        timeline.setOnFinished(event -> {
            timerCreate();
            play();
        });
        timeline.play();
    }
}
