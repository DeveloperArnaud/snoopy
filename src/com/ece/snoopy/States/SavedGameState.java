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

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.ece.snoopy.Controller.SavingState.loadMap;
import static com.ece.snoopy.Controller.SavingState.saveState;

public class SavedGameState extends GameState{

    private Player player;
    private TileMap tileMap;
    private UI ui;
    private Ball ball;

    private ArrayList<Bird> birds;

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
    public SavedGameState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        String line = null;
        String [] object = null;
        try(BufferedReader br = new BufferedReader(new FileReader("C:/Users/arnau/testBirds.txt"))) {

            while((line = br.readLine()) != null) {
                object = line.split(",");
                System.out.println(object[0]);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        /*
        birds = new ArrayList<>();
        tileMap.loadTiles("/Tilesets/testtileset.gif");
        tileMap = loadMap("C:/Users/arnau/testBirds.txt");
        //tileMap.loadMap("/Maps/templateMap.map");
        player = new Player(tileMap);
        ui = new UI(player, birds);
        ball = new Ball(tileMap);

        generateBirds();

        player.setTilePosition(20, 20);
        ball.setTilePosition(20,20);

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
        */

    }

    @Override
    public void update() {
        handleInput();

        if(eventGo) eventGo();
        if(eventFinish) eventFinish();

        if(player.getNbBirds() == 4) {
            eventFinish = blockInput = true;
        }

        if(player.getTime() == 1) {
            eventFinish = blockInput = true;
            gameStateManager.setState(GameStateManager.LEVEL1);
        }

        int ox = xsector;
        int oy = ysector;
        xsector = player.getX() / sectorSize;
        ysector = player.getY() / sectorSize;

        tileMap.setPosition(-xsector * sectorSize, -ysector * sectorSize);
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
        ball.draw(graphics2D);




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
        if(Inputs.isDown(Inputs.LEFT)) player.setLEFT();
        if(Inputs.isDown(Inputs.RIGHT)) player.setRIGHT();
        if(Inputs.isDown(Inputs.UP)) player.setUP();
        if(Inputs.isDown(Inputs.DOWN)) player.setDOWN();
    }
}
