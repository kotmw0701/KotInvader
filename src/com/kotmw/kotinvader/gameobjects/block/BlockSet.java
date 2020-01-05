package com.kotmw.kotinvader.gameobjects.block;

import com.kotmw.kotinvader.gameobjects.entity.Entity;
import com.kotmw.kotinvader.gui.GameContainer;
import javafx.geometry.BoundingBox;
import javafx.scene.paint.Color;

public class BlockSet {

    private final Block[][] blocks;

    private double x, y;
    private double width, height;

    private BoundingBox boundingBox;

    protected BlockSet(double x, double y, int length, boolean vertical) {
        this(x, y, vertical ? 1 : length, vertical ? length : 1);
    }

    protected BlockSet(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.boundingBox = new BoundingBox(x, y, width, height);
        blocks = new Block[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                blocks[i][j] = new Block(j*2+x, i*2+y, 2, 2, Color.RED);
            }
        }
    }

    protected BlockSet(double x, double y, int[][] model) {
        this.x = x;
        this.y = y;
        this.width = model[0].length*2;
        this.height = model.length*2;
        this.boundingBox = new BoundingBox(x, y, width, height);
        blocks = new Block[model.length][model[0].length];
        for (int i = 0; i < model.length; i++) {            //y
            for (int j = 0; j < model[0].length; j++) {     //x
                if (model[i][j] == 0) continue;
                Block block = new Block(j*2+x, i*2+y, 2, 2, Color.RED);
                blocks[i][j] = block;
            }
        }
    }

    public void setBlocks(GameContainer container) {
        for (Block[] y : blocks)
            for (Block x : y)
                if (x != null)
                    container.getChildren().add(x);
    }

    public boolean hit(Entity entity) {
        for (int i = 0; i < blocks.length; i++) {            //y
            for (int j = 0; j < blocks[0].length; j++) {     //x
                if (blocks[i][j] != null && blocks[i][j].getBoundsInParent().intersects(entity.getBoundsInParent())) {
                    if (blocks[i][j].getFill() == Color.TRANSPARENT) continue;
                    blocks[i][j].setFill(Color.TRANSPARENT);
                    return true;
                }
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
