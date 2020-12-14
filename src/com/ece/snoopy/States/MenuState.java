package com.ece.snoopy.States;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Controller.Inputs;
import com.ece.snoopy.SoundFX.SoundFX;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class MenuState extends GameState {

    private BufferedImage bg;
    private BufferedImage cursor;
    private int currentOption = 0;
    private ArrayList<String> optionList = new ArrayList<>();

    /**
     * Constucteur
     * @param gameStateManager GameStateManager
     */
    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        bg = Content.BACKGROUNDMENU[0][0];
        cursor = Content.CURSOR[0][0];
        optionList.add("Commencer");
        optionList.add("Jeu avancé");
        optionList.add("Charger une partie");
        optionList.add("Mot de passe");
        optionList.add("Quitter");
        File f = new File("C:/Users/arnau/testBirds");
        if(!f.exists()) {
            optionList.remove(optionList.get(2));
        }
        SoundFX.loadSound("/SFX/snoopyTitleScreen3.wav", "snoopyTitleScreen");
        SoundFX.loadSound("/SFX/menuoption.wav", "menuoption");
        SoundFX.loadSound("/SFX/collect.wav", "collect");
        SoundFX.setVolume("snoopyTitleScreen", -35);
        SoundFX.setVolume("menuoption", -25);
        SoundFX.play("snoopyTitleScreen");
    }

    /**
     * Mettre à jour l'etat Menu
     */
    @Override
    public void update() {
        handleInput();
    }

    /**
     * Affichage de l'etat Menu (adapté pour la sauvegarde)
     * @param graphics2D Graphics2D
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(bg, 0, 0, null);
        Font font = new Font("Century Gothic", Font.BOLD, 11);
        graphics2D.setFont(font);
        graphics2D.drawString("La Revanche de Snoopy", 14, 55);

        if (optionList.size() < 5) {
            graphics2D.drawString(optionList.get(0), 50, 124);
            graphics2D.drawString(optionList.get(1), 50, 140);
            graphics2D.drawString(optionList.get(2), 50, 156);
            graphics2D.drawString(optionList.get(3), 50, 172);

            if (currentOption == 0) {
                graphics2D.drawImage(cursor, 25, 112, null);
            } else if (currentOption == 1) {
                graphics2D.drawImage(cursor, 25, 128, null);
            } else if (currentOption == 2) {
                graphics2D.drawImage(cursor, 25, 144, null);
            } else if (currentOption == 3) {
                graphics2D.drawImage(cursor, 25, 160, null);
            }
            } else {
                graphics2D.drawString(optionList.get(0), 50, 112);
                graphics2D.drawString(optionList.get(1), 50, 127);
                graphics2D.drawString(optionList.get(2), 50, 143);
                graphics2D.drawString(optionList.get(3), 50, 159);
                graphics2D.drawString(optionList.get(4), 50, 175);
                if (currentOption == 0) {
                    graphics2D.drawImage(cursor, 25, 102, null);
                } else if (currentOption == 1) {
                    graphics2D.drawImage(cursor, 25, 117, null);
                } else if (currentOption == 2) {
                    graphics2D.drawImage(cursor, 25, 133, null);
                } else if (currentOption == 3) {
                    graphics2D.drawImage(cursor, 25, 149, null);
                } else if (currentOption == 4) {
                    graphics2D.drawImage(cursor, 25, 165, null);
                }
            }
    }

    /**
     * Gestion des entrées du menu
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

    /**
     * Récuperer l'option choisie
     */
    private void chooseOptions() {
        if(optionList.size() < 5 ) {
            if(currentOption == 0) {
                SoundFX.stop("snoopyTitleScreen");
                gameStateManager.setState(GameStateManager.LEVEL1);
            }
            if(currentOption == 1 ) {
                SoundFX.stop("snoopyTitleScreen");
                gameStateManager.setState(GameStateManager.LEVEL1AUTO);
            }
            if(currentOption == 2 ) {
                gameStateManager.setState(GameStateManager.MDP);
            }
            if (currentOption == 3) {
                SoundFX.play("collect");
                System.exit(0);
            }
        } else {
            if(currentOption == 0) {
                SoundFX.stop("snoopyTitleScreen");
                gameStateManager.setState(GameStateManager.LEVEL1);
            }
            if(currentOption == 1 ) {
                SoundFX.stop("snoopyTitleScreen");
                gameStateManager.setState(GameStateManager.LEVEL1AUTO);
            }
            if(currentOption == 2 ) {
                gameStateManager.setState(GameStateManager.SAVEDGAME);
            }
            if (currentOption == 3) {
                SoundFX.stop("snoopyTitleScreen");
                gameStateManager.setState(GameStateManager.MDP);
            }
            if(currentOption == 4) {
                SoundFX.play("collect");
                System.exit(0);

            }
        }
    }
}