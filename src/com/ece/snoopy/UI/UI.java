package com.ece.snoopy.UI;
import com.ece.snoopy.Main.GamePanel;
import com.ece.snoopy.Model.AutoPlayer;
import com.ece.snoopy.Model.Player;
import org.w3c.dom.css.Rect;

import java.awt.*;


public class UI {

    private Player player;
    private AutoPlayer autoPlayer;

    /**
     * Constructeur
     * @param p player
     */
    public UI(Player p) {
        this.player = p;
        autoPlayer = null;
    }
    public UI(AutoPlayer p) {
        this.autoPlayer = p;
        player = null;
    }

    /**
     * Affichage de l'interface de jeu (Vie et Timer)
     * @param graphics2D graphics2D
     */
    public void draw(Graphics2D graphics2D) {
        int seconds = 0;
        if (player != null)
            seconds = player.getTime();
        else
            seconds = autoPlayer.getTime();
        graphics2D.setColor(Color.WHITE);
        Rectangle r = new Rectangle(79, 0, 14, 12);
        graphics2D.fill(r);
        r = new Rectangle(4, 0, 32, 12);
        graphics2D.fill(r);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString( ""+seconds, 80, 10);
        if (player != null)
            graphics2D.drawString("Vie : " + player.getLife(), 5, 10);
        else
            graphics2D.drawString("Vie : " + autoPlayer.getLife(), 5, 10);

    }

}
