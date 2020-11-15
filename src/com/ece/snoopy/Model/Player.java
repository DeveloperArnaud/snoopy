package com.ece.snoopy.Model;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Map.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

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
    public void setRIGHT() {
        super.setRight();
    }
    public void setUP() {
        super.setUp();
    }
    public void setDOWN() {
        super.setDown();
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
        life --;
    }
    public int getLife(){
        return life;
    }

    public long getTicks() {
        return ticks;
    }
}
