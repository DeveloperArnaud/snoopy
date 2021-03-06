package com.ece.snoopy.Model;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Animation implements Serializable {

    private transient BufferedImage[] frames;
    private int currentFrame;
    private int numFrames;

    private int count;
    private int delay;

    private int timesPlayed;

    /**
     * Constructeur
     */
    public Animation() {
        timesPlayed = 0;
    }

    /**
     * On donne une animation à un élement
     * @param frames les images de l'animation
     */
    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
        this.currentFrame = 0;
        this.count = 0;
        this.timesPlayed = 0;
        this.delay = 2;
        this.numFrames = this.frames.length;
    }

    /**+
     * On renvoie la frame
     * @return l'image de la frame
     */
    public BufferedImage getFrames() {
        return frames[currentFrame];
    }

    /**
     *  Délai d'animation entre chaque frame du sprite
     * @param delay int
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }

    /**
     * Mettre à jour l'animation (Rafraîchir les frames du personnage animé)
     */
    public void update() {
        if(this.delay == -1 ) return;
        count ++;
        if(count == delay) {
            currentFrame ++;
            count = 0;
        }
        if(currentFrame == numFrames) {
            currentFrame = 0;
            timesPlayed++;
        }
    }
}
