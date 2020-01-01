package com.kotmw.kotinvader;

import com.kotmw.kotinvader.entity.Cannon;
import com.kotmw.kotinvader.gui.GameMain;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class PlayStatus {

    private Cannon cannon;
    private int remain;
    private int score;

    private IntegerProperty remainProperty;
    private StringProperty scoreProperty;

    public PlayStatus() {
        cannon = new Cannon(GameMain.MAIN_X/2, 500);
        remain = 3;
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
}
