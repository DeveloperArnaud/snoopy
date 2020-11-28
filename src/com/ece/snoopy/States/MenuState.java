package com.ece.snoopy.States;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Controller.Inputs;
import com.ece.snoopy.SoundFX.SoundFX;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

public class MenuState extends GameState {

    private BufferedImage bg;
    private BufferedImage cursor;

    private int currentOption = 0;
    private String[] options = {
            "Commencer",
            "Mot de passe",
            "Charger une partie",
            "Quitter",

    };

    private ArrayList<String> optionList = new ArrayList<>();

    /**
     *
     * @param gameStateManager
     */
    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        bg = Content.BACKGROUNDMENU[0][0];
        cursor = Content.CURSOR[0][0];
        optionList.add("Commencer");
        optionList.add("Charger une partie");
        optionList.add("Mot de passe");
        optionList.add("Quitter");
        File f = new File("C:/Users/arnau/testBirds.txt");
        if(!f.exists()) {
            optionList.remove(optionList.get(1));
        }
        SoundFX.loadSound("/SFX/snoopyTitleScreen3.wav", "snoopyTitleScreen");
        SoundFX.loadSound("/SFX/menuoption.wav", "menuoption");
        SoundFX.loadSound("/SFX/collect.wav", "collect");
        SoundFX.setVolume("snoopyTitleScreen", -20);
        SoundFX.setVolume("menuoption", -25);
        SoundFX.play("snoopyTitleScreen");
;
    }

    @Override
    public void update() {
        handleInput();
    }

    /**
     *
     * @param graphics2D
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(bg, 0, 0, null);
        Font font = new Font("Century Gothic", Font.PLAIN, 11);
        graphics2D.setFont(font);
        graphics2D.drawString("La Revanche de Snoopy", 25 , 40);

        if(optionList.size() < 4 ) {
            graphics2D.drawString(optionList.get(0), 50, 74);
            graphics2D.drawString(optionList.get(1), 50, 90);
            graphics2D.drawString(optionList.get(2), 50, 106);

            if (currentOption == 0) {

                graphics2D.drawImage(cursor, 25, 62, null);

            } else if (currentOption == 1) {

                graphics2D.drawImage(cursor, 25, 78, null);
            } else if (currentOption == 2) {

                graphics2D.drawImage(cursor, 25, 94, null);
            }
        } else {
            graphics2D.drawString(optionList.get(0), 50, 74);
            graphics2D.drawString(optionList.get(1), 50, 90);
            graphics2D.drawString(optionList.get(2), 50, 106);
            graphics2D.drawString(optionList.get(3), 50, 122);


            if (currentOption == 0) {

                graphics2D.drawImage(cursor, 25, 62, null);

            } else if (currentOption == 1) {

                graphics2D.drawImage(cursor, 25, 78, null);
            } else if (currentOption == 2) {

                graphics2D.drawImage(cursor, 25, 94, null);
            } else if (currentOption == 3) {

                graphics2D.drawImage(cursor, 25, 110, null);
            }
        }
    }

    /**
     * Gestion des inputs du state
     */
    @Override
    public void handleInput() {
        if(Inputs.isPressed(Inputs.DOWN) && currentOption < optionList.size() - 1) {
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
            SoundFX.stop("snoopyTitleScreen");
            gameStateManager.setState(GameStateManager.LEVEL1);
        }

        if(currentOption == 1 ) {
            gameStateManager.setState(GameStateManager.MDP);
        }
        if(optionList.size() < 4 ) {
            if (currentOption == 2) {
                SoundFX.play("collect");
                System.exit(0);

            }
        } else {
            if (currentOption == 1) {
                SoundFX.stop("snoopyTitleScreen");
                gameStateManager.setState(GameStateManager.SAVEDGAME);
            }

            if(currentOption == 2 ) {
                gameStateManager.setState(GameStateManager.MDP);
            }

            if(currentOption == 3) {
                SoundFX.play("collect");
                System.exit(0);

            }
        }

        if(currentOption == 3) {
            SoundFX.play("collect");
            System.exit(0);

        }
    }
}
