package com.kotmw.kotinvader.handlers;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyHandler implements EventHandler<KeyEvent> {

    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
            switch (event.getCode()) {
                case LEFT:
                    break;
                case RIGHT:
                    break;
                case SPACE:
                    break;
                default:
                    break;

            }
        } else if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            if (event.getCode().isArrowKey()) {

            }
        }
    }
}
