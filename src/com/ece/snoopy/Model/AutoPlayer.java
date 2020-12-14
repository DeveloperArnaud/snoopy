package com.ece.snoopy.Model;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Map.TileMap;
import com.ece.snoopy.SoundFX.SoundFX;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AutoPlayer extends Model {

    //Sprite de déplacement du personnage
    private transient BufferedImage[] downSprites;
    private transient BufferedImage[] leftSprites;
    private transient BufferedImage[] rightSprites;
    private transient BufferedImage[] upSprites;

    private int offsetNbTileX = 17;
    private int offsetNbTileY = 17;
    private int widthMap = 8;
    private int heightMap = 8;
    //Directions
    private final int DOWN = 0;
    private final int UP = 3;
    private final int LEFT = 1;
    private final int RIGHT = 2;
    private long ticks;
    private int nbBirds;
    private int life = 3;
    private int invincibility;

    private ArrayList bufferDeplacement;

    /**
     * Constructeur
     * @param tileMap TileMap
     */
    public AutoPlayer(TileMap tileMap) {
        super(tileMap);
        width = 16;
        height = 16;
        cwidth = 12;
        cheight = 12;
        invincibility = 100;
        moveSpeed = 2;
        bufferDeplacement = new ArrayList<Integer>();
        downSprites = Content.PLAYER[0];
        leftSprites = Content.PLAYER[1];
        rightSprites = Content.PLAYER[2];
        upSprites = Content.PLAYER[3];

        animation.setFrames(downSprites);
        animation.setDelay(10);
        SoundFX.loadSound("/SFX/destroyed.wav", "destroyed");
        SoundFX.setVolume("destroyed", -15);
    }


    //https://fr.wikipedia.org/wiki/Algorithme_de_Dijkstra#:~:text=En%20th%C3%A9orie%20des%20graphes%2C%20l,probl%C3%A8me%20du%20plus%20court%20chemin.

    /**
     * On créé un carte des distances pour chaque sommet, initialisée à + l'infini sauf pour la case de départ qui sera à 0
     * @param x la case de départ en x
     * @param y la case de départ en y
     * @return La carte des distances
     */
    private Map<Integer, Integer> initPath(int x, int y) {
        Map<Integer, Integer> distances = new HashMap<>();
        for (int i = offsetNbTileY * 8; i < (offsetNbTileY + heightMap + 1) * 8; i++)
            distances.put(i, Integer.MAX_VALUE - 1000);
        distances.put(x + 8 * y, 0);
        return distances;
    }

    /**
     * On cherche le sommet avec la plus petite distance
     * @param Q La liste des sommets parmis lesquels on choisi
     * @param distances tableau des distances
     * @return Le sommet à choisir
     */
    private Integer trouverMin(ArrayList<Integer> Q, Map<Integer, Integer> distances) {
        int mini = Integer.MAX_VALUE - 1000;
        int sommet = -1;
        for (Integer i : Q) {
            if (!distances.containsKey(i))
                continue;
            if (distances.get(i) < mini) {
                mini = distances.get(i);
                sommet = i;
            }
        }
        return sommet;
    }

    /**
     * On cherche à minimiser le cout entre s1 et s2, on met à jour les distances si c'est le cas
     * @param s1 sommet de départ
     * @param s2 sommet d'arrivée
     * @param distances tableau des distances
     * @param prede tableau des pères que l'on construit au fur et à mesure
     * @param objets La liste des objets de la carte pour déterminer les collision
     */
    private void majDistance(Integer s1, Integer s2, Map<Integer, Integer> distances, Map<Integer, Integer> prede, ArrayList<Objet> objets) {
        int typeS1 = tileMap.getIndex((s1 / 8) - 1, (s1 % 8) + 17);
        int typeS2 = tileMap.getIndex((s2 / 8) - 1, (s2 % 8) + 17);
        if (typeS1 == 20)
            return;
        if (typeS2 == 20)
            return;
        if (!distances.containsKey(s2) || !distances.containsKey(s1)) {
            System.out.println(s2 + " " + s1);
            return;
        }
        if (objets != null) {
            for (Objet e : objets) {
                int rowT = e.getY() / tileSize;
                int colT = e.getX() / tileSize;
                int rowTs2 = s2 / 8 -1;
                int colTs2 = s2 % 8 + 17;
                if (rowT == rowTs2 && colT == colTs2) {
                    System.out.println("On marche sur : " + rowT + " " + colT);
                    return;
                }
            }
        }
        if (distances.get(s2) > distances.get(s1) + 1) {
            distances.put(s2, distances.get(s1) + 1);
            prede.put(s2, s1);
        }
    }

    /**
     * Implémentation de Dijkstra dans laquelle on va chercher à déterminer les distances entre le point de départ et toutes les autres tiles
     * @param b1 Le point de départ
     * @param objets La liste des objets de la carte
     */
    public void computePath(Integer b1, ArrayList<Objet> objets) {
        int row = this.y / tileSize - 1;
        int col = this.x / tileSize - 1;
        System.out.println("x : " + col + " y : " + row);
        Map<Integer, Integer> distances = initPath(col, row);
        System.out.println();
        Map<Integer, Integer> prede = new HashMap<>();
        ArrayList<Integer> Q = new ArrayList<>();
        for (int i = offsetNbTileY * widthMap; i < (offsetNbTileY + heightMap + 2) * widthMap; i++)
            Q.add(i);
        while (!Q.isEmpty()) {
            Integer s1 = trouverMin(Q,distances);
            if (s1 == -1)
                break;
            Q.remove(Q.indexOf(s1));

            majDistance(s1, s1 - widthMap, distances, prede, objets);
            majDistance(s1, s1 + widthMap, distances, prede, objets);
            if ((s1 - 1) % widthMap != 7)
            majDistance(s1, s1 - 1, distances, prede, objets);
            if ((s1 + 1) % widthMap != 0)
            majDistance(s1, s1 + 1, distances, prede, objets);
        }
        while (b1 != null) {
            bufferDeplacement.add(b1);
            b1 = prede.get(b1);
        }
    }

    public void update() {
        ticks++;
        if (invincibility > 0)
            invincibility--;
        if (!bufferDeplacement.isEmpty() && ticks > 100 && ticks  % 10 == 9) {
            Integer xDep = ((Integer) bufferDeplacement.get(bufferDeplacement.size() - 1)) % 8;
            Integer yDep = ((Integer) bufferDeplacement.get(bufferDeplacement.size() - 1)) / 8;
            System.out.println("Next deplacement x : " + (xDep + 17) + ", y : " + (yDep - 1));
            bufferDeplacement.remove(bufferDeplacement.size() - 1);
            this.x = (xDep + 17) * tileSize + 8;
            this.y = (yDep -  1) * tileSize + 8;
            int rowT = this.y / tileSize;
            int colT = this.x / tileSize;
            int typeTile = tileMap.getIndex(rowT, colT);
            if (typeTile == 21)
                tileMap.setTile(this.y / tileSize, this.x / tileSize, 1);
        }
        super.update();
    }

    /**
     * Affichage du personnage
     * @param g Graphics2D
     */
    public void draw(Graphics2D g) {
        if (ticks < 50 || invincibility % 2 == 0) {
            super.draw(g);
        }
    }

    /**
     * Collecter les oiseaux
     */
    public void collectedBirds() {
        nbBirds ++;
    }

    /**
     * Récuperer le nombre d'oiseaux collectés
     * @return Le nombre d'oiseau
     */
    public int getNbBirds() {
        return nbBirds;
    }

    /**
     * Faire perdre une vie au personnage
     */
    public void losingLife() {
        if (invincibility > 0)
            return;
        invincibility = 30;
        this.life = this.life - 1;
    }

    /**
     * On renvoie le nombre de vie du joueur
     * @return nombre de point de vie
     */
    public int getLife(){
        return life;
    }

    /**
     * On renvoit le nombre de ticks écoulés
     * @return nombre de ticks
     */
    public long getTicks() {
        return ticks;
    }

    /**
     * Récuperer le temps de partie du joueur
     * @return Le temps
     */
    public int getTime() {
        int seconds = 60 - (int) ((this.getTicks() / 30) % 60);
        return seconds;
    }
}
