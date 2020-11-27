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

public class Level2State extends GameState{
    /**
     * Player
     */
    private Player player;
    private TileMap tileMap;
    private UI ui;
    private Ball ball;

    private ArrayList<Bird> birds;

    private ArrayList<Objet> objets;

    private int xsector;
    private int ysector;
    private int sectorSize;

    private boolean blockInput;
    private int eventTick;
    private ArrayList<Rectangle> boxes;
    private boolean eventGo;
    private boolean eventFinish;


    /**
     * @param gameStateManager
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
        ui = new UI(player, birds);
        ball = new Ball(tileMap);

        generateBirds();
        generateObjets();

        player.setTilePosition(20, 20);
        //ball.setTilePosition(20,20);

        sectorSize = GamePanel.WIDTH;
        xsector = player.getX() / sectorSize;
        ysector = player.getY() / sectorSize;
        tileMap.setInitPosition(-256, -256);

        SoundFX.loadSound("/SFX/snoopy-stage1.wav", "snoopyStage1");
        SoundFX.loadSound("/SFX/collect.wav", "collect");
        //SoundFX.play("snoopyStage1");


        boxes = new ArrayList<Rectangle>();
        eventGo = true;
        eventGo();

    }

    /**
     * Updating components
     */
    @Override
    public void update() {
        handleInput();

        if(eventGo) eventGo();
        if(eventFinish) eventFinish();

        if(player.getNbBirds() == 4) {
            eventFinish = blockInput = true;
        }

        int ox = xsector;
        int oy = ysector;
        xsector = player.getX() / sectorSize;
        ysector = player.getY() / sectorSize;

        tileMap.setPosition(-256, -256);
        tileMap.update();

        player.update();

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

    /**
     * Managing the end of the party, rectangle effect will make appear the start game screen
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
     * Managing the end of the party, rectangle effect will make appear the end screen
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
            //Data.setTime(player.getTicks());
            System.out.print(eventTick);
            gameStateManager.setState(GameStateManager.ENDLEVEL);
        }
    }

    /**
     * Generating birds on the map (need to be modified)
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

    private void generateObjets() {
        Objet rocher1;
        Objet rocher2;
        Objet rocher3;
        Objet rocher4;
        Objet speed;

        speed = new Objet(tileMap, Objet.SPEED);
        speed.setTilePosition(19,20);
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
        objets.add(speed);
    }

    @Override
    public void draw(Graphics2D graphics2D) {



        tileMap.draw(graphics2D);

        if(player.getTime() == 59) {
            graphics2D.drawString("Niveau 2", 54, 54);
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
            SoundFX.stop("snoopyStage1");
            gameStateManager.setPaused(true);
        }
        if(Inputs.isPressed(Inputs.S)) {
            SoundFX.stop("snoopyStage1");
            gameStateManager.setPaused(true);
            saveState(player, tileMap, birds);

        }

        if(blockInput) return;
        if(Inputs.isDown(Inputs.LEFT)) player.setLEFT(objets);
        if(Inputs.isDown(Inputs.RIGHT)) player.setRIGHT(objets);
        if(Inputs.isDown(Inputs.UP)) player.setUP(objets);
        if(Inputs.isDown(Inputs.DOWN)) player.setDOWN(objets);
    }

    public Player getPlayer() {
        return player;
    }

}
