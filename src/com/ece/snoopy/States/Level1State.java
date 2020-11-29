package com.ece.snoopy.States;

import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Controller.Inputs;
import com.ece.snoopy.Main.GamePanel;
import com.ece.snoopy.Map.TileMap;
import com.ece.snoopy.Model.Ball;
import com.ece.snoopy.Model.Bird;
import com.ece.snoopy.Model.Player;
import com.ece.snoopy.SoundFX.SoundFX;
import com.ece.snoopy.UI.UI;

import java.awt.*;
import java.util.ArrayList;

/*
    NIVEAU 1

 */
public class Level1State extends GameState{

    //Composants du niveau 1
    private Player player;
    private TileMap tileMap;
    private UI ui;
    private Ball ball;
    private ArrayList<Bird> birds;

    //Gestion des évènements et des entrées
    private boolean blockInput;
    private int eventTick;
    private ArrayList<Rectangle> boxes;
    private boolean eventGo;
    private boolean eventFinish;


    /**
     * @param gameStateManager GameStateManager
     */
    public Level1State(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        birds = new ArrayList<>();
        tileMap = new TileMap(16);
        tileMap.loadTiles("/Tilesets/testtileset.gif");
        tileMap.loadMap("/Maps/level1.map");
        player = new Player(tileMap);
        ui = new UI(player);
        ball = new Ball(tileMap);

        generateBirds();

        player.setTilePosition(20, 20);
        tileMap.setInitPosition(-256, -256);

        SoundFX.loadSound("/SFX/snoopy-stage1.wav", "snoopyStage1");
        SoundFX.loadSound("/SFX/collect.wav", "collect");
        SoundFX.setVolume("snoopyStage1", -25);
        SoundFX.setVolume("collect", -25);
        SoundFX.play("snoopyStage1");


        boxes = new ArrayList<>();
        eventGo = true;
        eventGo();

    }

    /**
     * Mettre à jour les composants de l'etat 1 (Niveau 1)
     */
    @Override
    public void update() {
        handleInput();

        //Effet d'affichage
        if(eventGo) eventGo();
        if(eventFinish) eventFinish();

        // Si le personnage a 4 oiseaux, fin de la partie
        if(player.getNbBirds() == 4) {
            eventFinish = blockInput = true;
        }

        /* GESTION DU TEMPS */
        //1800 = 1min
        if(player.getTicks() == 1800) {
            player.losingLife();
        }
        //1800*2 = 2min
        if(player.getTicks() == (1800 * 2)) {
            player.losingLife();
        }
         //1800*3 = 3min
        if(player.getTicks() == (1800 * 3)) {
            player.losingLife();
            gameStateManager.setState(GameStateManager.GAMEOVER);
        }
        /* FIN BLOC GESTION DU TEMPS */

        // Si le personnage a 0 vie, retour au menu
        if(player.getLife() == 0 ) {
            SoundFX.stop("snoopyStage1");
            gameStateManager.setState(GameStateManager.MENU);
        }

        /*
        if(player.getLife() == -100) {
            eventFinish = blockInput = true;
            gameStateManager.setState(GameStateManager.MENU);
        }
        */

        tileMap.setPosition(-256, -256);
        tileMap.update();

        player.update();

        //Récupération des oiseaux
        for(int i = 0; i < birds.size(); i++) {
            Bird bird = birds.get(i);
            bird.update();

            if(player.intersects(bird)) {
                birds.remove(i);
                i--;

                player.collectedBirds();
                SoundFX.play("collect");
            }
        }

        // Pas encore implémentée
        if(player.intersects(ball)){
            player.losingLife();
        }

    }

    /**
     * Permet d'afficher un debut de partie avec un effet d'apparition
     */
    private void eventGo() {
        eventTick++;
        if(eventTick == 1) {
            boxes.clear();
            for (int i = 0; i < 9; i++) {
                boxes.add(new Rectangle(0, i * 16, GamePanel.WIDTH, 16));
            }
        }

        if(eventTick > 1 && eventTick < 32) {
            for(int i = 0; i < boxes.size(); i++) {
                Rectangle r = boxes.get(i);
                if(i % 2 == 0) {
                    r.x  -= 4;
                } else {
                    r.x += 4;
                }
            }
        }
        if(eventTick == 33) {
            boxes.clear();
            eventGo = false;
            eventTick = 0;
        }
    }


    /**
     * Permet d'afficher un fin de partie avec un effet de disparition
     */
    private void eventFinish() {
        eventTick++;
        if(eventTick == 1) {
            boxes.clear();
            for(int i = 0; i < 9; i++) {
                if(i % 2 == 0) boxes.add(new Rectangle(-128, i * 16, GamePanel.WIDTH, 16));
                else boxes.add(new Rectangle(128, i * 16, GamePanel.WIDTH, 16));
            }

        }
        if(eventTick > 1) {
            for(int i = 0; i < boxes.size(); i++) {
                Rectangle r = boxes.get(i);
                if(i % 2 == 0) {
                    if(r.x < 0) r.x += 4;
                }
                else {
                    if(r.x > 0) r.x -= 4;
                }
            }
        }
        if(eventTick > 33) {
            gameStateManager.setState(GameStateManager.ENDLEVEL);
        }
    }

    /**
     * Générer les oiseaux (ici losange) sur la map
     */
    private void generateBirds() {
        Bird bird;
        Bird bird1;
        Bird bird2;
        Bird bird3;

        bird = new Bird(tileMap);
        bird.setTilePosition(17,17);
        bird1 = new Bird(tileMap);
        bird1.setTilePosition(24,17);
        bird2 = new Bird(tileMap);
        bird2.setTilePosition(17,24);
        bird3 = new Bird(tileMap);
        bird3.setTilePosition(24,24);
        birds.add(bird);
        birds.add(bird1);
        birds.add(bird2);
        birds.add(bird3);


    }

    /**
     * Affichage du niveau 1 et de tous ces composants
     * @param graphics2D Graphics2D
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        tileMap.draw(graphics2D);
        player.draw(graphics2D);
        //ball.draw(graphics2D);

        System.out.println(player.getTicks());

        if(player.getTicks() < 90) {
            graphics2D.drawString("Niveau 1", 60, 40);
        }

        for(Bird bird : birds) {
            bird.draw(graphics2D);
        }

        ui.draw(graphics2D);

        graphics2D.setColor(Color.BLACK);
        for(int i = 0; i < boxes.size(); i++) {
            graphics2D.fill(boxes.get(i));
        }
    }

    @Override
    public void handleInput() {

        //Mettre en pause
        if(Inputs.isPressed(Inputs.ESCAPE)){
            SoundFX.stop("snoopyStage1");
            gameStateManager.setPaused(true);
        }

        if(!gameStateManager.getPaused()) {
            SoundFX.resumeLoop("snoopyStage1");
        }

        //Bloquer les entrées et figer le jeu
        if(blockInput) return;
        // Déplacements du personnage
        if(Inputs.isDown(Inputs.LEFT)) player.setLEFT();
        if(Inputs.isDown(Inputs.RIGHT)) player.setRIGHT();
        if(Inputs.isDown(Inputs.UP)) player.setUP();
        if(Inputs.isDown(Inputs.DOWN)) player.setDOWN();
    }

    /**
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public ArrayList<Bird> getBirds(){
        return birds;
    }

}
