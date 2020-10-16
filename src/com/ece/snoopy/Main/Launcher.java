package com.ece.snoopy.Main;

import javax.swing.*;

public class Launcher {

    public static void main(String[] args) {
        JFrame game = new JFrame("La revanche de Snoopy");

        game.add(new GamePanel());
        game.setResizable(false);
        game.pack();

        game.setLocationRelativeTo(null);
        game.setVisible(true);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
