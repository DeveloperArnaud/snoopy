package com.ece.snoopy.States;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Controller.Inputs;
import com.ece.snoopy.SoundFX.SoundFX;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MdpState extends GameState {
    private BufferedImage bg;

    private String pwd;

    /**
     * @param gameStateManager
     */
    public MdpState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }


    @Override
    public void init() {
        bg = Content.BACKGROUNDMENU[0][0];
        SoundFX.loadSound("/SFX/menuoption.wav", "menuoption");
        SoundFX.loadSound("/SFX/collect.wav", "collect");
        pwd = "";
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
        graphics2D.drawString("Entrez le code du niveau", 20 , 20);
        String s = "";
        for (int i = 0; i < 8; i++) {
            if (i < pwd.length())
                s += pwd.charAt(i);
            else
                s += '*';
        }
        graphics2D.drawString(s, 60 , 50);
        graphics2D.drawString("Appuyez sur ENTREE", 30 , 80);
        graphics2D.drawString("pour le lancer", 50 , 100);
        graphics2D.drawString("ou ECHAP pour quitter", 28 , 120);
    }

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

    private void chooseLevel(String l) {
        if(l.equals("UN")) {
            SoundFX.stop("snoopyTitleScreen");
            gameStateManager.setState(GameStateManager.LEVEL1);
        }
        if(l.equals("DEUX")) {
            SoundFX.stop("snoopyTitleScreen");
            gameStateManager.setState(GameStateManager.LEVEL2);
        }
        else {
            pwd = "";
        }
    }
}
