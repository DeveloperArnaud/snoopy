package com.ece.snoopy.Model;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Main.GamePanel;
import com.ece.snoopy.Map.TileMap;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Ball extends Model {

    Player player;
    BufferedImage[] ballImage;
    int x = 0, y = 0,  velX = 2, velY = 2;


    /**
     * Constructor
     *
     * @param tm
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

    public void draw(Graphics2D graphics2D) {

        super.draw(graphics2D);


    }
}
