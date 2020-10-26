package com.ece.snoopy.States;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Controller.Inputs;
import com.ece.snoopy.SoundFX.SoundFX;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuState extends GameState {

    private BufferedImage bg;
    private BufferedImage cursor;

    private int currentOption = 0;
    private String[] options = {
            "Commencer",
            "Quitter"
    };

    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        bg = Content.BACKGROUNDMENU[0][0];
        cursor = Content.CURSOR[0][0];
        SoundFX.loadSound("/SFX/menuoption.wav", "menuoption");
        SoundFX.loadSound("/SFX/collect.wav", "collect");

    }

    @Override
    public void update() {
        handleInput();
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(bg, 0, 0, null);
        Font font = new Font("Century Gothic", Font.PLAIN, 11);
        graphics2D.setFont(font);
        graphics2D.drawString("La Revanche de Snoopy", 5 , 50);
        graphics2D.drawString(options[0], 40 , 100);
        graphics2D.drawString(options[1], 40 , 116);
        if(currentOption == 0) {

            graphics2D.drawImage(cursor, 25, 87, null);

        }

        else if(currentOption == 1){

            graphics2D.drawImage(cursor, 25, 103, null);
        }
    }

    @Override
    public void handleInput() {
        if(Inputs.isPressed(Inputs.DOWN) && currentOption < options.length - 1) {
            SoundFX.play("menuoption");
            currentOption ++;
        }

        if(Inputs.isPressed(Inputs.UP) && currentOption > 0) {
            SoundFX.play("menuoption");
            currentOption--;
        }
        if(Inputs.isPressed(Inputs.ENTER)) {
            chooseOptions();
        }

    }

    private void chooseOptions() {
        if(currentOption == 0) {
            gameStateManager.setState(GameStateManager.LEVEL1);
        }

        if(currentOption == 1 ) {
            SoundFX.play("collect");
            System.exit(0);

        }
    }
}
