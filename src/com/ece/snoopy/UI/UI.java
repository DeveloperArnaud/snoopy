package com.ece.snoopy.UI;
import com.ece.snoopy.Model.Player;

import java.awt.*;
import java.io.Serializable;


public class UI implements Serializable {

    private Player player;

    /**
     * Constructeur
     * @param p player
     */
    public UI(Player p) {
        this.player = p;

    }

    /**
     * Affichage de l'interface de jeu (Vie et Timer)
     * @param graphics2D graphics2D
     */
    public void draw(Graphics2D graphics2D) {
        int seconds = player.getTime();
        graphics2D.drawString( ""+seconds, 80, 10);
        graphics2D.drawString("Vie : " + player.getLife(), 5, 10);

    }

    public void drawSavedUI(Graphics2D graphics2D) {
        int seconds = player.getTimeSaved();
        graphics2D.drawString( ""+seconds, 80, 10);
        graphics2D.drawString("Vie : " + player.getLife(), 5, 10);
    }

}
