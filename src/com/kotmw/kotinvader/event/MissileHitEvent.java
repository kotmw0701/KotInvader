package com.kotmw.kotinvader.event;

import com.kotmw.kotinvader.gameobjects.entity.Entity;
import com.kotmw.kotinvader.gameobjects.entity.missiles.Missile;
import javafx.event.Event;
import javafx.event.EventType;

public class MissileHitEvent extends Event {

    public static final EventType<MissileHitEvent> MISSILE_HIT = new EventType<>("MISSILE_HIT");

    public MissileHitEvent(Missile missile, Entity entity) {
        super(MISSILE_HIT);
    }
}
