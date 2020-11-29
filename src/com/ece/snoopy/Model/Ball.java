package com.ece.snoopy.Model;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Main.GamePanel;
import com.ece.snoopy.Map.TileMap;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
/*
    EN COURS DE DEVELOPPEMENT
 */
public class Ball extends Model {

    BufferedImage[] ballImage;

    /**
     * Constructeur
     * @param tm tileMap
     */
    public Ball(TileMap tm) {
        super(tm);
        width = 16;
        height = 16;
        cwidth = 12;
        cheight = 12;
        ballImage = Content.BALL[0];

        animation.setFrames(ballImage);
        animation.setDelay(0);

    }

    /**
     * Affichage de la balle
     * @param graphics2D
     */
    public void draw(Graphics2D graphics2D) {

       graphics2D.fillOval(12,12,10,10);

    }
}
