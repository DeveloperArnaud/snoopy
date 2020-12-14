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
import java.io.*;
import java.util.ArrayList;



public class SavedGameState extends GameState {

    private static final long serialVersionUID = -4137127613771002199L;

    private Player player;
    private TileMap tileMap;
    private UI ui;
    private Ball ball;

    private ArrayList<Bird> birds;

    Level1State level1State;

    private boolean blockInput;
    private int eventTick;
    private ArrayList<Rectangle> boxes;
    private boolean eventGo;
    private boolean eventFinish;



    /**
     * @param gameStateManager
     */
    public SavedGameState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {

        try(FileInputStream fi = new FileInputStream(new File("C:/Users/arnau/testBirds"))) {
            ObjectInputStream oi = new ObjectInputStream(fi);

            GameState gameState = (GameState) oi.readObject();
            level1State = (Level1State) gameState;
            birds = new ArrayList<>();
            System.out.println(birds);
            tileMap = new TileMap(16);
            tileMap.loadTiles("/Tilesets/testtileset.gif");
            tileMap.loadMap("/Maps/level1.map");
            player = new Player(tileMap);
            ui = new UI(player);
            ball = new Ball(tileMap, 1);

            this.generateBirds();

            player.setTilePosition(level1State.getPlayer().getTilePositionY(), level1State.getPlayer().getTilePositionX());
            tileMap.setInitPosition(-256, -256);
            ball.setTilePosition(level1State.getBall().getTilePositionY(),level1State.getBall().getTilePositionX());
            System.out.println(level1State.getPlayer().getTime());

            SoundFX.loadSound("/SFX/snoopy-stage1.wav", "snoopyStage1");
            SoundFX.loadSound("/SFX/collect.wav", "collect");
            SoundFX.loadSound("/SFX/losinglife.wav", "losingLife");
            SoundFX.setVolume("snoopyStage1", -35);
            SoundFX.setVolume("losingLife", -30);
            SoundFX.setVolume("collect", -25);
            SoundFX.play("snoopyStage1");



            boxes = new ArrayList<>();
            eventGo = true;
            eventGo();


            oi.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

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
            SoundFX.play("losingLife");
            player.losingLife();
        }
        //1800*2 = 2min
        if(player.getTicks() == (1800 * 2)) {
            SoundFX.play("losingLife");
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
        ball.update();

        //Récupération des oiseaux
        for(int i = 0; i < level1State.getBirds().size(); i++) {
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
            SoundFX.play("losingLife");
        }

    }

    public void generateBirds() {
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
            //Data.setTime(player.getTicks());
            System.out.print(eventTick);
            gameStateManager.setState(GameStateManager.ENDLEVEL);
        }
    }


    @Override
    public void draw(Graphics2D graphics2D) {
        tileMap.draw(graphics2D);
        player.draw(graphics2D);
        ball.draw(graphics2D);




        for(Bird bird : birds) {
            bird.draw(graphics2D);
        }



        ui.draw(graphics2D);

        graphics2D.setColor(Color.BLACK);
        for(int i = 0; i < level1State.getBoxes().size(); i++) {
            graphics2D.fill(level1State.getBoxes()
                    .get(i));
        }
    }

    @Override
    public void handleInput() {
        if(Inputs.isPressed(Inputs.ESCAPE)){
            SoundFX.stop("snoopyStage1");
            gameStateManager.setPaused(true);
        }
        if(Inputs.isPressed(Inputs.S)) {
            SoundFX.stop("snoopyStage1");
            gameStateManager.setPaused(true);
            //saveState(player, tileMap, birds);

        }
        if(blockInput) return;
        if(Inputs.isDown(Inputs.LEFT)) player.setLEFT();
        if(Inputs.isDown(Inputs.RIGHT)) player.setRIGHT();
        if(Inputs.isDown(Inputs.UP)) player.setUP();
        if(Inputs.isDown(Inputs.DOWN)) player.setDOWN();
    }
}
