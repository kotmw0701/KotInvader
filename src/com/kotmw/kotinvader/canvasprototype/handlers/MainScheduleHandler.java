package com.kotmw.kotinvader.canvasprototype.handlers;

import com.kotmw.kotinvader.canvasprototype.gui.GameContainer;
import com.kotmw.kotinvader.canvasprototype.gui.GameMain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MainScheduleHandler implements EventHandler<ActionEvent> {

    private GameContainer container;

    public MainScheduleHandler(GameContainer container) {
        this.container = container;
    }

    @Override
    public void handle(ActionEvent event) {
        container.getGraphicsContext2D().clearRect(0, 0, GameMain.MAIN_X, GameMain.MAIN_Y);
        container.getEntities().forEach(entity -> {
            entity.move();
            entity.draw(container.getGraphicsContext2D());
        });
    }
}
