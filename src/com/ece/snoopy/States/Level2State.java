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
import java.util.ArrayList;

import static com.ece.snoopy.Controller.SavingState.saveState;
/*
    NIVEAU 2

 */
public class Level2State extends GameState{

    //Composants du niveau 2
    private Player player;
    private TileMap tileMap;
    private UI ui;
    private Ball ball;
    private ArrayList<Bird> birds;
    private ArrayList<Objet> objets;


    private boolean blockInput;
    private int eventTick;
    private ArrayList<Rectangle> boxes;
    private boolean eventGo;
    private boolean eventFinish;


    /**
     * @param gameStateManager GameStateManager
     */
    public Level2State(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        birds = new ArrayList<>();
        objets = new ArrayList<>();
        tileMap = new TileMap(16);
        tileMap.loadTiles("/Tilesets/testtileset.gif");
        tileMap.loadMap("/Maps/level2.map");
        player = new Player(tileMap);
        ui = new UI(player);
        ball = new Ball(tileMap);

        generateBirds();
        generateObjets();

        player.setTilePosition(20, 20);
        //ball.setTilePosition(20,20);
        tileMap.setInitPosition(-256, -256);

        SoundFX.loadSound("/SFX/snoopyStage2.wav", "snoopyStage2");
        SoundFX.loadSound("/SFX/collect.wav", "collect");
        SoundFX.setVolume("snoopyStage2", -25);
        SoundFX.setVolume("collect", -25);
        SoundFX.play("snoopyStage2");



        boxes = new ArrayList<>();
        eventGo = true;
        eventGo();

    }

    @Override
    public void update() {
        handleInput();

        if(eventGo) eventGo();
        if(eventFinish) eventFinish();

        tileMap.setPosition(-256, -256);
        tileMap.update();

        player.update();

        if(player.getNbBirds() == 4) {
            eventFinish = blockInput = true;
        }

        if(player.getTicks() == 1800) {
            player.losingLife();
        }

        if(player.getTicks() == (1800 * 2)) {
            player.losingLife();
        }


        if(player.getTicks() == (1800 * 3)) {
            player.losingLife();
        }

        if(player.getLife() == 0 ) {
            SoundFX.stop("snoopyStage2");
            gameStateManager.setState(GameStateManager.MENU);
        }

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

        if(player.intersects(ball)){
            player.losingLife();
        }

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

    private void generateObjets() {
        Objet rocher1;
        Objet rocher2;
        Objet rocher3;
        Objet rocher4;
        Objet trap;
        Objet trap2;

        trap = new Objet(tileMap, Objet.TRAP);
        trap.setTilePosition(20,17);
        trap2 = new Objet(tileMap, Objet.TRAP);
        trap2.setTilePosition(20,24);
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
    }

    @Override
    public void draw(Graphics2D graphics2D) {



        tileMap.draw(graphics2D);

        if(player.getTicks() < 90) {
            graphics2D.drawString("Niveau 2", 60, 40);
        }

        player.draw(graphics2D);
        //ball.draw(graphics2D);



        for (Objet rock : objets)
            rock.draw(graphics2D);

        for(Bird bird : birds)
            bird.draw(graphics2D);

        ui.draw(graphics2D);

        graphics2D.setColor(Color.BLACK);
        for(int i = 0; i < boxes.size(); i++) {
            graphics2D.fill(boxes.get(i));
        }
    }

    @Override
    public void handleInput() {

        if(Inputs.isPressed(Inputs.ESCAPE)){
            SoundFX.stop("snoopyStage2");
            gameStateManager.setPaused(true);
        }
        if(!gameStateManager.getPaused()) {
            SoundFX.resumeLoop("snoopyStage2");
        }
        if(Inputs.isPressed(Inputs.S)) {
            SoundFX.stop("snoopyStage2");
            gameStateManager.setPaused(true);
            saveState(player, tileMap, birds);

        }

        if(blockInput) return;
        if(Inputs.isDown(Inputs.LEFT)) player.setLEFT(objets, birds);
        if(Inputs.isDown(Inputs.RIGHT)) player.setRIGHT(objets, birds);
        if(Inputs.isDown(Inputs.UP)) player.setUP(objets, birds);
        if(Inputs.isDown(Inputs.DOWN)) player.setDOWN(objets, birds);
        if(Inputs.isPressed(Inputs.SPACE)) player.setAction();
    }

    public Player getPlayer() {
        return player;
    }
}
