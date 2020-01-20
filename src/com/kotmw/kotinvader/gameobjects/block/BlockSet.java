package com.kotmw.kotinvader.gameobjects.block;

import com.kotmw.kotinvader.gameobjects.entity.Entity;
import com.kotmw.kotinvader.gui.GameContainer;
import javafx.geometry.BoundingBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class BlockSet {

    protected final List<Block> blockList;

    private double x, y;
    private double width, height;

    private BoundingBox boundingBox;

    public BlockSet(double x, double y, int count, boolean vertical, int hitPoint) {
        this(x, y, vertical ? 1 : count, vertical ? count : 1, hitPoint);
    }

    public BlockSet(double x, double y, int widthCount, int heightCount, int hitPoint) {
        this.x = x;
        this.y = y;
        this.width = widthCount*2;
        this.height = heightCount*2;
        this.boundingBox = new BoundingBox(x, y, width, height);
        this.blockList = new ArrayList<>(widthCount*heightCount);
        for (int i = 0; i <= heightCount; i++) {
            for (int j = 0; j <= widthCount; j++) {
                this.blockList.add(new Block(j*2+x, i*2+y, 2, 2, hitPoint));
            }
        }
    }

    BlockSet(double x, double y, int hitPoint, byte[][] model) {
        this.x = x;
        this.y = y;
        this.width = model[0].length*2;
        this.height = model.length*2;
        this.boundingBox = new BoundingBox(x, y, width, height);
        this.blockList = new ArrayList<>(model.length*model[0].length);
        for (int i = 0; i < model.length; i++) {            //y
            for (int j = 0; j < model[0].length; j++) {     //x
                if (model[i][j] == 0) continue;
                Block block = new Block(j*2+x, i*2+y, 2, 2, hitPoint);
                this.blockList.add(block);
            }
        }
    }

    public void setBlocks(GameContainer container) {
        container.getChildren().addAll(this.blockList);
    }

    public boolean hit(Entity entity, int damage) {
        for (Block block : this.blockList) {
            if (block.getBoundsInParent().intersects(entity.getBoundsInParent())) {
                if (block.getFill() == Color.TRANSPARENT) continue;
                for (Block target : this.blockList) {
                    double distance = Math.sqrt(Math.pow(block.getTranslateX() - target.getTranslateX(), 2)
                            + Math.pow(block.getTranslateY() - target.getTranslateY(), 2));
                    if (distance < 1) target.hit(damage);
                    else if (distance < 2.5) target.hit(damage*0.5);
                    else if (distance < 5.0) target.hit(damage*0.25);
                }
                this.blockList.removeIf(target -> target.getFill() == Color.TRANSPARENT);
                return true;
            }
        }
        return false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }
}
