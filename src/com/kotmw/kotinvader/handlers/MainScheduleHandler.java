package com.kotmw.kotinvader.handlers;

import com.kotmw.kotinvader.entity.Entity;
import com.kotmw.kotinvader.gui.GameContainer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MainScheduleHandler implements EventHandler<ActionEvent> {

    GameContainer container;

    public MainScheduleHandler(GameContainer container) {
        this.container = container;
    }

    @Override
    public void handle(ActionEvent event) {
        container.getChildren().stream().filter(e -> e instanceof Entity).forEach(e -> ((Entity) e).move());

        container.getChildren().removeIf(e -> !container.isObjectInWindow((Entity)e));
    }
}
