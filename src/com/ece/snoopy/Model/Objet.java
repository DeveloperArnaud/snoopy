package com.ece.snoopy.Model;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Map.Tile;
import com.ece.snoopy.Map.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Objet extends Model{
    public static int ROCK = 0;
    public static int ROCK_LIGHT = 1;
    public static int SPEED = 2;

    private int type;

    BufferedImage[] bufferedImages;

    private ArrayList<int[]> setTiles;

    public Objet(TileMap tm, int type) {
        super(tm);
        this.type = type;
        width = 16;
        height = 16;
        cwidth = 12;
        cheight = 12;

        moveSpeed = 16;
        if (type == ROCK)
            bufferedImages = Content.ROCK[0];
        if (type == ROCK_LIGHT)
            bufferedImages = Content.ROCK_LIGHT[0];
        if (type == SPEED)
            bufferedImages = Content.SPEED[0];
        animation.setFrames(bufferedImages);
        animation.setDelay(10);
        setTiles = new ArrayList<>();
    }

    public void addSet(int [] i ) {
        setTiles.add(i);
    }

    public ArrayList<int[]> getSetTiles() {
        return setTiles;
    }

    public void update() {
        animation.update();
    }

    public void draw(Graphics2D graphics2D) {
        super.draw(graphics2D);
    }

    public int getType() {
        return type;
    }

    public boolean goLeft() {
        int col = x / tileSize;
        int row = y / tileSize;
        if (col == 0 || tileMap.getType(row, col - 1) == Tile.BLOCKED)
            return false;
        x = x - tileSize;
        return true;
    }

    public boolean goRight() {
        int col = x / tileSize;
        int row = y / tileSize;
        if (col == tileMap.getNumCols() || tileMap.getType(row, col + 1) == Tile.BLOCKED )
            return false;
        x = x + tileSize;
        return true;
    }

    public boolean goUp() {
        int col = x / tileSize;
        int row = y / tileSize;
        if (row == 0 || tileMap.getType(row - 1, col) == Tile.BLOCKED)
            return false;
        y = y - tileSize;
        return true;
    }

    public boolean goDown() {
        int col = x / tileSize;
        int row = y / tileSize;
        if (row == tileMap.getNumRows() || tileMap.getType(row + 1, col) == Tile.BLOCKED)
            return false;
        y = y + tileSize;
        return true;
    }
}
