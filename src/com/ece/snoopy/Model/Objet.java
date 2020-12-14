package com.ece.snoopy.Model;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Map.Tile;
import com.ece.snoopy.Map.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Objet extends Model{

    //Types d'objets
    public static int ROCK = 0;
    public static int TRAP = 2;
    private int type;

    //Image
    transient BufferedImage[] bufferedImages;

    /**
     * Constructeur
     * @param tm TileMap
     * @param type int
     */
    public Objet(TileMap tm, int type) {
        super(tm);
        this.type = type;
        width = 16;
        height = 16;
        cwidth = 12;
        cheight = 12;

        if (type == ROCK)
            bufferedImages = Content.ROCK[0];
        if (type == TRAP)
            bufferedImages = Content.SPEED[0];
        animation.setFrames(bufferedImages);
        animation.setDelay(10);
    }

    /**
     * Mettre à jour l'animation des objets
     */
    public void update() {
        animation.update();
    }

    /**
     * Affichage de l'objet
     * @param graphics2D Graphics2D
     */
    public void draw(Graphics2D graphics2D) {
        super.draw(graphics2D);
    }

    public int getType() {
        return type;
    }

    /**
     * Pousser un objet par la gauche (direction du personnage)
     * @param objets Les objets
     * @param birds Les oiseaux
     * @return
     */
    public boolean goLeft(ArrayList<Objet> objets, ArrayList<Bird> birds) {
        int col = x / tileSize;
        int row = y / tileSize;
        if (col == 0 || tileMap.getType(row, col - 1) == Tile.BLOCKED)
            return false;
        x = x - tileSize;
        for (Objet o : objets) {
            if (!o.equals(this)) {
                if (this.intersects(o)) {
                    x = x + tileSize;
                    return false;
                }
            }
        }
        for (Bird b : birds) {
            if (this.intersects(b)) {
                x = x + tileSize;
                return false;
            }
        }
        return true;
    }

    /**
     * Pousser un objet par la droite (direction du personnage)
     * @param objets Liste d'objets
     * @param birds Liste d'oiseaux
     * @return
     */
    public boolean goRight(ArrayList<Objet> objets, ArrayList<Bird> birds) {
        int col = x / tileSize;
        int row = y / tileSize;
        if (col == tileMap.getNumCols() || tileMap.getType(row, col + 1) == Tile.BLOCKED )
            return false;
        x = x + tileSize;
        for (Objet o : objets) {
            if (!o.equals(this)) {
                if (this.intersects(o)) {
                    x = x - tileSize;
                    return false;
                }
            }
        }
        for (Bird b : birds) {
            if (this.intersects(b)) {
                x = x - tileSize;
                return false;
            }
        }
        return true;
    }

    /**
     * Pousser un objet par le haut (direction du personnage)
     * @param objets Les objets
     * @param birds Les oiseaux
     * @return
     */
    public boolean goUp(ArrayList<Objet> objets, ArrayList<Bird> birds) {
        int col = x / tileSize;
        int row = y / tileSize;
        if (row == 0 || tileMap.getType(row - 1, col) == Tile.BLOCKED)
            return false;
        y = y - tileSize;
        for (Objet o : objets) {
            if (!o.equals(this)) {
                if (this.intersects(o)) {
                    y = y + tileSize;
                    return false;
                }
            }
        }
        for (Bird b : birds) {
            if (this.intersects(b)) {
                y = y + tileSize;
                return false;
            }
        }
        return true;
    }

    /**
     * Pousser un objet par le bas (direction du personnage)
     * @param objets Les objets
     * @param birds Les oiseaux
     * @return
     */
    public boolean goDown(ArrayList<Objet> objets, ArrayList<Bird> birds) {
        int col = x / tileSize;
        int row = y / tileSize;
        if (row == tileMap.getNumRows() || tileMap.getType(row + 1, col) == Tile.BLOCKED)
            return false;
        y = y + tileSize;
        for (Objet o : objets) {
            if (!o.equals(this)) {
                if (this.intersects(o)) {
                    y = y - tileSize;
                    return false;
                }
            }
        }
        for (Bird b : birds) {
            if (this.intersects(b)) {
                y = y - tileSize;
                return false;
            }
        }
        return true;
    }
}
