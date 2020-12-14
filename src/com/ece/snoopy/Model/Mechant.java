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
    transient BufferedImage[] bufferedImages;
    private transient BufferedImage[] downSprites;
    private transient BufferedImage[] leftSprites;
    private transient BufferedImage[] rightSprites;
    private transient BufferedImage[] upSprites;
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
        downSprites = Content.MECHANT[0];
        leftSprites = Content.MECHANT[1];
        rightSprites = Content.MECHANT[2];
        upSprites = Content.MECHANT[3];

        animation.setFrames(downSprites);

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

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }

    /**
     * Change la frame en fonction de la direction
     * @param i direction
     * @param frame frame associée à la direction
     * @param delay temps entre chaque frame
     */
    private void setAnimation(int i, BufferedImage[] frame, int delay) {
        currentAnimation = i;
        animation.setFrames(frame);
        animation.setDelay(delay);

    }

    /**
     * Choisi dans quelle direction le méchant va se déplacer
     * @param player Le joueur qu'il cherche à suivre
     */
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
            if (colTile < playerColTile) {
                dir = RIGHT;
                setAnimation(RIGHT, rightSprites, 10);
            }
            else if (colTile > playerColTile) {
                dir = LEFT;
                setAnimation(LEFT, leftSprites, 10);
            }
            else
                dir = STOP;
        }
        else {
            if (rowTile < playerRowTile) {
                dir = DOWN;
                setAnimation(DOWN, downSprites, 10);
            }
            else if (rowTile > playerRowTile){
                dir = UP;
                setAnimation(UP, upSprites, 10);
            }
            else
                dir = STOP;
        }
        switch (dir) {
            case LEFT:
                x -= tileSize;
                break;
            case RIGHT:
                x += tileSize;
                break;
            case DOWN:
                y += tileSize;
                break;
            case UP:
                y -= 16;
                break;
            default:
                break;
        }
    }
}
