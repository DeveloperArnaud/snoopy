package com.ece.snoopy.States;

import com.ece.snoopy.Controller.Inputs;
import com.ece.snoopy.Main.GamePanel;
import com.ece.snoopy.Controller.GameStateManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/*
    INTRODUCTION DU JEU (Effet graphique)
 */
public class IntroState extends GameState{

    //Image et gestion d'effets
    private transient BufferedImage background;
    private int alpha;
    private int ticks;

    //Durée du fondu entrant
    private final int FADE_IN = 60;

    //Durée de l'intro
    private final int LENGTH = 60;

    //Durée du fondu sortant
    private final int FADE_OUT = 60;

    /**
     * Constructeur
     * @param gameStateManager GameStateManager
     */
    public IntroState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        ticks = 0;
        try {
            background = ImageIO.read(getClass().getResourceAsStream("/Intro/intro.jpg"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mettre à jour les effets
     */
    @Override
    public void update() {
        handleInput();
        ticks++;
        if(ticks < FADE_IN) {
            alpha = (int) (255 - 255 * (1.0 * ticks / FADE_IN));
            if(alpha < 0) alpha = 0;
        }
        if(ticks > FADE_IN + LENGTH) {
            alpha = (int) (255 * (1.0 * ticks - FADE_IN - LENGTH) / FADE_OUT);
            if(alpha > 255) alpha = 255;

        }

        if(ticks > FADE_IN + LENGTH + FADE_OUT) {
            gameStateManager.setState(GameStateManager.MENU);
        }
    }


    /**
     * Affichage de l'introduction
     * @param graphics2D Graphics2D
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2);
        graphics2D.drawImage(background, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2, null);
        graphics2D.setColor(new Color(0, 0, 0, alpha));
        graphics2D.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2);
    }

    /**
     * Intercepter les entrées
     */
    @Override
    public void handleInput() {
        if(Inputs.isPressed(Inputs.ENTER)) {
            gameStateManager.setState(GameStateManager.MENU);
        }
    }
}
