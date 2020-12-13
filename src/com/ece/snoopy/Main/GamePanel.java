package com.ece.snoopy.Main;
import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Controller.Inputs;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    //Dimension de la fênetre de jeu
    public static final int WIDTH = 160;
    public static final int HEIGHT = 160;
    public static final int HEIGHT2 = HEIGHT + 16;
    public static final int SCALE = 3;

    //Gestion des perfomances du Thread (visionnage des ressources consommées par l'ordinateur)
    private Thread thread;
    private boolean isRunning;
    private final int fps = 30;
    private final int TARGET_TIME = 1000 / fps;

    //Palette graphique
    private BufferedImage image;
    private Graphics2D graphics2D;

    //Gestion des états de jeu
    private GameStateManager gameStateManager;

    /**
     * Constructeur
     */
    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT2 * SCALE));
        System.out.println("Largeur : " + WIDTH * SCALE +  " Hauteur : " + HEIGHT2 * SCALE );
        setFocusable(true);
        requestFocus();
    }

    /**
     * Initialisation de la fênetre de jeu
     */
    private void init() {
        isRunning = true;
        image = new BufferedImage(WIDTH, HEIGHT2,  1 );
        graphics2D = (Graphics2D) image.getGraphics();
        gameStateManager = new GameStateManager();
    }

    /**
     * Not used function
     * @param e KeyEvent
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Récuperer la touche appuyée par l'utilisateur
     * @param e KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
        Inputs.keySet(e.getKeyCode(), true);
    }

    /**
     *  Empêcher la progagation d'une touche à travers le jeu
     * @param e KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {
        Inputs.keySet(e.getKeyCode(), false);
    }

    /**
     * Notification du système de gestion des perfomances
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
     * Lancement du système de gestion des perfomances
     */
    @Override
    public void run() {
        init();

        long start;
        long elapsed;
        long wait;

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

    /**
     * Mettre à jour les états de jeu et les entrées utilisateurs afin de pas faire bugger le jeu
     */
    public void update() {
        gameStateManager.update();
        Inputs.update();
    }



    /**
     * Affichage des états
     */
    private void draw() {
        gameStateManager.draw(graphics2D);
    }

    /**
     *  Affichage du jeu sur l'ecran
     */
    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image,0, 0 , WIDTH * SCALE, HEIGHT2 * SCALE, null);
        g2.dispose();
    }
}
