package com.kotmw.kotinvader.gui;

import com.kotmw.kotinvader.PlayStatus;
import com.kotmw.kotinvader.entity.Cannon;
import com.kotmw.kotinvader.entity.Enemy;
import com.kotmw.kotinvader.entity.Entity;
import com.kotmw.kotinvader.entity.missiles.InvaderMissile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.List;
import java.util.stream.Collectors;

public class GameContainer extends Pane {

    private Timeline timeline;

    public GameContainer(PlayStatus player) {
        this.setPrefSize(GameMain.MAIN_X, GameMain.MAIN_Y);
        this.setBackground(new Background(new BackgroundFill(Color.web("#080808"), CornerRadii.EMPTY, Insets.EMPTY)));

        this.getChildren().add(player.getCannon());

        timerCreate();
        play();
    }

    public boolean isObjectInWindow(Entity entity) {
        return entity.getTranslateX() > 0
                && entity.getTranslateY() > 0
                && entity.getTranslateX() < GameMain.MAIN_X
                && entity.getTranslateY() < GameMain.MAIN_Y;
    }

    public List<Entity> getEntities() {
        return getChildren().stream().map(n -> (Entity)n).collect(Collectors.toList());
    }

    private void timerCreate() {
        timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0.017),
                        event -> {
                            getEntities().forEach(entity -> {
                                entity.move();
                                switch (entity.getEntityType()) {
                                    case INVADERMISSILE:
                                        if (!isObjectInWindow(entity)) {
                                            entity.hit(100);
                                        }
                                        getEntities().stream()
                                                .filter(e -> e instanceof Cannon /*|| e instanceof Tochica */)
                                                .forEach(others -> {
                                                    if (entity.getBoundsInParent().intersects(others.getBoundsInParent())) {
                                                        entity.hit(10);
                                                        others.hit(10);
                                                    }
                                                });
                                        break;
                                    case CANNONMISSILE:
                                        if (!isObjectInWindow(entity)) {
                                            entity.hit(100);
                                        }
                                        getEntities().stream()
                                                .filter(e -> e instanceof Enemy || e instanceof InvaderMissile)
                                                .forEach(others -> {
                                                    if (entity.getBoundsInParent().intersects(others.getBoundsInParent())) {
                                                        entity.hit(10);
                                                        others.hit(10);
                                                    }
                                                });
                                        break;
                                }
                            });

                            getChildren().removeIf(e -> !((Entity)e).isAlive());
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
