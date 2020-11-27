package com.ece.snoopy.Main;

// The GamePanel is the drawing canvas.
// It contains the game loop which
// keeps the game moving forward.
// This class is also the one that grabs key events.

import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Controller.Inputs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    //Dimensions of the game
    public static final int WIDTH = 160;
    public static final int HEIGHT = 144;
    public static final int HEIGHT2 = HEIGHT + 16;
    public static final int SCALE = 3;

    //Game loop
    private Thread thread;
    private boolean isRunning;
    private final int fps = 30;
    private final int TARGET_TIME = 1000 / fps;

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

    /**
     * Getting the key pressed through the states
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        Inputs.keySet(e.getKeyCode(), true);
    }

    /**
     *  Avoiding the propagation key through the states
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        Inputs.keySet(e.getKeyCode(), false);
    }

    /**
     *  Managing the game loop (perfomances)
     */
    public void addNotify() {
        super.addNotify();
        if(thread == null) {
            addKeyListener(this);
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Game loop
     */
    @Override
    public void run() {

        init();

        long start;
        long elapsed;
        long wait;

        // game loop
        while(isRunning) {

            start = System.nanoTime();

            update();
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;

            wait = TARGET_TIME - elapsed / 1000000;
            if(wait < 0) wait = TARGET_TIME;

            try {
                Thread.sleep(wait);
            }
            catch(Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void update() {
        gameStateManager.update();
        Inputs.update();
    }

    private void init() {
        isRunning = true;
        image = new BufferedImage(WIDTH, HEIGHT2,  1 );
        graphics2D = (Graphics2D) image.getGraphics();
        gameStateManager = new GameStateManager();
    }

    /**
     * Draw the states
     */
    private void draw() {
        gameStateManager.draw(graphics2D);
    }

    /**
     *  Display the game
     */
    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image,0, 0 , WIDTH * SCALE, HEIGHT2 * SCALE, null);
        g2.dispose();
    }
}
