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
        graphics2D.drawString("Temps final",40, 36);

    }

    @Override
    public void handleInput() {
        if(Inputs.isPressed(Inputs.ENTER)) {
            gameStateManager.setState(GameStateManager.MENU);
        }
    }
}
