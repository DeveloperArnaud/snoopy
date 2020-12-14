package com.ece.snoopy.States;

import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Controller.Inputs;
import com.ece.snoopy.Main.GamePanel;
import com.ece.snoopy.Map.TileMap;
import com.ece.snoopy.Model.Ball;
import com.ece.snoopy.Model.Bird;
import com.ece.snoopy.Model.Objet;
import com.ece.snoopy.Model.Player;
import com.ece.snoopy.SoundFX.SoundFX;
import com.ece.snoopy.UI.UI;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;



public class SavedGameState extends GameState {

    //ID de serialisation (BufferedImage)
    private static final long serialVersionUID = -4137127613771002199L;

    //Composants
    private Player player;
    private TileMap tileMap;
    private UI ui;
    private Ball ball;

    //Les objets
    private ArrayList<Bird> birds;
    private ArrayList<Objet> objets;

    //Les states
    Level1State level1State;
    Level2State level2State;
    Level3State level3State;
    Level4State level4State;
    Level5State level5State;

    //Les evenements
    private boolean blockInput;
    private int eventTick;
    private ArrayList<Rectangle> boxes;
    private boolean eventGo;
    private boolean eventFinish;
    GameState gameState;


    /**
     * @param gameStateManager
     */
    public SavedGameState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {

        try(FileInputStream fi = new FileInputStream(new File("C:/Users/arnau/save1"))) {
            ObjectInputStream oi = new ObjectInputStream(fi);

            gameState = (GameState) oi.readObject();

            if(gameState instanceof Level1State) {
                SoundFX.stop("snoopyTitleScreen");
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
                System.out.println(level1State.getPlayer().getTilePositionX());
                player.setTilePosition(level1State.getPlayer().getTilePositionY(), level1State.getPlayer().getTilePositionX());
                tileMap.setInitPosition(-256, -256);
                ball.setTilePosition(level1State.getBall().getTilePositionY(), level1State.getBall().getTilePositionX());
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

            } else if(gameState instanceof Level2State) {
                SoundFX.stop("snoopyStage1");
                SoundFX.stop("snoopyTitleScreen");
                level2State = (Level2State) gameState;
                birds = new ArrayList<>();
                objets = new ArrayList<>();
                tileMap = new TileMap(16);
                tileMap.loadTiles("/Tilesets/testtileset.gif");
                tileMap.loadMap("/Maps/level2.map");
                player = new Player(tileMap);
                ui = new UI(player);
                ball = new Ball(tileMap, 1);

                generateBirds();
                generateObjets();

                player.setTilePosition(level2State.getPlayer().getTilePositionY(), level2State.getPlayer().getTilePositionX());
                System.out.println(level2State.getPlayer().getTilePositionY());
                tileMap.setInitPosition(-256, -256);
                ball.setTilePosition(level2State.getBall().getTilePositionY(), level2State.getBall().getTilePositionX());

                SoundFX.loadSound("/SFX/snoopyStage2.wav", "snoopyStage2");
                SoundFX.loadSound("/SFX/collect.wav", "collect");
                SoundFX.loadSound("/SFX/losinglife.wav", "losingLife");
                SoundFX.setVolume("snoopyStage2", -35);
                SoundFX.setVolume("losingLife", -30);
                SoundFX.setVolume("collect", -25);
                SoundFX.play("snoopyStage2");



                boxes = new ArrayList<>();
                eventGo = true;
                eventGo();

                oi.close();
            }

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
        if(gameState instanceof Level1State) {
            for (int i = 0; i < birds.size(); i++) {
                Bird bird = birds.get(i);
                bird.update();

                if (player.intersects(bird)) {
                    birds.remove(i);
                    i--;

                    player.collectedBirds();
                    SoundFX.play("collect");
                }
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

        if(gameState instanceof Level1State) {
            if (level1State.getBirds().get(0) != null) {
                birds.add(bird);
            } else if (level1State.getBirds().get(1) != null) {
                birds.add(bird1);
            } else if (level1State.getBirds().get(2) != null) {
                birds.add(bird2);
            } else if (level1State.getBirds().get(3) != null) {
                birds.add(bird3);
            } else {
                birds.remove(bird);
                birds.remove(bird1);
                birds.remove(bird2);
                birds.remove(bird3);

            }
        }

    }

    public void generateObjets() {
        Objet rocher1;
        Objet rocher2;
        Objet rocher3;
        Objet rocher4;
        Objet trap;
        Objet trap2;
        Objet trap3;
        Objet trap4;

        trap = new Objet(tileMap, Objet.TRAP);
        trap.setTilePosition(20,17);
        trap2 = new Objet(tileMap, Objet.TRAP);
        trap2.setTilePosition(20,24);
        trap3 = new Objet(tileMap, Objet.TRAP);
        trap3.setTilePosition(21,17);
        trap4 = new Objet(tileMap, Objet.TRAP);
        trap4.setTilePosition(21,24);
        rocher1 = new Objet(tileMap, Objet.ROCK);
        rocher1.setTilePosition(18,17);
        rocher2 = new Objet(tileMap, Objet.ROCK);
        rocher2.setTilePosition(23,17);
        rocher3 = new Objet(tileMap, Objet.ROCK);
        rocher3.setTilePosition(17,23);
        rocher4 = new Objet(tileMap, Objet.ROCK);
        rocher4.setTilePosition(24,23);
        objets.add(rocher1);
        objets.add(rocher2);
        objets.add(rocher3);
        objets.add(rocher4);
        objets.add(trap);
        objets.add(trap2);
        objets.add(trap3);
        objets.add(trap4);
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
        for(int i = 0; i < boxes.size(); i++) {
            graphics2D.fill(boxes
                    .get(i));
        }
    }

    @Override
    public void handleInput() {

        if(gameState instanceof Level1State) {
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
        } else if(gameState instanceof Level2State) {
            if(Inputs.isPressed(Inputs.ESCAPE)){
                SoundFX.stop("snoopyStage2");
                gameStateManager.setPaused(true);
            }
            if(!gameStateManager.getPaused()) {
                SoundFX.resumeLoop("snoopyStage2");
            }

            if(blockInput) return;
            if(Inputs.isDown(Inputs.LEFT)) player.setLEFT(objets, birds);
            if(Inputs.isDown(Inputs.RIGHT)) player.setRIGHT(objets, birds);
            if(Inputs.isDown(Inputs.UP)) player.setUP(objets, birds);
            if(Inputs.isDown(Inputs.DOWN)) player.setDOWN(objets, birds);
            if(Inputs.isPressed(Inputs.SPACE)) player.setAction();
        }
    }
}
