package com.ece.snoopy.Model;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Map.TileMap;
import com.ece.snoopy.SoundFX.SoundFX;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Mechant extends Model {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    public static final int STOP = 4;

    //Sprite de déplacement du personnage
    BufferedImage[] bufferedImages;
    private int dir;

    /**
     * Constructeur
     * @param tileMap TileMap
     */
    public Mechant(TileMap tileMap) {
        super(tileMap);
        width = 16;
        height = 16;
        cwidth = 12;
        cheight = 12;
        dir = STOP;
        bufferedImages = Content.MECHANT[0];

        animation.setFrames(bufferedImages);
        animation.setDelay(10);
        SoundFX.loadSound("/SFX/destroyed.wav", "destroyed");
        SoundFX.setVolume("destroyed", -15);
    }

    /**
     * Mettre à jour la sprite d'animation de direction en fonction de la direction effectué (ex : DIRECTION DROITE = SPRITE ANIMATION DROITE)
     */
    public void update() {
        super.update();
    }

    public void draw(Graphics2D g) {
        super.draw(g);
    }

    public void computeDirection(Player player) {
        int playerRowTile = player.getY() / tileSize;
        int playerColTile = player.getX() / tileSize;
        int rowTile = this.getY() / tileSize;
        int colTile = this.getX() / tileSize;
        int yDiff = rowTile - playerRowTile;
        int xDiff = colTile - playerColTile;
        if (xDiff < 0)
            xDiff *= -1;
        if (yDiff < 0)
            yDiff *= -1;
        if (xDiff > yDiff) {
            System.out.println("Player x:" + playerColTile + " Mob x: " + colTile);
            if (colTile < playerColTile) {
                dir = RIGHT;
            }
            else if (colTile > playerColTile) {
                dir = LEFT;
            }
            else {
                dir = STOP;
            }
        }
        else {
            System.out.println("Player y:" + playerRowTile + " Mob y: " + rowTile);
            if (rowTile < playerRowTile) {
                dir = DOWN;
            }
            else if (rowTile > playerRowTile){
                dir = UP;
            }
            else {
                dir = STOP;
            }
        }
        switch (dir) {
            case LEFT:
                x -= tileSize;
                System.out.println("LEFT");
                break;
            case RIGHT:
                x += tileSize;
                System.out.println("RIGHT");
                break;
            case DOWN:
                y += tileSize;
                System.out.println("DOWN");
                break;
            case UP:
                y -= 16;
                System.out.println("UP");
                break;
            default:
                break;
        }
    }
}
