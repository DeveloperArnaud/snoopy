package com.ece.snoopy.States;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Controller.Inputs;
import com.ece.snoopy.SoundFX.SoundFX;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MdpState extends GameState {
    private BufferedImage bg;
    private BufferedImage cursor;

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
        cursor = Content.CURSOR[0][0];
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
        graphics2D.drawString("Entrez le code", 25 , 10);
        graphics2D.drawString("du niveau", 35 , 30);
        String s = "";
        for (int i = 0; i < 8; i++) {
            if (i < pwd.length())
                s += pwd.charAt(i);
            else
                s += '*';
        }
        graphics2D.drawString(s, 40 , 50);
        graphics2D.drawString("Appuyez sur entrée", 10 , 60);
        graphics2D.drawString("pour le lancer", 28 , 80);
        graphics2D.drawString("ou ECHAP pour quitter", 3 , 115);
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
            gameStateManager.setState(GameStateManager.LEVEL1);
        }
        else {
            pwd = "";
        }
    }
}
