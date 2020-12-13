package com.ece.snoopy.States;

import com.ece.snoopy.Controller.GameStateManager;
import com.ece.snoopy.Controller.Inputs;
import com.ece.snoopy.Main.GamePanel;
import com.ece.snoopy.Model.Player;
import com.ece.snoopy.SoundFX.SoundFX;

import java.awt.*;
/*
    ECRAN DE FIN DE PARTIE
 */
public class EndState extends GameState {

    //Graphisme et Joueur
    private Color color;
    private Player player;

    //Gestion des états de jeu
    private GameStateManager gameStateManager;

    /**
     * Constructeur
     * @param gameStateManager GameStateManager
     */
    public EndState(GameStateManager gameStateManager) {
        super(gameStateManager);
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void init() {
        color = new Color(164,198,222);
    }

    @Override
    public void update() {
        handleInput();
    }

    /**
     * Affichage de l'etat de fin de jeu
     * @param graphics2D Graphics2D
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(color);
        Font font = new Font("Century Gothic", Font.PLAIN, 11);
        graphics2D.setFont(font);
        graphics2D.fillRect(0,0, GamePanel.WIDTH, GamePanel.HEIGHT2);
        graphics2D.setColor(new Color(0,0,0));
        graphics2D.drawString("Temps final",25, 36);
        GameState prev = this.gameStateManager.getPreviousState();
        if (prev instanceof Level1State) {
            Level1State lv1 = (Level1State)prev;
            player = lv1.getPlayer();
            int seconds = player.getTime();
            int scoreFinalLv1 = seconds * 100;
            graphics2D.drawString( ""+seconds, 95, 36);
            graphics2D.drawString( "Score niveau : " + scoreFinalLv1, 25, 56);
            graphics2D.drawString( "Score total : " + scoreFinalLv1, 25, 76);
            graphics2D.drawString( "Niveau 1 terminé !", 25, 90);
            Graphics2D g = (Graphics2D) graphics2D;
            g.setColor(Color.RED);
            g.drawString("CODE NIVEAU 1 : UN",25,150);
            gameStateManager.setScoreLvl1(scoreFinalLv1);

        } else if(prev instanceof Level2State) {
            Level2State lv2 = (Level2State) prev;
            player = lv2.getPlayer();
            int seconds = player.getTime();
            int scoreLv2 = seconds * 100;
            int scoreFinalLv2;
            graphics2D.drawString( ""+seconds, 95, 36);
            graphics2D.drawString( "Score niveau : " + scoreLv2, 25, 56);
            if(gameStateManager.getScoreLvl1() > 0) {
                scoreFinalLv2 =  scoreLv2 + gameStateManager.getScoreLvl1();
                graphics2D.drawString( "Score total : " + scoreFinalLv2, 25, 76);
            } else {

                graphics2D.drawString( "Score total : " + scoreLv2, 25, 76);
            }
            graphics2D.drawString( "Niveau  2 terminé !", 25, 90);
            gameStateManager.setScoreLvl2(scoreLv2);
            Graphics2D g = (Graphics2D) graphics2D;
            g.setColor(Color.RED);
            g.drawString("CODE NIVEAU 2 : DEUX",25,150);
        } else if(prev instanceof Level3State) {
            Level3State lv3 = (Level3State) prev;
            player = lv3.getPlayer();
            int seconds = player.getTime();
            int scoreFinalLv3 = seconds * 100;
            graphics2D.drawString( ""+seconds, 95, 36);
            graphics2D.drawString( "Score niveau : " + scoreFinalLv3, 25, 56);
            gameStateManager.setScoreLvl3(scoreFinalLv3);
            if(gameStateManager.getScoreLvl1() > 0 || gameStateManager.getScoreLvl2() > 0) {
                scoreFinalLv3 = (gameStateManager.getScoreLvl1() + gameStateManager.getScoreLvl2()) + scoreFinalLv3;
                graphics2D.drawString( "Score total : " + scoreFinalLv3, 25, 76);
            } else {
                graphics2D.drawString( "Score total : " + scoreFinalLv3, 25, 76);
            }


            graphics2D.drawString( "Niveau  3 terminé !", 25, 90);
            Graphics2D g = (Graphics2D) graphics2D;
            g.setColor(Color.RED);
            g.drawString("CODE NIVEAU 3 : TROIS",25,150);
        } else if(prev instanceof Level4State) {
            Level4State lv4 = (Level4State) prev;
            player = lv4.getPlayer();
            int seconds = player.getTime();
            int scoreFinalLv4 = seconds * 100;
            graphics2D.drawString( ""+seconds, 95, 36);
            graphics2D.drawString( "Score niveau : " + scoreFinalLv4, 25, 56);
            gameStateManager.setScoreLvl4(scoreFinalLv4);
            if(gameStateManager.getScoreLvl1() > 0 || gameStateManager.getScoreLvl2() > 0 ||gameStateManager.getScoreLvl3() > 0) {
                scoreFinalLv4 = (gameStateManager.getScoreLvl1() + gameStateManager.getScoreLvl2() + gameStateManager.getScoreLvl3()) + scoreFinalLv4;
                graphics2D.drawString( "Score total : " + scoreFinalLv4, 25, 76);
            } else {
                graphics2D.drawString( "Score total : " + scoreFinalLv4, 25, 76);
            }
            graphics2D.drawString( "Niveau  4 terminé !", 25, 90);
            Graphics2D g = (Graphics2D) graphics2D;
            g.setColor(Color.RED);
            g.drawString("CODE NIVEAU 4 : QUATRE",25,150);
        } else if(prev instanceof Level5State) {
            Level5State lv5 = (Level5State) prev;
            player = lv5.getPlayer();
            int seconds = player.getTime();
            int scoreFinalLv5 = seconds * 100;
            graphics2D.drawString( ""+seconds, 95, 36);
            graphics2D.drawString( "Score niveau : " + scoreFinalLv5, 25, 56);
            if (gameStateManager.getScoreLvl1() > 0 || gameStateManager.getScoreLvl2() > 0 || gameStateManager.getScoreLvl3() > 0 || gameStateManager.getScoreLvl4() > 0) {
                scoreFinalLv5 = (gameStateManager.getScoreLvl1() + gameStateManager.getScoreLvl2() + gameStateManager.getScoreLvl3() + gameStateManager.getScoreLvl4()) + scoreFinalLv5;
                graphics2D.drawString( "Score total : " + scoreFinalLv5, 25, 76);
            } else {
                graphics2D.drawString( "Score total : " + scoreFinalLv5, 25, 76);
            }
            graphics2D.drawString( "Niveau  5 terminé !", 25, 90);
            Graphics2D g = (Graphics2D) graphics2D;
            g.setColor(Color.RED);
            g.drawString("CODE NIVEAU 5 : CINQ",25,150);
        }
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString( "ENTRER: CONTINUER", 25, 110);
        graphics2D.drawString( "ECHAP: MENU PRINCIPAL", 25, 130);
    }

    /**
     * Intercepter les entrées
     */
    @Override
    public void handleInput() {
        if(Inputs.isPressed(Inputs.ESCAPE)) {
            gameStateManager.setState(GameStateManager.MENU);
            SoundFX.stop("snoopyStage1");
            SoundFX.stop("snoopyStage2");
            SoundFX.stop("snoopyStage3");
            SoundFX.stop("snoopyStage4");
            SoundFX.stop("snoopyStage5");
        }
        if(Inputs.isPressed(Inputs.ENTER)) {
            if (gameStateManager.getPreviousState() instanceof Level1State) {
                gameStateManager.setState(GameStateManager.LEVEL2);
                SoundFX.stop("snoopyStage1");
            }
            else if (gameStateManager.getPreviousState() instanceof Level2State) {
                gameStateManager.setState(GameStateManager.LEVEL3);
                SoundFX.stop("snoopyStage1");
                SoundFX.stop("snoopyStage2");
            }
            else if (gameStateManager.getPreviousState() instanceof Level3State) {
                gameStateManager.setState(GameStateManager.LEVEL4);
                SoundFX.stop("snoopyStage1");
                SoundFX.stop("snoopyStage2");
            }
            else {
                gameStateManager.setState(GameStateManager.LEVEL5);
                SoundFX.stop("snoopyStage1");
                SoundFX.stop("snoopyStage2");
            }
        }
    }
}
