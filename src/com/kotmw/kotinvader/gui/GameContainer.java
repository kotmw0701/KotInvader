package com.kotmw.kotinvader.gui;

import com.kotmw.kotinvader.PlayStatus;
import com.kotmw.kotinvader.gameobjects.block.BlockSet;
import com.kotmw.kotinvader.gameobjects.block.Floor;
import com.kotmw.kotinvader.gameobjects.block.Tochica;
import com.kotmw.kotinvader.gameobjects.entity.*;
import com.kotmw.kotinvader.gameobjects.entity.missiles.InvaderMissile;
import com.kotmw.kotinvader.gameobjects.entity.missiles.Missile;
import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
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
    private FloatsContainer floats;
    private Floor floor;
    private int level;
//    private boolean pause;

    private List<BlockSet> tochicaList;
    private boolean rainbow, zeroDeath, fullChain;

    private Timeline timeline;

    private boolean invaderRight, down;
    private int frameCounter, negateCount, invaderSpeed, limitCount, stockLine;
    private Invader[] abobes;

    //――――――――――――――――――――――――――――――――――
//    private Line leftLine, rightLine;
    //――――――――――――――――――――――――――――――――――

    public GameContainer(PlayStatus player, CoverPane cover, FloatsContainer floats) {
        this.player = player;
        this.floats = floats;
        this.cover = cover;
        this.setPrefSize(GameMain.MAIN_X, GameMain.MAIN_Y);

//        this.pause = false;

        initGame(level = 0);
        //―――――――――――――――――――――――――――――――――――――――――――――
//        leftLine = new Line(0, 0, 0, 600);
//        leftLine.setStroke(Color.GREEN);
//
//        rightLine = new Line(0, 0, 0, 600);
//        rightLine.setStroke(Color.PINK);
//
//        this.getChildren().addAll(leftLine, rightLine);
        //―――――――――――――――――――――――――――――――――――――――――――――

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

    /*
    Level 0 ~ 4   : インベーダーが下りてくるのみ
    Level 5 ~ 9  : フィールド縮小 + インベーダーの横移動範囲縮小
    Level 10 ~ 14 : Tochica + Floorの耐久減少
    Level 15 ~    : 追加インベーダー
     */
    private void initGame(int level) {
        if (timeline != null) timeline.stop();
        this.getChildren().stream()
                .filter(node -> node instanceof Missile || node instanceof Enemy)
                .forEach(entity -> ((Entity)entity).hit(1000));
        this.getChildren().clear();

        this.getChildren().add(player.getCannon());

        this.abobes = createInvaders(level);
        this.tochicaList = createTochica(level);
        this.tochicaList.add(this.floor = createFloor(level));
        this.down = false;
        this.rainbow = false;
        this.fullChain = true;
        this.zeroDeath = true;
        this.invaderSpeed = 50;
        this.limitCount = 0;
        this.negateCount = 0;

        if (level >= 15) stockLine = level - 14;

        timerCreate();
    }

    private boolean isObjectInWindow(Entity entity, double margin) {
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
        this.timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0.01),
                        event -> {
                            if (invaderSpeed < 1) invaderSpeed = 1;
                            frameCounter++;
                            getEntities().forEach(entity -> {
                                switch (entity.getEntityType()) {
                                    case UFO:
                                        if (!isObjectInWindow(entity, 20)) entity.hit(100);
                                        entity.move();
                                        break;
                                    case CANNON:
                                        if (floor.hasFloor(entity) || entity.isInvincible())
                                            entity.move();
                                        break;
                                    case INVADER:
                                        if (frameCounter % 25 == 0) {
                                            Invader invader = (Invader) entity;
                                            if (invader.isActive() && Math.random() > 0.98) {
                                                Missile missile = invader.shoot();
                                                if (missile != null) getChildren().add(missile);
                                            }
                                        }
                                        if (frameCounter % invaderSpeed == 0) {
                                            if (down) {
                                                entity.setSpeed(32.0);
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
                                        tochicaList.forEach(tochica -> {
                                            if (entity.getBoundsInParent().intersects(tochica.getBoundingBox()) && tochica.hit(entity, 10))
                                                entity.hit(10);
                                        });
                                        Cannon cannon = player.getCannon();
                                        if (!cannon.isInvincible() && entity.getBoundsInParent().intersects(cannon.getBoundsInParent())) {
                                            entity.hit(10);
                                            cannon.hit(10);
                                            if (player.getRemain() < 1) gameOver();

                                            if (!player.getCannon().isAlive() && player.getRemain() > 0) {
                                                zeroDeath = false;
                                                getChildren().add(player.respawn());
                                                player.decreaseRemain();
                                                negateCount = 0;
                                            }
                                        }
                                        break;
                                    case CANNONMISSILE:
                                        entity.move();
                                        if (!isObjectInWindow(entity, 20)) {
                                            fullChain = false;
                                            entity.hit(100);
                                        }
                                        tochicaList.forEach(tochica -> {
                                            if (entity.getBoundsInParent().intersects(tochica.getBoundingBox()) && tochica.hit(entity, 10))
                                                entity.hit(10);
                                        });
                                        getEntities().stream()
                                                .filter(e -> e instanceof Enemy || e instanceof InvaderMissile)
                                                .forEach(others -> {
                                                    if (!others.isInvincible() && entity.getBoundsInParent().intersects(others.getBoundsInParent())) {
                                                        entity.hit(10);
                                                        others.hit(10);
                                                        switch (others.getEntityType()) {
                                                            case INVADER:
                                                                if (rainbow) addScore(others, 500, true, true);
                                                                else addScore(others, 0, false, false);
                                                                break;
                                                            case UFO:
                                                                addScore(others, 0, true, false);
                                                                break;
                                                            case INVADERMISSILE:
                                                                if (5 == ++negateCount) {
                                                                    player.increaseRemain();
                                                                    negateCount = 0;
                                                                }
                                                                break;
                                                            default:
                                                                addScore(others, 0, false, false);
                                                                break;
                                                        }
                                                    }
                                                });
                                        break;
                                }
                            });

                            int beforeCount = getInvaderCount();
                            getChildren().removeIf(e -> e instanceof Entity && !((Entity)e).isAlive());
                            int invaderCount = getInvaderCount();

                            if (frameCounter % invaderSpeed == 0) {

                                double rightMost = 250, leftMost = 950 + 22, bottomMost = 0;
                                Invader lastInvader = null;
                                for (Entity entity : getEntities()) {
                                    if (entity instanceof Invader && entity.isAlive()) {
                                        lastInvader = (Invader) entity;
                                        double invaderX = entity.getTranslateX();
                                        rightMost = Math.max(rightMost, invaderX);
                                        leftMost = Math.min(leftMost, invaderX);
                                        bottomMost = Math.max(bottomMost, entity.getTranslateY());
                                    }
                                }

                                if (limitCount == 2 && bottomMost > GameMain.MAIN_Y) gameOver();

                                //――――――――――――――――――――――――――――――――――
//                                leftLine.setTranslateX(leftMost);
//                                rightLine.setTranslateX(rightMost);
                                //――――――――――――――――――――――――――――――――――

                                if (getInvaderCount() == 1 && lastInvader.getInvaderType() == 1) rainbow = true;

                                if (!down) {
                                    if (down = ((!invaderRight && 250 > leftMost) || (invaderRight && 950 < rightMost))) {
                                        if (bottomMost >= 476) {
                                            gameOver();
                                            return;
                                        }
                                        if (invaderRight && rainbow) limitCount++;
                                        invaderRight = !invaderRight;
                                    }
                                } else if (limitCount < 2) {
                                    if (stockLine-- > 0) {
                                        this.abobes = addInvader(3, this.abobes, GameMain.MAIN_X/2-158);
                                        invaderSpeed += 11;
                                    }
                                    down = false;
                                }
                            }

//                            if (invaderCount < 50)
//                                invaderSpeed -= (beforeCount - invaderCount);
                            if (invaderCount == 0) {
                                if (stockLine-- > 0) {
                                    this.abobes = addInvader(3, this.abobes, GameMain.MAIN_X/2-158);
                                    invaderSpeed += 11;
                                }
                                else clear();
                            }
                        }
                )
        );
        this.timeline.setCycleCount(6000);
        this.timeline.setOnFinished(event -> {
            timerCreate();
            play();
        });
    }

    private void addScore(Entity killed, int amount, boolean bonus, boolean rainbow) {
        if (killed instanceof Enemy)
            amount = amount == 0 ? ((Enemy)killed).getScore() : amount;
        else if (amount == 0) return;
        this.player.addScore(amount);
        if (bonus) {
            if (rainbow) this.floats.rainbow(killed.getTranslateX(), killed.getTranslateY(), amount);
            else this.floats.showScore(killed.getTranslateX(), killed.getTranslateY(), amount);
        }
    }

