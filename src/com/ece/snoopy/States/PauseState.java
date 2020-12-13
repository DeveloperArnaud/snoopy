package com.ece.snoopy.States;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Controller.Inputs;
import com.ece.snoopy.SoundFX.SoundFX;
import java.awt.*;

import static com.ece.snoopy.Controller.SavingState.saveState;


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
        Content.drawString(graphics2D,"PAUSE", 60, 30);
        Content.drawString(graphics2D,"Fleches: Deplacer", 20, 60);
        Content.drawString(graphics2D,"S: Sauvegarder", 20, 80);
        Content.drawString(graphics2D, "ESPACE: Action", 20, 100);
        Content.drawString(graphics2D, "F1: Retour menu", 20, 120);
    }

    /**
     * Intercepter les entrées de l'etat Pause
     */
    @Override
    public void handleInput() {
        if(Inputs.isPressed(Inputs.ESCAPE)) {
            gameStateManager.setPaused(false);
        }
        // Non fonctionnel (en cours de développement)
        if(Inputs.isPressed(Inputs.S)) {
            SoundFX.stop("snoopyStage2");
            gameStateManager.setPaused(true);
            GameState gameState = this.gameStateManager.getPreviousState();
            saveState(gameState);
        }

        if(Inputs.isPressed(Inputs.F1)){
            gameStateManager.setPaused(false);
            gameStateManager.setState(GameStateManager.MENU);
            SoundFX.stop("snoopyStage1");
            SoundFX.stop("snoopyStage5");
        }
    }


}
