package com.ece.snoopy.States;

import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Controller.Inputs;
import com.ece.snoopy.SoundFX.SoundFX;
import java.awt.*;


public class PauseState extends GameState {

    /**
     * Constructeur
     *
     * @param gameStateManager GameStateManager
     */
    public PauseState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        handleInput();
    }

    /**
     * Affichage de l'etat pause
     * @param graphics2D Graphics2D
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        Font font = new Font("Century Gothic", Font.PLAIN, 10);
        graphics2D.setFont(font);
        graphics2D.drawString("PAUSE", 60, 30);
        graphics2D.drawString("Flèches: Déplacements", 20, 60);
        graphics2D.drawString("S : Sauvegarder", 20, 80);
        graphics2D.drawString( "ESPACE: Action", 20, 100);
        graphics2D.drawString( "F1: Retourner au menu", 20, 120);
    }

    /**
     * Intercepter les entrées de l'etat Pause
     */
    @Override
    public void handleInput() {
        if(Inputs.isPressed(Inputs.ESCAPE)) {
            gameStateManager.setPaused(false);
        }

        if(Inputs.isPressed(Inputs.F1)){
            gameStateManager.setPaused(false);
            gameStateManager.setState(GameStateManager.MENU);
            SoundFX.stop("snoopyStage1");
        }
    }


}
