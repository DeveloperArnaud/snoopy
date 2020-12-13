package com.ece.snoopy.States;

import com.ece.snoopy.Controller.Content;
import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Controller.Inputs;
import com.ece.snoopy.Main.GamePanel;
import com.ece.snoopy.Map.TileMap;
import com.ece.snoopy.Model.*;
import com.ece.snoopy.SoundFX.SoundFX;
import com.ece.snoopy.UI.UI;

import java.awt.*;
import java.util.ArrayList;

public class Level5State extends GameState {

    //Composants du niveau 5
    private Player player;
    private TileMap tileMap;
    private UI ui;
    private Ball ball;
    private Ball ball2;
    private Mechant mechant;
    private ArrayList<Bird> birds;
    private ArrayList<Objet> objets;

    //Gestion des évènements et des entrées
    private boolean blockInput;
    private int eventTick;
    private ArrayList<Rectangle> boxes;
    private boolean eventGo;
    private boolean eventFinish;


    /**
     * Constructeur
     * @param gameStateManager GameStateManager
     */
    public Level5State(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        birds = new ArrayList<>();
        objets = new ArrayList<>();
        tileMap = new TileMap(16);
        tileMap.loadTiles("/Tilesets/testtileset.gif");
        tileMap.loadMap("/Maps/level3.map");
        player = new Player(tileMap);
        ui = new UI(player, birds);
        ball = new Ball(tileMap, 2);
        ball2 = new Ball(tileMap, 2);
        mechant = new Mechant(tileMap);

        generateBirds();
        generateObjets();

        player.setTilePosition(20, 20);
        ball.setTilePosition(20,23);
        ball2.setTilePosition(23,20);
        mechant.setTilePosition(20,23);
        tileMap.setInitPosition(-256, -256);

        SoundFX.loadSound("/SFX/snoopyStage5.wav", "snoopyStage5");
        SoundFX.loadSound("/SFX/collect.wav", "collect");
        SoundFX.loadSound("/SFX/losinglife.wav", "losingLife");
        SoundFX.setVolume("losingLife", -20);
        SoundFX.setVolume("snoopyStage5", -35);
        SoundFX.setVolume("collect", -25);
        SoundFX.play("snoopyStage5");

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
        ball.update();
        ball2.update();
        if (player.getTicks() % 40 == 29)
            mechant.computeDirection(player);
        if (player.intersects(mechant)) {
            player.losingLife();
            SoundFX.play("losingLife");
        }
        mechant.update();

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
            gameStateManager.setState(GameStateManager.GAMEOVER);
        }
        if(player.getLife() == 0 ) {
            SoundFX.stop("snoopyStage5");
            gameStateManager.setState(GameStateManager.GAMEOVER);
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

        if(player.intersects(ball) || player.intersects(ball2)){
            player.losingLife();
            SoundFX.play("losingLife");
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
        Objet trap1;
        Objet trap2;
        Objet trap3;
        Objet trap4;
        Objet trap5;
        Objet trap6;
        Objet trap7;
        Objet trap8;

        trap1 = new Objet(tileMap, Objet.TRAP);
        trap1.setTilePosition(19,17);
        trap2 = new Objet(tileMap, Objet.TRAP);
        trap2.setTilePosition(17,19);
        trap3 = new Objet(tileMap, Objet.TRAP);
        trap3.setTilePosition(22,17);
        trap4 = new Objet(tileMap, Objet.TRAP);
        trap4.setTilePosition(24,19);
        trap5 = new Objet(tileMap, Objet.TRAP);
        trap5.setTilePosition(17,22);
        trap6 = new Objet(tileMap, Objet.TRAP);
        trap6.setTilePosition(19,24);
        trap7 = new Objet(tileMap, Objet.TRAP);
        trap7.setTilePosition(22,24);
        trap8 = new Objet(tileMap, Objet.TRAP);
        trap8.setTilePosition(24,22);
        rocher1 = new Objet(tileMap, Objet.ROCK);
        rocher1.setTilePosition(18,18);
        rocher2 = new Objet(tileMap, Objet.ROCK);
        rocher2.setTilePosition(23,23);
        rocher3 = new Objet(tileMap, Objet.ROCK);
        rocher3.setTilePosition(18,23);
        rocher4 = new Objet(tileMap, Objet.ROCK);
        rocher4.setTilePosition(23,18);
        objets.add(rocher1);
        objets.add(rocher2);
        objets.add(rocher3);
        objets.add(rocher4);
        objets.add(trap1);
        objets.add(trap2);
        objets.add(trap3);
        objets.add(trap4);
        objets.add(trap5);
        objets.add(trap6);
        objets.add(trap7);
        objets.add(trap8);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        tileMap.draw(graphics2D);


        mechant.draw(graphics2D);

        player.draw(graphics2D);

        if(player.getTicks() < 90) {
            Content.drawString(graphics2D, "Niveau 5",50, 70);
        }

        for (Objet rock : objets)
            rock.draw(graphics2D);

        for(Bird bird : birds)
            bird.draw(graphics2D);

        ui.draw(graphics2D);

        graphics2D.setColor(Color.BLACK);
        for(int i = 0; i < boxes.size(); i++) {
            graphics2D.fill(boxes.get(i));
        }
        ball.draw(graphics2D);
        ball2.draw(graphics2D);
    }

    @Override
    public void handleInput() {

        if(Inputs.isPressed(Inputs.ESCAPE)){
            SoundFX.stop("snoopyStage5");
            gameStateManager.setPaused(true);
        }
        if(!gameStateManager.getPaused()) {
            SoundFX.resumeLoop("snoopyStage5");
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

    public TileMap getTileMap() {
        return tileMap;
    }

    public ArrayList<Bird> getBirds(){
        return birds;
    }
}

