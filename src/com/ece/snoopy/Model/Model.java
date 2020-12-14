package com.ece.snoopy.Model;

import com.ece.snoopy.Map.Tile;
import com.ece.snoopy.Map.TileMap;

import java.awt.*;
import java.io.Serializable;

public abstract class Model implements Serializable {

    //Mouvements
    protected TileMap tileMap;
    protected Animation animation;
    protected int tileSize;
    protected boolean moving;
    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;

    //Dimensions des sprites d'animation (16x16, au plus petit 12x12)
    protected int width;
    protected int height;
    protected int cwidth;
    protected int cheight;

    //Tile Position
    protected int tilePositionX;
    protected int tilePositionY;

    //Position
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
     * Constructeur
     * @param tm
     */
    public Model(TileMap tm) {
        this.tileMap = tm;
        this.tileSize = tileMap.getTileSize();
        animation = new Animation();
    }

    /**
     * Affichage
     * @param g Graphics2D
     */
    public void draw(Graphics2D g){
        setMapPosition();
        g.drawImage(
                animation.getFrames(),
                x + xmap - width / 2,
                y + ymap - height / 2,
                null);
    }

    /**
     * On met à jour xmap et ymap
     */
    public void setMapPosition() {
        xmap = tileMap.getX();
        ymap = tileMap.getY();
    }

    /* GESTION DES DIRECTIONS ( DROITE, GAUCHE, HAUT, BAS) */

    /**
     * On part sur la gauche
     */
    public void setLeft() {
        if(moving) return;
        left = true;
        moving = validateNextPosition();
    }

    /**
     * On part sur la droite
     */
    public void setRight() {
        if(moving) return;
        right = true;
        moving = validateNextPosition();
    }

    /**
     * On part vers le haut
     */
    public void setUp() {
        if(moving) return;
        up = true;
        moving = validateNextPosition();
    }

    /**
     * On part vers le bas
     */
    public void setDown() {
        if(moving) return;
        down = true;
        moving = validateNextPosition();
    }
    /*   FIN BLOC GESTION DES DIRECTIONS */

    /**
     * Permet de d'eviter que le personnage traverse une case de type BLOCKED
     * @return Retourne true s'il y peut se déplacer sur une case et false s'il ne peut pas
     */
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

    /**
     * Mettre à jour les directions et l'animation
     */
    public void update() {

        if(moving) getNextPosition();

        if(x == xdest && y == ydest) {
            left = right = up = down = moving = false;
            rowTile = y / tileSize;
            colTile = x / tileSize;
        }

        animation.update();
    }

    /**
     *  Permet de placer sur une case spécifique un objet, le personnage etc..
     * @param i
     * @param j
     */
    public void setTilePosition(int i, int j) {
        y = i  * tileSize + tileSize /2;
        x = j  * tileSize + tileSize /2;
        xdest = x;
        ydest = y;
    }

    /**
     * On recupère la position de la tuile
     * @return positon de tuile en x
     */
    public int getTilePositionX() {
        tilePositionX = (int)(xdest / (tileSize + tileSize) * 2 - 0.5);
        return tilePositionX;
    }

    /**
     * On recupère la position de la tuile
     * @return positon de tuile en y
     */

    public int getTilePositionY() {
        tilePositionY = (int) (y / (tileSize + tileSize) * 2 - 0.5);
        return tilePositionY;
    }

    /**
     *  Récupérer la prochaine direction en fonction de la direction du personnage afin d'instaurer un déplacement case par case
     */
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

    /**
     * Obtenir le x
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Obtenir le y
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Permet de gérer l'intersection entre le personnage et les objets
     * @param m Model
     * @return Retourne true si le personnage est sur la même case que l'oiseau
     */
    public boolean intersects(Model m){
        return getRectangle().intersects(m.getRectangle());
    }

    /**
     * Récupérer l'intersection d'un rectangle (oiseau) et le personange
     * @return Un rectangle correspondant un oiseau (objet récupérable)
     */
    protected Rectangle getRectangle() {
        return new Rectangle(x,y,cwidth,cheight);
    }

}