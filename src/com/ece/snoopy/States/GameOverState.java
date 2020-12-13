package com.ece.snoopy.States;

import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Controller.Inputs;
import com.ece.snoopy.Main.GamePanel;
import com.ece.snoopy.SoundFX.SoundFX;

import java.awt.*;

public class GameOverState extends GameState {

    private Color color;

    /**
     * Constructeur
     * @param gameStateManager GameStateManager
     */
    public GameOverState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        color = new Color(164,198,222);
    }

    @Override
    public void update() {
        handleInput();
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(color);
        Font font = new Font("Century Gothic", Font.PLAIN, 11);
        graphics2D.setFont(font);
        graphics2D.fillRect(0,0, GamePanel.WIDTH, GamePanel.HEIGHT2);
        graphics2D.setColor(new Color(0,0,0));
        graphics2D.drawString("GAME OVER",50, 50);

        graphics2D.drawString( "ENTRER: RECOMMENCER", 25, 100);
        graphics2D.drawString( "ECHAP: MENU PRINCIPAL", 25, 120);
    }

    @Override
    public void handleInput() {
        if(Inputs.isPressed(Inputs.ESCAPE)) {
            gameStateManager.setState(GameStateManager.MENU);
            SoundFX.stop("snoopyStage1");
            SoundFX.stop("snoopyStage2");
            SoundFX.stop("snoopyStage3");
        }

        if(Inputs.isPressed(Inputs.ENTER)) {
            if (gameStateManager.getPreviousState() instanceof Level1State) {
                gameStateManager.setState(GameStateManager.LEVEL1);
                SoundFX.resumeLoop("snoopyStage1");
            }
            else if(gameStateManager.getPreviousState() instanceof Level2State) {
                gameStateManager.setState(GameStateManager.LEVEL2);
                SoundFX.resumeLoop("snoopyStage2");
            }
            else if(gameStateManager.getPreviousState() instanceof Level3State) {
                gameStateManager.setState(GameStateManager.LEVEL3);
                SoundFX.resumeLoop("snoopyStage2");
            }
            else if(gameStateManager.getPreviousState() instanceof Level4State) {
                gameStateManager.setState(GameStateManager.LEVEL4);
                SoundFX.resumeLoop("snoopyStage2");
            }
            else {
                gameStateManager.setState(GameStateManager.LEVEL5);
                SoundFX.resumeLoop("snoopyStage3");
            }
        }
    }
}
