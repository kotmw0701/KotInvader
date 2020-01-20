package com.kotmw.kotinvader.gameobjects.block;

import com.kotmw.kotinvader.gameobjects.entity.Entity;

public class Floor extends BlockSet {

    private final double reach = 10.0;

    public Floor(double x, double y, int widthCount, int hitPoint) {
        super(x, y, widthCount, 0, hitPoint);
    }

    public boolean hasFloor(Entity entity) {
        double x = entity.getTranslateX();
        double y = entity.getTranslateY()+20;
        if (entity.getDirection() == Entity.Direction.RIGHT)
            return this.blockList.stream().anyMatch(target -> Math.sqrt(Math.pow(x - target.getTranslateX()+entity.getImage().getWidth(), 2) + Math.pow(y - target.getTranslateY(), 2)) < reach);
        else if (entity.getDirection() == Entity.Direction.LEFT)
            return this.blockList.stream().anyMatch(target -> Math.sqrt(Math.pow(x - target.getTranslateX(), 2) + Math.pow(y - target.getTranslateY(), 2)) < reach);
        return true;
    }
}
