package com.ece.snoopy.Model;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Map.TileMap;
import com.ece.snoopy.SoundFX.SoundFX;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/*
    EN COURS DE DEVELOPPEMENT
 */
public class Ball extends Model {

    public static final int NO = 0;
    public static final int NE = 1;
    public static final int SE = 2;
    public static final int SO = 3;

    private int dir;
    private int moveSpeed = 1;
    private long ticks;


    BufferedImage[] ballImage;

    /**
     * Constructeur
     * @param tm tileMap
     */
    public Ball(TileMap tm, int moveSpeed) {
        super(tm);
        ticks = 0;
        width = 16;
        height = 16;
        cwidth = 12;
        cheight = 12;
        Random r = new Random();
        dir = r.nextInt(4);
        ballImage = Content.BALL[0];

        SoundFX.loadSound("/SFX/menuoption.wav", "collision");
        SoundFX.setVolume("collision", -20);

        this.moveSpeed = moveSpeed;
        animation.setFrames(ballImage);
        animation.setDelay(0);

    }

    public void update() {
        ticks++;
        if (dir == NO) {
            int rowTile = (y - 3) / tileSize;
            int colTile = (x - 3) / tileSize;
            if (rowTile == 16) {
                SoundFX.play("collision");
                dir = SO;
            }
            else if (colTile == 16) {
                SoundFX.play("collision");

            if (rowTile <= 16)
                dir = SO;
            else if (colTile <= 16)

                dir = NE;
            }
        }
        else if (dir == SO) {
            int rowTile = (y + 4) / tileSize;
            int colTile = (x - 3) / tileSize;

            if (rowTile == 25) {
                SoundFX.play("collision");
                dir = NO;
            }
            else if (colTile == 16) {
                SoundFX.play("collision");

            if (rowTile >= 25)
                dir = NO;
            else if (colTile <= 16)

                dir = SE;
            }
        }
        else if (dir == SE) {
            int rowTile = (y + 4) / tileSize;
            int colTile = (x + 4) / tileSize;

            if (rowTile == 25) {
                SoundFX.play("collision");
                dir = NE;
            }
            else if (colTile == 25) {
                SoundFX.play("collision");

            if (rowTile >= 25)
                dir = NE;
            else if (colTile >= 25)

                dir = SO;
            }
        }
        else if (dir == NE) {
            int rowTile = (y - 3) / tileSize;
            int colTile = (x + 4) / tileSize;

            if (rowTile == 16) {
                SoundFX.play("collision");
                dir = SE;
            }
            else if (colTile == 25) {
                SoundFX.play("collision");

            if (rowTile <= 16)
                dir = SE;
            else if (colTile >= 25)

                dir = NO;
            }
        }

        if (dir == NO) {
            x = x - moveSpeed;
            y = y - moveSpeed;
        }
        if (dir == NE) {
            x = x + moveSpeed;
            y = y - moveSpeed;
        }
        if (dir == SO) {
            x = x - moveSpeed;
            y = y + moveSpeed;
        }
        if (dir == SE) {
            x = x + moveSpeed;
            y = y + moveSpeed;
        }
        super.update();
    }

    /**
     * Affichage de la balle
     * @param graphics2D
     */
    public void draw(Graphics2D graphics2D) {
        super.draw(graphics2D);
       //graphics2D.fillOval(12,12,10,10);
    }
}
