package com.ece.snoopy.Model;

import com.ece.snoopy.Map.Tile;
import com.ece.snoopy.Map.TileMap;

import java.awt.*;

public abstract class Model {

    protected TileMap tileMap;
    protected int tileSize;
    protected  Animation animation;
    protected  boolean moving;
    protected  boolean left;
    protected  boolean right;
    protected  boolean up;
    protected  boolean down;


    protected int width;
    protected int height;
    protected int cwidth;
    protected int cheight;

    // position
    protected int x;
    protected int y;
    protected int xdest;
    protected int ydest;
    protected int rowTile;
    protected int colTile;

    //animation
    protected int moveSpeed;
    protected int currentAnimation;
    protected int xmap;
    protected int ymap;

    /**
     * Constructor
     * @param tm
     */
    public Model(TileMap tm) {
        this.tileMap = tm;
        this.tileSize = tileMap.getTileSize();
        animation = new Animation();
    }

    public void draw(Graphics2D g){
        setMapPosition();
        g.drawImage(
                animation.getFrames(),
                x + xmap - width / 2,
                y + ymap - height / 2,
                null);
    }

    public void setMapPosition() {
        xmap = tileMap.getX();
        ymap = tileMap.getY();
    }
    

    public void setLeft() {
        if(moving) return;
        left = true;
        moving = validateNextPosition();
    }
    public void setRight() {
        if(moving) return;
        right = true;
        moving = validateNextPosition();
    }
    public void setUp() {
        if(moving) return;
        up = true;
        moving = validateNextPosition();
    }

    public void setDown() {
        if(moving) return;
        down = true;
        moving = validateNextPosition();
    }

    private boolean validateNextPosition() {

        if(moving) return true;

        rowTile = y / tileSize;
        colTile = x / tileSize;

        if(left) {
            if(colTile == 0 || tileMap.getType(rowTile, colTile - 1) == Tile.BLOCKED ) {
                return false;
            } else {
                xdest = x - tileSize;
            }
        }
        if(right) {
            if(colTile == tileMap.getNumCols() || tileMap.getType(rowTile, colTile + 1) == Tile.BLOCKED ) {
                return false;
            } else {
                xdest = x + tileSize;
            }
        }
        if(up) {
            if(rowTile == 0 || tileMap.getType(rowTile - 1, colTile) == Tile.BLOCKED ) {
                return false;
            } else {
                ydest = y - tileSize;
            }
        }
        if(down) {
            if(rowTile == tileMap.getNumRows() - 1 || tileMap.getType(rowTile + 1, colTile) == Tile.BLOCKED ) {
                return false;
            } else {
                ydest = y + tileSize;
            }
        }
        return true;
    }

    public void update() {

        if(moving) getNextPosition();

        if(x == xdest && y == ydest) {
            left = right = up = down = moving = false;
            rowTile = y / tileSize;
            colTile = x / tileSize;
        }

        animation.update();
    }

    public void setTilePosition(int i, int j) {
        y = i  * tileSize + tileSize /2;
        x = j  * tileSize + tileSize /2;
        xdest = x;
        ydest = y;
    }



    private void getNextPosition() {

        if(left && x > xdest) x -= moveSpeed;
        else left = false;
        if(left && x < xdest) x = xdest;

        if(right && x < xdest) x += moveSpeed;
        else right = false;
        if(right && x > xdest) x = xdest;

        if(up && y > ydest) y -= moveSpeed;
        else up = false;
        if(up && y < ydest) y = ydest;

        if(down && y < ydest) y += moveSpeed;
        else down = false;
        if(down && y > ydest) y = ydest;


    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean intersects(Model m){
        return getRectangle().intersects(m.getRectangle());
    }
    protected Rectangle getRectangle() {
        return new Rectangle(x,y,cwidth,cheight);
    }

}