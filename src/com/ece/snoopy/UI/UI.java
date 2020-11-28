package com.ece.snoopy.UI;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Model.Bird;
import com.ece.snoopy.Model.Player;

import java.awt.*;
import java.util.ArrayList;

public class UI {

    private Player player;
    private int nbBirds;


    public UI(Player p, ArrayList<Bird> birds) {
        this.player = p;
        this.nbBirds = birds.size();

    }

    public void draw(Graphics2D graphics2D) {

        int seconds = player.getTime();

        graphics2D.drawString( ""+seconds, 80, 10);
        graphics2D.drawString("Vie : " + player.getLife(), 5, 10);

    }

}
