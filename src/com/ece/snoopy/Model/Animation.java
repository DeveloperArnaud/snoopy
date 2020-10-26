package com.ece.snoopy.Model;

import java.awt.image.BufferedImage;

public class Animation {

    private BufferedImage[] frames;
    private int currentFrame;
    private int numFrames;

    private int count;
    private int delay;

    private int timesPlayed;

    public Animation() {
        timesPlayed = 0;
    }

    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
        this.currentFrame = 0;
        this.count = 0;
        this.timesPlayed = 0;
        this.delay = 2;
        this.numFrames = this.frames.length;
    }

    public BufferedImage getFrames() {
        return frames[currentFrame];
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getNumFrames() {
        return numFrames;
    }

    public void setNumFrames(int numFrames) {
        this.numFrames = numFrames;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public boolean hasPlayedOnce() {
        return timesPlayed > 0;
    }

    public boolean hasPlayed(int timesPlayed) {
        return this.timesPlayed == timesPlayed;
    }

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
