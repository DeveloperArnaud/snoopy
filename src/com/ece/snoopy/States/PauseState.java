package com.ece.snoopy.States;

import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Controller.Inputs;
import com.ece.snoopy.Controller.SavingState;
import com.ece.snoopy.Map.Tile;
import com.ece.snoopy.Map.TileMap;
import com.ece.snoopy.Model.Bird;
import com.ece.snoopy.Model.Player;
import com.ece.snoopy.SoundFX.SoundFX;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.util.ArrayList;

import static com.ece.snoopy.Controller.SavingState.saveState;

public class PauseState extends GameState {

    private Player player;
    private TileMap tileMap;
    private ArrayList<Bird> birds;


    public PauseState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    public PauseState(GameStateManager gameStateManager, Player player, TileMap tileMap, ArrayList<Bird> birds) {
        super(gameStateManager);
        this.player = player;
        this.tileMap = tileMap;
        this.birds = birds;
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        handleInput();
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        Font font = new Font("Century Gothic", Font.PLAIN, 10);
        graphics2D.setFont(font);
        graphics2D.drawString("PAUSE", 50, 30);
        graphics2D.drawString("Flèches", 12, 60);
        graphics2D.drawString(": Déplacements", 52, 60);

        graphics2D.drawString("S", 12, 80);
        graphics2D.drawString(": Sauvegarder", 52, 80);

        graphics2D.drawString( "F1", 12, 100);
        graphics2D.drawString(": Retourner", 52, 100);
        graphics2D.drawString( "au menu", 60, 110);
    }

    @Override
    public void handleInput() {
        if(Inputs.isPressed(Inputs.ESCAPE)) {
            gameStateManager.setPaused(false);
            SoundFX.resumeLoop("snoopyStage1");
        }

        if(Inputs.isPressed(Inputs.F1)){
            gameStateManager.setPaused(false);
            gameStateManager.setState(GameStateManager.MENU);
        }
    }


}
