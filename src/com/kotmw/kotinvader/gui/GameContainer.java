package com.kotmw.kotinvader.gui;

import com.kotmw.kotinvader.PlayStatus;
import com.kotmw.kotinvader.entity.Cannon;
import com.kotmw.kotinvader.entity.Enemy;
import com.kotmw.kotinvader.entity.Entity;
import com.kotmw.kotinvader.entity.Invader;
import com.kotmw.kotinvader.entity.missiles.InvaderMissile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.List;
import java.util.stream.Collectors;

public class GameContainer extends Pane {

    /*                                            1200px
    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
    ┃                                                                                               ┃
    ┃                                                                                               ┃
    ┃                                 ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊                             　　┃
    ┃                                 ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊                             　　┃
    ┃                                 ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊                             　　┃
    ┃                                 ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊                             　　┃
    ┃                                 ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊                             　　┃
    ┃                                                                                               ┃ 550px
    ┃                                    ｜                                                    　　　　┃
    ┃                                                                                               ┃
    ┃　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　      ｜                            　　　　　┃
    ┃                                               ｜                                         　　　　┃
    ┃                                                                                               ┃
    ┃                                                                                               ┃
    ┃                                               凸                                   　　　　      ┃
    ┃                                                                                               ┃
    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
     */

    private PlayStatus player;
    private CoverPane cover;

    private Timeline timeline;

    private boolean invaderRight, down;
    private int frameCounter, negateCount;

    //――――――――――――――――――――――――――――――――――
//    private Line leftLine, rightLine;
    //――――――――――――――――――――――――――――――――――

    public GameContainer(PlayStatus player, CoverPane cover) {
        this.player = player;
        this.cover = cover;
        this.setPrefSize(GameMain.MAIN_X, GameMain.MAIN_Y);

        this.getChildren().add(player.getCannon());

        createInvaders();

        //―――――――――――――――――――――――――――――――――――――――――――――
//        leftLine = new Line(0, 0, 0, 600);
//        leftLine.setStroke(Color.GREEN);
//
//        rightLine = new Line(0, 0, 0, 600);
//        rightLine.setStroke(Color.PINK);
//
//        this.getChildren().addAll(leftLine, rightLine);
        //―――――――――――――――――――――――――――――――――――――――――――――

        timerCreate();
    }

    public boolean isObjectInWindow(Entity entity) {
        return entity.getTranslateX() > 0
                && entity.getTranslateY() > 0
                && entity.getTranslateX() < GameMain.MAIN_X
                && entity.getTranslateY() < GameMain.MAIN_Y;
    }

    public List<Entity> getEntities() {
        return getChildren().stream().filter(entity -> entity instanceof Entity).map(n -> (Entity)n).collect(Collectors.toList());
    }

    private void timerCreate() {
        timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0.01),
                        event -> {
                            frameCounter++;
                            getEntities().forEach(entity -> {
                                switch (entity.getEntityType()) {
                                    case CANNON:
                                        entity.move();
                                        break;
                                    case INVADER:
                                        if (frameCounter % 30 == 0) {
                                            Invader invader = (Invader) entity;
                                            if (invader.isActive() && Math.random() > 0.95) getChildren().add(invader.shoot());
                                            if (down) {
                                                entity.setSpeed(25.0);
                                                entity.setDirection(90);
                                            } else {
                                                entity.setSpeed(5.0);
                                                if (invaderRight) entity.setDirection(0.0);
                                                else entity.setDirection(180.0);
                                            }
                                            entity.move();
                                        }
                                        break;
                                    case INVADERMISSILE:
                                        entity.move();
                                        if (!isObjectInWindow(entity)) entity.hit(100);
                                        getEntities().stream()
                                                .filter(e -> e instanceof Cannon /*|| e instanceof Tochica */)
                                                .forEach(others -> {
                                                    if (!others.isInvincible() && entity.getBoundsInParent().intersects(others.getBoundsInParent())) {
                                                        entity.hit(10);
                                                        others.hit(10);
                                                        if (!player.getCannon().isAlive() && player.getRemain() > 0) {
                                                            getChildren().add(player.respawn());
                                                            player.decreaseRemain();
                                                            negateCount = 0;
                                                        } else {
                                                            //ゲームオーバー
                                                        }
                                                    }
                                                });
                                        break;
                                    case CANNONMISSILE:
                                        entity.move();
                                        if (!isObjectInWindow(entity)) entity.hit(100);
                                        getEntities().stream()
                                                .filter(e -> e instanceof Enemy || e instanceof InvaderMissile)
                                                .forEach(others -> {
                                                    if (entity.getBoundsInParent().intersects(others.getBoundsInParent())) {
                                                        entity.hit(10);
                                                        others.hit(10);
                                                        if (others instanceof InvaderMissile) {
                                                            if (10 == ++negateCount) {
                                                                player.increaseRemain();
                                                                negateCount = 0;
                                                            }
                                                        } else player.addScore(50);
                                                    }
                                                });
                                        break;
                                }
                            });
                            if (frameCounter % 30 == 0) {
                                double rightMost = 250, leftMost = 950 + 22;
                                for (Entity entity : getEntities()) {
                                    if (entity instanceof Invader && entity.isAlive()) {
                                        double invaderX = entity.getTranslateX();
                                        rightMost = Math.max(rightMost, invaderX);
                                        leftMost = Math.min(leftMost, invaderX);
                                    }
                                }
                                //――――――――――――――――――――――――――――――――――
//                                leftLine.setTranslateX(leftMost);
//                                rightLine.setTranslateX(rightMost);
                                //――――――――――――――――――――――――――――――――――

                                if (!down) {
                                    if (down = (250 > leftMost || 950 < rightMost)) invaderRight = !invaderRight;
                                } else down = false;
                            }

                            getChildren().removeIf(e -> e instanceof Entity && !((Entity)e).isAlive());
                        }
                )
        );
        timeline.setCycleCount(6000);
        timeline.setOnFinished(event -> {
            timerCreate();
            play();
        });
    }

    public void play() {
        timeline.play();
    }

    private void createInvaders() {
        for (int x = 0; x < 11; x++) {
            Invader aboveInvader = null;
            for (int y = 0; y < 5; y++) {
                Invader invader;
                double yPoint = 50 + y * 25, xPoint = GameMain.MAIN_X/2-150 + x*30;
                if (y == 0) {
                    invader = new Invader(xPoint, yPoint);
                } else if (y == 4) {
                    invader = new Invader(xPoint, yPoint, aboveInvader, true);
                } else {
                    invader = new Invader(xPoint, yPoint, aboveInvader);
                }
                aboveInvader = invader;
                getChildren().add(invader);
            }
        }
    }
}
