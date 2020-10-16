package com.ece.snoopy.States;

import java.awt.*;

/**
 * This abstract class
 * (interface could be used too but it needs an constructor to manage the state of each display) will
 * implements the basics functions
 * that each classes
 * could use without
 * needing to recode them such as init, update, draw and handleInput.
 */

public abstract class GameState {

    /**
     *
     */
    public GameState() {}

    /**
     * Function to initialize the state
     */
    public abstract void init();

    /**
     * Function to update the state
     */
    public abstract void update();

    /**
     * Function to draw on the Frame
     * @param graphics2D
     */
    public abstract void draw(Graphics2D graphics2D);

    /**
     * Function to get user inputs
     */
    public abstract void handleInput();
}
