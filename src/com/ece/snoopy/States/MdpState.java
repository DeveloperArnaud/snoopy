package com.ece.snoopy.States;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Controller.Inputs;
import com.ece.snoopy.SoundFX.SoundFX;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MdpState extends GameState {

    //Image et mot de passe
    private BufferedImage bg;
    private String pwd;

    /**
     * Constructeur
     * @param gameStateManager GameStateManager
     */
    public MdpState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }


    @Override
    public void init() {
        bg = Content.BACKGROUNDMDP[0][0];
        SoundFX.loadSound("/SFX/menuoption.wav", "menuoption");
        SoundFX.loadSound("/SFX/collect.wav", "collect");
        pwd = "";
    }

    /**
     * Mettre à jour l'etat Mdp
     */
    @Override
    public void update() {
        handleInput();
    }

    /**
     * Affichage de l'etat Mdp
     * @param graphics2D Graphics2D
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(bg, 0, 0, null);
        Font font = new Font("Century Gothic", Font.BOLD, 11);
        graphics2D.setFont(font);
        graphics2D.drawString("Entrez le code du niveau", 15 , 20);
        String s = "";
        for (int i = 0; i < 8; i++) {
            if (i < pwd.length())
                s += pwd.charAt(i);
            else
                s += '*';
        }
        graphics2D.drawString(s, 60 , 50);
        graphics2D.drawString("Appuyez sur ENTREE", 30 , 130);
        graphics2D.drawString("pour le lancer", 48 , 150);
        graphics2D.drawString("ou ECHAP pour quitter", 28 , 170);
    }

    /**
     * Intercepter les entrées utilisateurs
     */
    @Override
    public void handleInput() {
        if(Inputs.isPressed(Inputs.ENTER)) {
            chooseLevel(pwd);
        }
        if(Inputs.isPressed(Inputs.NUMBER)) {
            SoundFX.play("menuoption");
            pwd += Inputs.numberKey();
        }
        if(Inputs.isPressed(Inputs.LETTER)) {
            SoundFX.play("menuoption");
            pwd += Inputs.letterKey();
        }
        if(Inputs.isPressed(Inputs.ESCAPE)) {
            gameStateManager.setState(GameStateManager.MENU);
        }
        if(Inputs.isPressed(Inputs.ERASE)) {
            if (pwd.length() > 0)
                pwd = pwd.substring(0, pwd.length() - 1);
        }
    }

    /**
     * Choix d'un niveau selon le code entré
     * @param l Code saisi par l'utilisateur
     */
    private void chooseLevel(String l) {
        if(l.equals("UN")) {
            gameStateManager.setScoreLvl1(0);
            gameStateManager.setScoreLvl2(0);
            gameStateManager.setScoreLvl3(0);
            gameStateManager.setScoreLvl4(0);
            SoundFX.stop("snoopyTitleScreen");
            gameStateManager.setState(GameStateManager.LEVEL1);
        }
        if(l.equals("DEUX")) {
            gameStateManager.setScoreLvl1(0);
            gameStateManager.setScoreLvl2(0);
            gameStateManager.setScoreLvl3(0);
            gameStateManager.setScoreLvl4(0);
            SoundFX.stop("snoopyTitleScreen");
            gameStateManager.setState(GameStateManager.LEVEL2);
        }
        if(l.equals("TROIS")) {
            gameStateManager.setScoreLvl1(0);
            gameStateManager.setScoreLvl2(0);
            gameStateManager.setScoreLvl3(0);
            gameStateManager.setScoreLvl4(0);
            SoundFX.stop("snoopyTitleScreen");
            gameStateManager.setState(GameStateManager.LEVEL3);
        }
        if(l.equals("QUATRE")) {
            gameStateManager.setScoreLvl1(0);
            gameStateManager.setScoreLvl2(0);
            gameStateManager.setScoreLvl3(0);
            gameStateManager.setScoreLvl4(0);
            SoundFX.stop("snoopyTitleScreen");
            gameStateManager.setState(GameStateManager.LEVEL4);
        }
        if(l.equals("CINQ")) {
            gameStateManager.setScoreLvl1(0);
            gameStateManager.setScoreLvl2(0);
            gameStateManager.setScoreLvl3(0);
            gameStateManager.setScoreLvl4(0);
            SoundFX.stop("snoopyTitleScreen");
            gameStateManager.setState(GameStateManager.LEVEL5);
        }
        else {
            pwd = "";
        }
    }
}
