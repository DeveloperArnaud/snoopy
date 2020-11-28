package com.ece.snoopy.Model;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Map.TileMap;
import com.ece.snoopy.SoundFX.SoundFX;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Model {

    private BufferedImage[] downSprites;
    private BufferedImage[] leftSprites;
    private BufferedImage[] rightSprites;
    private BufferedImage[] upSprites;

    private final int DOWN = 0;
    private final int UP = 3;
    private final int LEFT = 1;
    private final int RIGHT = 2;
    private long ticks;
    private int nbBirds;
    private int life = 3;


    public Player(TileMap tileMap) {

        super(tileMap);
        width = 16;
        height = 16;
        cwidth = 12;
        cheight = 12;

        moveSpeed = 2;

        downSprites = Content.PLAYER[0];
        leftSprites = Content.PLAYER[1];
        rightSprites = Content.PLAYER[2];
        upSprites = Content.PLAYER[3];

        animation.setFrames(downSprites);
        animation.setDelay(10);
        SoundFX.loadSound("/SFX/destroyed.wav", "destroyed");
        SoundFX.setVolume("destroyed", -15);

    }

    public void update() {

        ticks++;


        if(down) {
            if(currentAnimation != DOWN) {
                setAnimation(DOWN, downSprites, 10);
            }
        }
        if(left) {
            if(currentAnimation != LEFT) {
                setAnimation(LEFT, leftSprites, 10);
            }
        }

        if(right) {
            if(currentAnimation != RIGHT) {
                setAnimation(RIGHT, rightSprites, 10);
            }
        }

        if(up) {
            if(currentAnimation != UP) {
                setAnimation(UP, upSprites, 10);
            }
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
    public void setLEFT(ArrayList<Objet> objets) {
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
                    if (obj.getType() == Objet.ROCK && !obj.goLeft())
                        return;
                }
            }
        }
        super.setLeft();
    }
    public void setRIGHT() {
        super.setRight();
    }
    public void setRIGHT(ArrayList<Objet> objets) {
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
                    if (obj.getType() == Objet.ROCK && !obj.goRight())
                        return;
                }
            }
        }
        super.setRight();
    }
    public void setUP() {
        super.setUp();
    }
    public void setUP(ArrayList<Objet> objets) {
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
                    if (obj.getType() == Objet.ROCK && !obj.goUp())
                        return;
                }
            }
        }
        super.setUp();
    }
    public void setDOWN() {
        super.setDown();
    }
    public void setDOWN(ArrayList<Objet> objets) {
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
                    if (obj.getType() == Objet.ROCK && !obj.goDown())
                        return;
                }
            }
        }
        super.setDown();
    }

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


    public void draw(Graphics2D g) {
        super.draw(g);
    }

    public void collectedBirds() {
        nbBirds ++;
    }

    public int getNbBirds() {
        return nbBirds;
    }

    public void losingLife() {
        this.life = this.life - 1;
    }
    public int getLife(){
        return life;
    }

    public long getTicks() {
        return ticks;
    }

    public int getTime() {
        int seconds = 60 - (int) ((this.getTicks() / 30) % 60);
        return seconds;
    }
}
