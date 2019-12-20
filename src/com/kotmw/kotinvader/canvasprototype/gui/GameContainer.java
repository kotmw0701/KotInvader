package com.kotmw.kotinvader.canvasprototype.gui;

import com.kotmw.kotinvader.canvasprototype.entity.Entity;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GameContainer extends Canvas {

    private List<Entity> entities;

    public GameContainer() {
        super(GameMain.MAIN_X, GameMain.MAIN_Y);
        entities = new ArrayList<>();

        this.getGraphicsContext2D().setFill(Color.GRAY);
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
