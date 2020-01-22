package com.kotmw.kotinvader.event;

import javafx.event.Event;
import javafx.event.EventType;

public class MissileHitEvent extends Event {

    public static final EventType<MissileHitEvent> MISSILE_HIT = new EventType<>("MISSILE_HIT");

    public MissileHitEvent() {
        super(MISSILE_HIT);
    }
}
