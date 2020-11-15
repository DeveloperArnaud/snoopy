package com.ece.snoopy.Main;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Launcher {

    public static JFrame game;
    double x = 0, y = 0,  velX = 2, velY = 2;

    public static void main(String[] args) {
        game = new JFrame("La revanche de Snoopy");

        game.add(new GamePanel());
        game.setResizable(false);
        game.pack();

        game.setLocationRelativeTo(null);
        game.setVisible(true);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
