package com.ece.snoopy.States;

import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Controller.Inputs;
import com.ece.snoopy.Main.GamePanel;
import com.ece.snoopy.Map.TileMap;
import com.ece.snoopy.Model.Player;
import com.ece.snoopy.SoundFX.SoundFX;

import java.awt.*;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Level1State extends GameState{
    /**
     * Player
     */
    private Player player;
    private TileMap tileMap;

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
        SoundFX.loadSound("/SFX/snoopy-stage1.wav", "snoopyStage1");
        tileMap = new TileMap(16);
        tileMap.loadTiles("/Tilesets/testtileset.gif");
        tileMap.loadMap("/Maps/testmap.map");
        player = new Player(tileMap);

        player.setTilePosition(17, 17);

        sectorSize = GamePanel.WIDTH;
        xsector = player.getX() / sectorSize;
        ysector = player.getY() / sectorSize;
        tileMap.setInitPosition(-xsector * sectorSize, -ysector * sectorSize);

        boxes = new ArrayList<Rectangle>();
        eventGo = true;
        eventGo();
        SoundFX.play("snoopyStage1");
    }

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
    

    @Override
    public void draw(Graphics2D graphics2D) {
        tileMap.draw(graphics2D);
        player.draw(graphics2D);
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
