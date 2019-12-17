package com.kotmw.kotinvader.handlers;

import com.kotmw.kotinvader.entity.Cannon;
import com.kotmw.kotinvader.entity.Enemy;
import com.kotmw.kotinvader.entity.Entity;
import com.kotmw.kotinvader.entity.missiles.InvaderMissile;
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
        container.getEntities().forEach(entity -> {
            entity.move();
            switch (entity.getEntityType()) {
                case INVADERMISSILE:
                    if (!container.isObjectInWindow(entity)) {
                        entity.hit(100);
                    }
                    container.getEntities().stream()
                            .filter(e -> e instanceof Cannon /*|| e instanceof Tochica */)
                            .forEach(others -> {
                                if (entity.getBoundsInParent().intersects(others.getBoundsInParent())) {
                                    entity.hit(10);
                                    others.hit(10);
                                }
                            });
                    break;
                case CANNONMISSILE:
                    if (!container.isObjectInWindow(entity)) {
                        entity.hit(100);
                    }
                    container.getEntities().stream()
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

        container.getChildren().removeIf(e -> !((Entity)e).isAlive());
    }
}
