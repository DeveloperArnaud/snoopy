package com.ece.snoopy.States;

import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Controller.Inputs;
import com.ece.snoopy.Main.GamePanel;
import com.ece.snoopy.Model.Player;
import com.ece.snoopy.Model.Timer;
import com.ece.snoopy.SoundFX.SoundFX;

import java.awt.*;

public class EndState extends GameState {

    private Color color;
    private Player player;

    public EndState(GameStateManager gameStateManager) {
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
        graphics2D.drawString("Temps final",25, 36);
        String lv = "1";
        GameState prev = this.gameStateManager.getPreviousState();
        if (prev instanceof Level1State) {
            Level1State lv1 = (Level1State)prev;
            player = lv1.getPlayer();
            int seconds = 60 - (int) ((player.getTicks() / 30) % 60);
            graphics2D.drawString( ""+seconds, 95, 36);
            graphics2D.drawString( "Score niveau : " + seconds * 100, 25, 56);
        }
        graphics2D.drawString( "Niveau " + lv + " terminé !", 25, 80);
        graphics2D.drawString( "ENTRER: CONTINUER", 5, 100);
        graphics2D.drawString( "ECHAP: MENU PRINCIPAL", 5, 110);
    }

    @Override
    public void handleInput() {
        if(Inputs.isPressed(Inputs.ESCAPE)) {
            gameStateManager.setState(GameStateManager.MENU);
            SoundFX.stop("snoopyStage1");
        }
        if(Inputs.isPressed(Inputs.ENTER)) {
            gameStateManager.setState(GameStateManager.LEVEL1);
        }
    }
}
