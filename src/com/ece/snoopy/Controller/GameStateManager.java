package com.ece.snoopy.Controller;

import com.ece.snoopy.SoundFX.SoundFX;
import com.ece.snoopy.States.*;

import java.awt.*;

/*
    GESTION DES ETATS DE JEU
 */
public class GameStateManager {


    // Modèle des etats de jeu
    private GameState[] gameStates;

    // Etat de pause
    private boolean paused;
    private PauseState pauseState;

    // Les états de jeu
    public static final int NUM_STATES = 12;
    public static final int INTRO = 0;
    public static final int MENU = 1;
    public static final int LEVEL1 = 2;
    public static final int ENDLEVEL = 3;
    public static final int SAVEDGAME = 4;
    public static final int MDP = 5;
    public static final int LEVEL2 = 6;
    public static final int LEVEL3 = 7;
    public static final int LEVEL4 = 9;
    public static final int LEVEL5 = 10;
    public static final int LEVEL1AUTO = 11;
    public static final int GAMEOVER = 8;

    //Score des niveaux
    private int scoreLvl1;
    private int scoreLvl2;
    private int scoreLvl3;
    private int scoreLvl4;

    // Niveau actuel et précent
    private int currentState;
    private int previousState;

    /**
     * Constructeur
     */
    public GameStateManager() {
        //Initialise l'etat des niveaux
        scoreLvl1 = 0;
        scoreLvl2 = 0;
        scoreLvl3 = 0;
        scoreLvl4 = 0;
        // Initialise le son
        SoundFX.init();
        paused = false;
        pauseState = new PauseState(this);
        gameStates = new GameState[NUM_STATES];
        // On initialise l'état de départ par l'introduction
        setState(INTRO);
    }

    /**
     *
     * @param s Le score du niveau 1
     */
    public void setScoreLvl1(int s) {
        this.scoreLvl1 = s;
    }

    /**
     * Récupérer le score du niveau 1
     * @return int
     */
    public int getScoreLvl1() {
        return scoreLvl1;
    }

    public void setScoreLvl2(int s) {
        this.scoreLvl2 = s;
    }

    public int getScoreLvl2() {
        return this.scoreLvl2;
    }

    public void setScoreLvl3(int s) {
        this.scoreLvl3 = s;
    }

    public int getScoreLvl3() {
        return this.scoreLvl3;
    }

    public void setScoreLvl4(int s) {
        this.scoreLvl4 = s;
    }

    public int getScoreLvl4() {
        return this.scoreLvl4;
    }

    /**
     *  Modifier l'etat du jeu
     * @param i Récuperer le numéro d'etat du jeu
     */
    public void setState(int i) {
        previousState = currentState;
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

        else if(i == GAMEOVER) {
            gameStates[i] = new GameOverState(this);
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
        else if(i == LEVEL3) {
            gameStates[i] = new Level3State(this);
            gameStates[i].init();
        }
        else if(i == LEVEL4) {
            gameStates[i] = new Level4State(this);
            gameStates[i].init();
        }
        else if(i == LEVEL5) {
            gameStates[i] = new Level5State(this);
            gameStates[i].init();
        }
        else if(i == LEVEL1AUTO) {
            gameStates[i] = new AdvancedLevel1State(this);
            gameStates[i].init();
        }
    }

    public GameState getPreviousState() {
        return gameStates[previousState];
    }

    /**
     *  Permettre de mettre à jour l'etat entre chaque niveau
     */
    public void update() {
        if(paused) {
            pauseState.update();
        }
       else if(gameStates[currentState] != null) {
            gameStates[currentState].update();
        }
    }

    /**
     * Modifier l'etat de Pause
     * @param b Valeur de pause (true : en pause)
     */
    public void setPaused(boolean b) {
        paused = b;
    }

    /**
     * Récuperer l'etat de pause (permet de gérer le son dans le menu pause)
     * @return true en pause
     */
    public boolean getPaused() {
        return paused;
    }

    /**
     * Affichage graphique de chaque état
     * @param g Graphics2D
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
