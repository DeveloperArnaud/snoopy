
package com.ece.snoopy.UI;
import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Main.GamePanel;
import com.ece.snoopy.Model.AutoPlayer;
import com.ece.snoopy.Model.Player;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class UI {

    private int yoffset;
    private Player player;
    private BufferedImage bar;
    private BufferedImage bird;
    private BufferedImage heart;
    private BufferedImage heart2;
    private BufferedImage heart3;
    private ArrayList<BufferedImage> hearts = new ArrayList<>();
    private AutoPlayer autoPlayer;

    /**
     * Constructeur
     * @param p player
     */
    public UI(Player p) {
        this.player = p;
        autoPlayer = null;
        yoffset = GamePanel.HEIGHT;
        bar = Content.BAR[0][0];
        bird = Content.BIRD[0][0];
        heart = Content.HEART[0][0];
        heart2 = Content.HEART[0][0];
        heart3 = Content.HEART[0][0];
        hearts.add(heart);
        hearts.add(heart2);
        hearts.add(heart3);
    }
    public UI(AutoPlayer p) {
        this.autoPlayer = p;
        player = null;
        yoffset = GamePanel.HEIGHT;
        bar = Content.BAR[0][0];
        bird = Content.BIRD[0][0];
        heart = Content.HEART[0][0];
        heart2 = Content.HEART[0][0];
        heart3 = Content.HEART[0][0];
        hearts.add(heart);
        hearts.add(heart2);
        hearts.add(heart3);
    }

    /**
     * Affichage de l'interface de jeu (Vie et Timer)
     * @param graphics2D graphics2D
     */
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(bar, 0, yoffset, null);
        if(player != null) {
            graphics2D.fillRect(8, yoffset + 6, player.getNbBirds() * 16, 4);
        } else {
            graphics2D.fillRect(8, yoffset + 6, autoPlayer.getNbBirds() * 16, 4);
        }
        graphics2D.drawImage(bird, 80, yoffset, null);
        if(player != null) {
            if (player.getLife() == 3) {
                graphics2D.drawImage(hearts.get(0), 119, yoffset + 2, null);
                graphics2D.drawImage(hearts.get(1), 132, yoffset + 2, null);
                graphics2D.drawImage(hearts.get(2), 145, yoffset + 2, null);

            } else if (player.getLife() == 2) {
                graphics2D.drawImage(hearts.get(0), 119, yoffset + 2, null);
                graphics2D.drawImage(hearts.get(1), 132, yoffset + 2, null);

            } else if (player.getLife() == 1) {

                graphics2D.drawImage(hearts.get(0), 119, yoffset + 2, null);
            }
        } else {
            if (autoPlayer.getLife() == 3) {
                graphics2D.drawImage(hearts.get(0), 119, yoffset + 2, null);
                graphics2D.drawImage(hearts.get(1), 132, yoffset + 2, null);
                graphics2D.drawImage(hearts.get(2), 145, yoffset + 2, null);

            } else if (autoPlayer.getLife() == 2) {
                graphics2D.drawImage(hearts.get(0), 119, yoffset + 2, null);
                graphics2D.drawImage(hearts.get(1), 132, yoffset + 2, null);

            } else if (autoPlayer.getLife() == 1) {

                graphics2D.drawImage(hearts.get(0), 119, yoffset + 2, null);
            }
        }

        int seconds;
        if (player != null)
            seconds = player.getTime();
        else
            seconds = autoPlayer.getTime();

        Content.drawString(graphics2D,""+seconds, 80, 4);


    }

}