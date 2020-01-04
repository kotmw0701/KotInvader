package com.kotmw.kotinvader.gui;

import com.kotmw.kotinvader.PlayStatus;
import com.kotmw.kotinvader.entity.*;
import com.kotmw.kotinvader.entity.missiles.InvaderMissile;
import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
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

    private boolean invaderRight, down, rainbow;
    private int frameCounter, negateCount, invaderSpeed, limitCount;

    //――――――――――――――――――――――――――――――――――
//    private Line leftLine, rightLine;
    //――――――――――――――――――――――――――――――――――

    public GameContainer(PlayStatus player, CoverPane cover) {
        this.player = player;
        this.cover = cover;
        this.invaderSpeed = 50;
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

        Timeline ufoTimeline = new Timeline(
                new KeyFrame(
                        new Duration(25000),
                        event -> {
                            if (getInvaderCount() < 8) return;
                            getChildren().add(new UFO());
                        }
                )
        );
        ufoTimeline.setCycleCount(Timeline.INDEFINITE);
        ufoTimeline.play();
    }

    public boolean isObjectInWindow(Entity entity, double margin) {
        return entity.getTranslateX() > -margin
                && entity.getTranslateY() > -margin
                && entity.getTranslateX() < GameMain.MAIN_X+margin
                && entity.getTranslateY() < GameMain.MAIN_Y+margin;
    }

    private List<Entity> getEntities() {
        return getChildren().stream().filter(entity -> entity instanceof Entity).map(n -> (Entity)n).collect(Collectors.toList());
    }

    private int getInvaderCount() {
        return (int) getChildren().stream().filter(entity -> entity instanceof Invader).count();
    }

    private void timerCreate() {
        timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0.01),
                        event -> {
                            frameCounter++;
                            getEntities().forEach(entity -> {
                                switch (entity.getEntityType()) {
                                    case UFO:
                                        if (!isObjectInWindow(entity, 20)) entity.hit(100);
                                    case CANNON:
                                        entity.move();
                                        break;
                                    case INVADER:
                                        if (frameCounter % invaderSpeed == 0) {
                                            Invader invader = (Invader) entity;
                                            if (invader.isActive() && Math.random() > 0.95) getChildren().add(invader.shoot());
                                            if (down) {
                                                entity.setSpeed(16.0);
                                                entity.setDirection(Entity.Direction.DOWN);
                                            } else {
                                                entity.setSpeed(5.0);
                                                if (invaderRight) entity.setDirection(Entity.Direction.RIGHT);
                                                else entity.setDirection(Entity.Direction.LEFT);
                                            }
                                            entity.move();
                                        }
                                        break;
                                    case INVADERMISSILE:
                                        entity.move();
                                        if (!isObjectInWindow(entity, 0)) entity.hit(100);
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
                                                        } else gameOver();
                                                    }
                                                });
                                        break;
                                    case CANNONMISSILE:
                                        entity.move();
                                        if (!isObjectInWindow(entity, 20)) entity.hit(100);
                                        getEntities().stream()
                                                .filter(e -> e instanceof Enemy || e instanceof InvaderMissile)
                                                .forEach(others -> {
                                                    if (entity.getBoundsInParent().intersects(others.getBoundsInParent())) {
                                                        entity.hit(10);
                                                        others.hit(10);
                                                        switch (others.getEntityType()) {
                                                            case INVADER:
                                                                if (rainbow) addScore(others, 500, true);
                                                                else addScore(others, 0, false);
                                                                break;
                                                            case UFO:
                                                                addScore(others, 0, true);
                                                                break;
                                                            case INVADERMISSILE:
                                                                if (10 == ++negateCount) {
                                                                    player.increaseRemain();
                                                                    negateCount = 0;
                                                                }
                                                                break;
                                                            default:
                                                                addScore(others, 0, false);
                                                                break;
                                                        }
                                                    }
                                                });
                                        break;
                                }
                            });

                            getChildren().removeIf(e -> e instanceof Entity && !((Entity)e).isAlive());

                            if (frameCounter % invaderSpeed == 0) {

                                double rightMost = 250, leftMost = 950 + 22, bottomMost = 0;
                                Invader lastInvader = null;
                                for (Entity entity : getEntities()) {
                                    if (entity instanceof Invader && entity.isAlive()) {
                                        lastInvader = (Invader) entity;
                                        double invaderX = entity.getTranslateX();
                                        rightMost = Math.max(rightMost, invaderX);
                                        leftMost = Math.min(leftMost, invaderX);
                                        if (down) bottomMost = Math.max(bottomMost, entity.getTranslateY());
                                    }
                                }

                                if (limitCount == 2 && bottomMost > GameMain.MAIN_Y) gameOver();

                                //――――――――――――――――――――――――――――――――――
//                                leftLine.setTranslateX(leftMost);
//                                rightLine.setTranslateX(rightMost);
                                //――――――――――――――――――――――――――――――――――

                                if (getInvaderCount() <= 1 && lastInvader.getInvaderType() == 1) rainbow = true;

                                if (!down) {
                                    if (down = (250 > leftMost || 950 < rightMost)) {
                                        if (bottomMost >= 476) {
                                            gameOver();
                                            return;
                                        }
                                        if (invaderRight && rainbow) limitCount++;
                                        invaderRight = !invaderRight;
                                    }
                                } else if (limitCount < 2) down = false;
                            }

                            int invaderCount = getInvaderCount();
                            if (invaderCount > 0) invaderSpeed = invaderCount > 10 ? invaderCount - 5 : invaderCount;
                            else clear();
                        }
                )
        );
        timeline.setCycleCount(6000);
        timeline.setOnFinished(event -> {
            timerCreate();
            play();
        });
    }

    public void addScore(Entity killed, int amount, boolean bonus) {
        if (killed instanceof Enemy)
            amount = amount == 0 ? ((Enemy)killed).getScore() : amount;
        else if (amount == 0) return;
        player.addScore(amount);
        if (bonus) cover.showScore(killed.getTranslateX(), killed.getTranslateY(), amount, (killed instanceof Invader));
    }

    public void play() {
        timeline.play();
    }

    public void clear() {
        player.addScore(1000);
        timeline.stop();
    }

    private void gameOver() {
        timeline.stop();
        cover.showResult();
    }

    private void createInvaders() {
        for (int x = 0; x < 11; x++) {
            Invader aboveInvader = null;
            for (int y = 0; y < 5; y++) {
                Invader invader;
                double yPoint = 60 + y * 32, xPoint = GameMain.MAIN_X/2-150 + x*30;
                if (y == 0) {
                    invader = new Invader(xPoint, yPoint, 3);
                } else if (y == 4) {
                    invader = new Invader(xPoint-4, yPoint, aboveInvader, true, 1);
                } else if (y == 3) {
                    invader = new Invader(xPoint-4, yPoint, aboveInvader, 1);
                } else {
                    invader = new Invader(xPoint-2, yPoint, aboveInvader, 2);
                }
                aboveInvader = invader;
                getChildren().add(invader);
            }
        }
    }
}
