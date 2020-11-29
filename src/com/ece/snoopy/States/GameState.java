package com.ece.snoopy.States;

import com.ece.snoopy.Controller.GameStateManager;

import java.awt.*;

/*
 * Cette classe abstraite
 * (Une interface aurait pu être utilisée aussi mais nous avons optés pour une classe abstraite pour le refactoring)
 * implementera les fonctions principales qui seront réutilisés pour les différents ecrans/niveaux.
 */

public abstract class GameState {

    protected GameStateManager gameStateManager;
    /**
     * Constructeur
     * @param gameStateManager GameStateManager
     */
    public GameState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    /**
     * Initialise l'etat des states
     */
    public abstract void init();

    /**
     * Mettre à jour l'état des states
     */
    public abstract void update();

    /**
     * Affichage de l'etat
     * @param graphics2D Graphics2D
     */
    public abstract void draw(Graphics2D graphics2D);

    /**
     * Intercepter les entrées utilisateurs
     */
    public abstract void handleInput();
}
