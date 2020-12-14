package com.ece.snoopy.Model;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Map.TileMap;
import com.ece.snoopy.SoundFX.SoundFX;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AutoPlayer extends Model {

    //Sprite de déplacement du personnage
    private BufferedImage[] downSprites;
    private BufferedImage[] leftSprites;
    private BufferedImage[] rightSprites;
    private BufferedImage[] upSprites;

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

    /**
     * Mettre à jour la sprite d'animation de direction en fonction de la direction effectué (ex : DIRECTION DROITE = SPRITE ANIMATION DROITE)
     */
    //https://fr.wikipedia.org/wiki/Algorithme_de_Dijkstra#:~:text=En%20th%C3%A9orie%20des%20graphes%2C%20l,probl%C3%A8me%20du%20plus%20court%20chemin.

    private Map<Integer, Integer> initPath(int x, int y) {
        Map<Integer, Integer> distances = new HashMap<>();
        for (int i = offsetNbTileY * 8; i < (offsetNbTileY + heightMap + 1) * 8; i++)
            distances.put(i, Integer.MAX_VALUE - 1000);
        distances.put(x + 8 * y, 0);
        return distances;
    }

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

    private void majDistance(Integer s1, Integer s2, Map<Integer, Integer> distances, Map<Integer, Integer> prede, ArrayList<Objet> objets) {
        //if (s1 < offsetNbTileX * widthMap || s2 < offsetNbTileX * widthMap || s1 > (offsetNbTileY + heightMap + 1) * widthMap || s2 > (offsetNbTileY + heightMap + 1) * widthMap)
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

    private void setAnimation(int i, BufferedImage[] frame, int delay) {
        currentAnimation = i;
        animation.setFrames(frame);
        animation.setDelay(delay);

    }

    public void setLEFT() {
        super.setLeft();
    }

    public void setLEFT(ArrayList<Objet> objets, ArrayList<Bird> birds) {
        if (moving)
            return;
        for (Objet obj : objets) {
            rowTile = obj.getY() / tileSize;
            colTile = obj.getX() / tileSize;
            if (obj.getType() == Objet.TRAP) {
                if (colTile == x / tileSize - 1 && y / tileSize == rowTile) {
                    this.life = 0;
                    super.setLeft();
                    objets.remove(obj);
                    return;
                }
            }
            if (y / tileSize == rowTile) {
                if (x / tileSize - 1 == colTile) {
                    if (obj.getType() == Objet.ROCK && !obj.goLeft(objets, birds))
                        return;
                }
            }
        }
        super.setLeft();
    }
    public void setRIGHT() {
        super.setRight();
    }

    public void setRIGHT(ArrayList<Objet> objets, ArrayList<Bird> birds) {
        if (moving)
            return;
        for (Objet obj : objets) {
            rowTile = obj.getY() / tileSize;
            colTile = obj.getX() / tileSize;
            if (obj.getType() == Objet.TRAP) {
                if (colTile == x / tileSize + 1 && y / tileSize == rowTile) {
                    this.life = 0;
                    super.setRight();
                    objets.remove(obj);
                    return;
                }
            }
            if (y / tileSize == rowTile) {
                if (x / tileSize + 1 == colTile) {
                    if (obj.getType() == Objet.ROCK && !obj.goRight(objets, birds))
                        return;
                }
            }
        }
        super.setRight();
    }
    public void setUP() {
        super.setUp();
    }

    public void setUP(ArrayList<Objet> objets, ArrayList<Bird> birds) {
        if (moving)
            return;
        for (Objet obj : objets) {
            rowTile = obj.getY() / tileSize;
            colTile = obj.getX() / tileSize;
            if (obj.getType() == Objet.TRAP) {
                if (colTile == x / tileSize && y / tileSize - 1 == rowTile) {
                    this.life = 0;
                    super.setUp();
                    objets.remove(obj);
                    return;
                }
            }
            if (x / tileSize == colTile) {
                if (y / tileSize - 1 == rowTile) {

                    if (obj.getType() == Objet.ROCK && !obj.goUp(objets, birds))
                        return;
                }
            }
        }
        super.setUp();
    }
    public void setDOWN() {
        super.setDown();
    }

    public void setDOWN(ArrayList<Objet> objets, ArrayList<Bird> birds) {
        if (moving)
            return;
        for (Objet obj : objets) {
            rowTile = obj.getY() / tileSize;
            colTile = obj.getX() / tileSize;
            if (obj.getType() == Objet.TRAP) {
                if (colTile == x / tileSize && y / tileSize + 1 == rowTile) {
                    this.life = 0;
                    super.setDown();
                    objets.remove(obj);
                    return;
                }
            }
            if (x / tileSize == colTile) {
                if (y / tileSize + 1 == rowTile) {
                    if (obj.getType() == Objet.ROCK && !obj.goDown(objets, birds))
                        return;
                }
            }
        }
        super.setDown();
    }

    /**
     * Verifie en fonction de la direction du personnage si une case est cassable ( si index = 21, cassable)
     */
    public void setAction() {
        if(currentAnimation == UP && tileMap.getIndex(rowTile - 1, colTile) == 21) {
            SoundFX.play("destroyed");
            tileMap.setTile(rowTile - 1, colTile, 1);
        }

        if(currentAnimation == DOWN && tileMap.getIndex(rowTile + 1, colTile) == 21) {
            SoundFX.play("destroyed");
            tileMap.setTile(rowTile + 1, colTile, 1);

        }

        if(currentAnimation == LEFT && tileMap.getIndex(rowTile, colTile - 1) == 21) {
            SoundFX.play("destroyed");
            tileMap.setTile(rowTile , colTile - 1, 1);

        }

        if(currentAnimation == RIGHT && tileMap.getIndex(rowTile, colTile + 1) == 21) {
            SoundFX.play("destroyed");
            tileMap.setTile(rowTile, colTile  + 1, 1);

        }
    }

    /**
     * Affichage du personnage
     * @param g Graphics2D
     */
    public void draw(Graphics2D g) {
        if (ticks < 100 || invincibility % 2 == 0) {
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

    public int getLife(){
        return life;
    }

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
