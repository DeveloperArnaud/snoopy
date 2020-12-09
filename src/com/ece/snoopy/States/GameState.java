package com.ece.snoopy.States;

import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Model.Player;

import java.awt.*;
import java.io.Serializable;

/*
 * Cette classe abstraite
 * (Une interface aurait pu être utilisée aussi mais nous avons optés pour une classe abstraite pour le refactoring)
 * implementera les fonctions principales qui seront réutilisés pour les différents ecrans/niveaux.
 */

public abstract class GameState implements Serializable{

    protected GameStateManager gameStateManager;
    protected int positionXPlayer;
    protected int positionYPlayer;

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

    /**
     *
     * @return Les coordonnées de placement du personnage pour la sauvegarde
     */
    public int getPositionXPlayer(Player player) {
        positionXPlayer = (int) ((player.getXdest() / 16) - 0.5 + 1);
        return positionXPlayer;

    }

    public int getPositionYPlayer(Player player) {
        positionYPlayer = (int) ((player.getYdest() / 16) - 0.5 + 1);
        return positionYPlayer;
    }

    public abstract String getPathMap();

    public abstract String[] getPathSound();
}
