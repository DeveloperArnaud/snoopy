package com.ece.snoopy.States;

import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Controller.Inputs;
import com.ece.snoopy.Main.GamePanel;
import com.ece.snoopy.Model.Player;
import com.ece.snoopy.Model.Timer;

import java.awt.*;

public class EndState extends GameState {

    private Color color;
    private long ticks;
    private Player player;

    public EndState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        color = new Color(164,198,222);
        ticks = Timer.getTime();


    }

    @Override
    public void update() {
        handleInput();
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(color);

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
        }
        graphics2D.drawString( "Level " + lv + " cleared !", 20, 64);
    }

    @Override
    public void handleInput() {
        if(Inputs.isPressed(Inputs.ENTER)) {
            gameStateManager.setState(GameStateManager.MENU);
        }
    }
}
