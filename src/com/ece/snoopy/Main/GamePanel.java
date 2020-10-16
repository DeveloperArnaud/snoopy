package com.ece.snoopy.Main;

// The GamePanel is the drawing canvas.
// It contains the game loop which
// keeps the game moving forward.
// This class is also the one that grabs key events.

import com.ece.snoopy.Manager.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    //Dimensions of the game
    public static final int WIDTH = 128;
    public static final int HEIGHT = 128;
    public static final int HEIGHT2 = HEIGHT + 16;
    public static final int SCALE = 3;

    //Game loop
    private Thread thread;
    private boolean isRunning;
    private final int fps = 30;
    private final int time = 1000 / fps;

    //Drawing
    private BufferedImage image;
    private Graphics2D graphics2D;

    //Game State Manager
    private GameStateManager gameStateManager;

    /**
     * Constructor
     */
    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT2 * SCALE));
        setFocusable(true);
        requestFocus();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {

    }
}
