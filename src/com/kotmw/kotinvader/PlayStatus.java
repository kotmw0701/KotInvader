package com.kotmw.kotinvader;

import com.kotmw.kotinvader.gameobjects.entity.*;
import com.kotmw.kotinvader.gui.GameMain;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.*;

public class PlayStatus {

    private Cannon cannon;
    private int remain;
    private int score;

    private IntegerProperty remainProperty;
    private StringProperty scoreProperty;

    private List<Enemy> killData;

    public PlayStatus() {
        this.cannon = new Cannon(GameMain.MAIN_X/2-15, 500);
        this.remain = 3;
        this.killData = new ArrayList<>();
    }

    public Cannon getCannon() {
        return cannon;
    }

    public Cannon respawn() {
        if (remain < 1) return null;
        return cannon = cannon.respawn();
    }

    public IntegerProperty remainProperty() {
        if (Objects.isNull(remainProperty))
            remainProperty = new SimpleIntegerProperty(remain);
        return remainProperty;
    }

    public StringProperty scoreProperty() {
        if (Objects.isNull(scoreProperty))
            scoreProperty = new SimpleStringProperty(String.format("%07d", score));
        return scoreProperty;
    }

    public int getRemain() {
        if (Objects.nonNull(remainProperty))
            return remainProperty.get();
        return remain;
    }

    public void increaseRemain() {
        if (Objects.nonNull(remainProperty)) remainProperty.set(++remain);
        else remain++;
    }

    public void decreaseRemain() {
        if (Objects.nonNull(remainProperty)) remainProperty.set(--remain);
        else remain--;
    }

    public int getScore() {
        if (Objects.nonNull(scoreProperty))
            return Integer.parseInt(scoreProperty.get());
        return score;
    }

    public int addScore(int amount) {
        score += amount;
        if (Objects.nonNull(scoreProperty)) scoreProperty.set(String.format("%07d", score));
        return score;
    }

    public void addKillData(Enemy enemy) {
        this.killData.add(enemy);
    }

    public long getKillUfos() {
        return this.killData.stream().filter(enemy -> enemy instanceof UFO).count();
    }

    public long getKillInvaders(int invaderType) {
        return this.killData.stream()
                .filter(enemy -> enemy instanceof Invader && ((Invader)enemy).getInvaderType() == invaderType)
                .count();
    }
}
