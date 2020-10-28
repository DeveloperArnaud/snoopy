package com.ece.snoopy.States;

import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Controller.Inputs;
import com.ece.snoopy.Main.GamePanel;
import com.ece.snoopy.Map.TileMap;
import com.ece.snoopy.Model.Bird;
import com.ece.snoopy.Model.Player;
import com.ece.snoopy.SoundFX.SoundFX;
import com.ece.snoopy.UI.UI;

import java.awt.*;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Level1State extends GameState{
    /**
     * Player
     */
    private Player player;
    private TileMap tileMap;
    private UI ui;

    private ArrayList<Bird> birds;

    private int xsector;
    private int ysector;
    private int sectorSize;

    private boolean blockInput;
    private int eventTick;
    private ArrayList<Rectangle> boxes;
    private boolean eventGo;

    /**
     * @param gameStateManager
     */
    public Level1State(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {

        birds = new ArrayList<>();
        tileMap = new TileMap(16);
        tileMap.loadTiles("/Tilesets/testtileset.gif");
        tileMap.loadMap("/Maps/testmap.map");
        player = new Player(tileMap);
        ui = new UI(player, birds);

        generateBirds();

        player.setTilePosition(20, 20);

        sectorSize = GamePanel.WIDTH;
        xsector = player.getX() / sectorSize;
        ysector = player.getY() / sectorSize;
        tileMap.setInitPosition(-xsector * sectorSize, -ysector * sectorSize);

        SoundFX.loadSound("/SFX/snoopy-stage1.wav", "snoopyStage1");
        SoundFX.loadSound("/SFX/collect.wav", "collect");
        SoundFX.play("snoopyStage1");


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

        int ox = xsector;
        int oy = ysector;
        xsector = player.getX() / sectorSize;
        ysector = player.getY() / sectorSize;

        tileMap.setPosition(-xsector * sectorSize, -ysector * sectorSize);
        tileMap.update();


        if(tileMap.isMoving()) return;

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

    }

    /**
     * Managing the start of the game (to manage time, score etc..)
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
        bird1.setTilePosition(22,17);
        bird2 = new Bird(tileMap);
        bird2.setTilePosition(17,22);
        bird3 = new Bird(tileMap);
        bird3.setTilePosition(22,22);
        birds.add(bird);
        birds.add(bird1);
        birds.add(bird2);
        birds.add(bird3);


    }
    

    @Override
    public void draw(Graphics2D graphics2D) {
        tileMap.draw(graphics2D);
        player.draw(graphics2D);

        for(Bird bird : birds) {
            bird.draw(graphics2D);
        }

        ui.draw(graphics2D);
    }

    @Override
    public void handleInput() {

        if(blockInput) return;
        if(Inputs.isDown(Inputs.LEFT)) player.setLEFT();
        if(Inputs.isDown(Inputs.RIGHT)) player.setRIGHT();
        if(Inputs.isDown(Inputs.UP)) player.setUP();
        if(Inputs.isDown(Inputs.DOWN)) player.setDOWN();
    }
}