//    public void pause() {
//        if (pause) timeline.play();
//        else timeline.pause();
//        pause = !pause;
//    }

    public void play() {
        timeline.play();
    }

    private void clear() {
        this.player.addScore(1000);
        if (this.fullChain) {
            this.player.addScore(500);
            this.floats.showScore(430, 50, 500, "FullChain");
        }
        if (this.zeroDeath) {
            this.player.addScore(500);
            this.floats.showScore(600, 50, 500, "ZeroDeath");
        }
        this.timeline.stop();
        PauseTransition pause = new PauseTransition(Duration.seconds(2.0));
        pause.setOnFinished(event -> this.cover.nextLevel(++this.level+1, event1 -> play()));
        PauseTransition before = new PauseTransition(Duration.seconds(1.0));
        before.setOnFinished(event -> initGame(this.level));
        new SequentialTransition(pause, before).play();
    }

    private void gameOver() {
        this.timeline.stop();
        this.cover.showResult();
    }

    /*
    Tochica : 40
    Separate : 40
     */
    private List<BlockSet> createTochica(int level) {
        List<BlockSet> tochicaList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Tochica tochica = new Tochica(i*80+460, 400, level < 9 ? 20 : 20 - (level - 9) * 4);
            tochica.setBlocks(this);
            tochicaList.add(tochica);
        }
        System.out.println();
        return tochicaList;
    }

    private Floor createFloor(int level) {
        int blankSize = 0;
        if (level >= 5 && level < 9) blankSize = (level - 4)*100;
        else if (level >= 9) blankSize = 4*100;
        Floor floor = new Floor(blankSize, 520, 600-blankSize, (level < 9 ? 40 : 40 - (level - 9) * 6));
        floor.setBlocks(this);
        return floor;
    }

    private Invader[] createInvaders(int level) {
        Invader[] abobes = new Invader[11];
        for (int x = 0; x < 11; x++) {
            Invader aboveInvader = null;
            for (int y = 0; y < 5; y++) {
                Invader invader;
                double yPoint = 60 + (y+(level < 5 ? level : 0)) * 32, xPoint = GameMain.MAIN_X/2-158 + x*30;
                if (y == 0) abobes[x] = invader = new Invader(xPoint, yPoint, 3);
                else if (y == 3) invader = new Invader(xPoint-4, yPoint, aboveInvader, 1);
                else if (y == 4) invader = new Invader(xPoint-4, yPoint, aboveInvader, true, 1);
                else invader = new Invader(xPoint-2, yPoint, aboveInvader, 2);
                aboveInvader = invader;
                this.floats.invaderDebug(invader);
                this.getChildren().add(invader);
            }
        }
        return abobes;
    }

    private Invader[] addInvader(int type, Invader[] abobes, double base) {
        for (int i = 0; i < 11; i++) {
            if (abobes[i].isAlive()) {
                base = abobes[i].getTranslateX() - i*30;
                break;
            }
        }
        Invader[] nextAbobes = new Invader[11];
        double adjust = type == 1 ? -4 : type == 3 ? 0 : -2;
        ParallelTransition transition = new ParallelTransition();
        for (int x = 0; x < 11; x++) {
            double xPoint = base + x*30;
            double yPoint = 60;
            Invader invader = nextAbobes[x] = new Invader(xPoint-adjust, -10, type);
            abobes[x].setAboveInvader(invader);
            this.floats.invaderDebug(invader);
            this.getChildren().add(invader);

            TranslateTransition fall = new TranslateTransition(Duration.seconds(0.5), invader);
            fall.setFromY(-110);
            fall.setToY(yPoint);
            fall.setInterpolator(Interpolator.EASE_OUT);
            transition.getChildren().add(fall);
        }
        transition.play();
        return nextAbobes;
    }
}
