package com.ece.snoopy.Controller;

import com.ece.snoopy.SoundFX.SoundFX;
import com.ece.snoopy.States.*;

import java.awt.*;

public class GameStateManager {



    private GameState[] gameStates;

    private boolean paused;
    private PauseState pauseState;

    public static final int NUM_STATES = 7;
    public static final int INTRO = 0;
    public static final int MENU = 1;
    public static final int LEVEL1 = 2;
    public static final int ENDLEVEL = 3;
    public static final int SAVEDGAME = 4;
    public static final int MDP = 5;
    public static final int LEVEL2 = 6;

    private int scoreLvl1;
    private int scoreLvl2;

    private int currentState;
    private int previousState;

    /**
     * Constructeur
     */
    public GameStateManager() {
        scoreLvl1 = 0;
        scoreLvl2 = 0;
        // Initialisation du son
        SoundFX.init();
        paused = false;
        pauseState = new PauseState(this);
        gameStates = new GameState[NUM_STATES];
        setState(INTRO);
    }

    public void setScoreLvl1(int s) {
        this.scoreLvl1 = s;
    }

    public int getScoreLvl1() {
        return scoreLvl1;
    }

    public void setScoreLvl2(int s) {
        this.scoreLvl2 = s;
    }

    public int getScoreLvl2() {
        return this.scoreLvl2;
    }

    /**
     *
     * @param i
     */
    public void setState(int i) {
        previousState = currentState;
        //stateToNull(previousState);
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
        else if(i == SAVEDGAME) {
            gameStates[i] = new SavedGameState(this);
            gameStates[i].init();
        }

        else if(i == ENDLEVEL) {
            gameStates[i] = new EndState(this);
            gameStates[i].init();
        }
        else if(i == MDP) {
            gameStates[i] = new MdpState(this);
            gameStates[i].init();
        }
        else if(i == LEVEL2) {
            gameStates[i] = new Level2State(this);
            gameStates[i].init();
        }
    }

    public GameState getPreviousState() {
        return gameStates[previousState];
    }

    public void stateToNull(int previousState) {
        gameStates[previousState] = null;
    }

    /**
     *
     */
    public void update() {
        if(paused) {
            pauseState.update();
        }
       else if(gameStates[currentState] != null) {
            gameStates[currentState].update();
        }
    }

    public void setPaused(boolean b) {
        paused = b;
    }

    public boolean getPaused() {
        return paused;
    }
    /**
     * Affichage des états
     * @param g
     */
    public void draw(Graphics2D g) {
        if(paused) {
            pauseState.draw(g);
        }
        else if(gameStates[currentState] != null) {
            gameStates[currentState].draw(g);
        }
    }


}
