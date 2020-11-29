package com.ece.snoopy.Model;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Map.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;

public class Bird extends Model {

    //Image
    BufferedImage[] bufferedImages;

    /**
     * Constructeur
     * @param tm TileMap
     */
    public Bird(TileMap tm) {
        super(tm);

        width = 16;
        height = 16;
        cwidth = 12;
        cheight = 12;

        bufferedImages = Content.BIRD[0];
        animation.setFrames(bufferedImages);
        animation.setDelay(10);
    }

    /**
     * Mettre à jour le sprite d'animation de l'oiseau
     */
    public void update() {
        animation.update();
    }

    /**
     * Affichage de l'oiseau
     * @param graphics2D Graphics2D
     */
    public void draw(Graphics2D graphics2D) {
        super.draw(graphics2D);
    }
}
