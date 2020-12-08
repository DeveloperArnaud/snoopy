package com.ece.snoopy.Model;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Map.TileMap;
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

    private long ticks;


    BufferedImage[] ballImage;

    /**
     * Constructeur
     * @param tm tileMap
     */
    public Ball(TileMap tm) {
        super(tm);
        ticks = 0;
        width = 16;
        height = 16;
        cwidth = 12;
        cheight = 12;
        Random r = new Random();
        dir = r.nextInt(4);
        System.out.println(dir);
        ballImage = Content.BALL[0];

        animation.setFrames(ballImage);
        animation.setDelay(0);

    }

    public void update() {
        ticks++;
        if (dir == NO) {
            int rowTile = (y - 3) / tileSize;
            int colTile = (x - 3) / tileSize;
            if (rowTile == 16)
                dir = SO;
            else if (colTile == 16)
                dir = NE;
        }
        else if (dir == SO) {
            int rowTile = (y + 4) / tileSize;
            int colTile = (x - 3) / tileSize;
            if (rowTile == 25)
                dir = NO;
            else if (colTile == 16)
                dir = SE;
        }
        else if (dir == SE) {
            int rowTile = (y + 4) / tileSize;
            int colTile = (x + 4) / tileSize;
            if (rowTile == 25)
                dir = NE;
            else if (colTile == 25)
                dir = SO;
        }
        else if (dir == NE) {
            int rowTile = (y - 3) / tileSize;
            int colTile = (x + 4) / tileSize;
            if (rowTile == 16)
                dir = SE;
            else if (colTile == 25)
                dir = NO;
        }

        if (dir == NO) {
            x = x - 1;
            y = y - 1;
        }
        if (dir == NE) {
            x = x + 1;
            y = y - 1;
        }
        if (dir == SO) {
            x = x - 1;
            y = y + 1;
        }
        if (dir == SE) {
            x = x + 1;
            y = y + 1;
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
