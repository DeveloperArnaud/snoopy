package com.ece.snoopy.Controller;

import com.ece.snoopy.SoundFX.SoundFX;
import com.ece.snoopy.States.*;

import java.awt.*;

public class GameStateManager {


    private GameState[] gameStates;

    public static final int NUM_STATES = 4;
    public static final int INTRO = 0;
    public static final int MENU = 1;
    public static final int LEVEL1 = 2;
    public static final int LEVEL2 = 4;
    public static final int ENDLEVEL = 3;

    private int currentState;
    private int previousState;

    /**
     * Constructeur
     */
    public GameStateManager() {
        // Initialisation du son
        SoundFX.init();
        gameStates = new GameState[NUM_STATES];
        setState(INTRO);
    }

    /**
     *
     * @param i
     */
    public void setState(int i) {
        previousState = currentState;
        stateToNull(previousState);
        currentState = i;

        if(i == INTRO) {
            gameStates[i] = new IntroState(this);
            gameStates[i].init();
        } else if(i == MENU) {
            gameStates[i] = new MenuState(this);
            gameStates[i].init();
        }
        else if(i == LEVEL1) {
            gameStates[i] = new Level1State(this);
            gameStates[i].init();
        }

        else if(i == ENDLEVEL) {
            gameStates[i] = new EndState(this);
            gameStates[i].init();
        }
    }

    public void stateToNull(int previousState) {
        gameStates[previousState] = null;
    }

    /**
     *
     */
    public void update() {
        if(gameStates[currentState] != null) {
            gameStates[currentState].update();
        }
    }

    /**
     * Affichage des états
     * @param g
     */
    public void draw(Graphics2D g) {
        if(gameStates[currentState] != null) {
            gameStates[currentState].draw(g);
        }
    }
    
}
