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
     *
     * @param gameStateManager GameStateManager
     */
    public EndState(GameStateManager gameStateManager) {
        super(gameStateManager);
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void init() {
        color = new Color(164, 198, 222);
    }

    @Override
    public void update() {
        handleInput();
    }

    /**
     * Affichage de l'etat de fin de jeu
     *
     * @param graphics2D Graphics2D
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(color);
        Font font = new Font("Century Gothic", Font.PLAIN, 11);
        graphics2D.setFont(font);
        graphics2D.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2);
        graphics2D.setColor(new Color(0, 0, 0));

        GameState prev = this.gameStateManager.getPreviousState();
        if (!(prev instanceof AdvancedLevel2State || prev instanceof AdvancedLevel1State))
            graphics2D.drawString("Temps final", 20, 36);
        if (prev instanceof Level1State) {
            Level1State lv1 = (Level1State) prev;
            player = lv1.getPlayer();
            int seconds = player.getTime();
            int scoreFinalLv1 = seconds * 100;
            scoreFinalLv1 = scoreFinalLv1 * player.getLife() / 3;
            graphics2D.drawString("" + seconds, 95, 36);
            graphics2D.drawString("Score niveau : " + scoreFinalLv1, 20, 56);
            graphics2D.drawString("Score total : " + scoreFinalLv1, 20, 76);
            graphics2D.drawString("Niveau 1 terminé !", 20, 90);
            Graphics2D g = (Graphics2D) graphics2D;
            g.setColor(Color.RED);
            g.drawString("CODE NIVEAU 1 : UN", 20, 150);
            gameStateManager.setScoreLvl1(scoreFinalLv1);

        } else if (prev instanceof Level2State) {
            Level2State lv2 = (Level2State) prev;
            player = lv2.getPlayer();
            int seconds = player.getTime();
            int scoreLv2 = seconds * 100;
            int scoreFinalLv2;
            scoreLv2 = scoreLv2 * player.getLife() / 3;
            graphics2D.drawString("" + seconds, 95, 36);
            graphics2D.drawString("Score niveau : " + scoreLv2, 20, 56);
            if (gameStateManager.getScoreLvl1() > 0) {
                scoreFinalLv2 = scoreLv2 + gameStateManager.getScoreLvl1();
                graphics2D.drawString("Score total : " + scoreFinalLv2, 20, 76);
            } else {

                graphics2D.drawString("Score total : " + scoreLv2, 20, 76);
            }
            graphics2D.drawString("Niveau  2 terminé !", 20, 90);
            gameStateManager.setScoreLvl2(scoreLv2);
            Graphics2D g = (Graphics2D) graphics2D;
            g.setColor(Color.RED);
            g.drawString("CODE NIVEAU 2 : DEUX", 20, 150);
        } else if (prev instanceof Level3State) {
            Level3State lv3 = (Level3State) prev;
            player = lv3.getPlayer();
            int seconds = player.getTime();
            int scoreFinalLv3 = seconds * 100;
            scoreFinalLv3 = scoreFinalLv3 * player.getLife() / 3;
            graphics2D.drawString("" + seconds, 95, 36);
            graphics2D.drawString("Score niveau : " + scoreFinalLv3, 20, 56);
            gameStateManager.setScoreLvl3(scoreFinalLv3);
            if (gameStateManager.getScoreLvl1() > 0 || gameStateManager.getScoreLvl2() > 0) {
                scoreFinalLv3 = (gameStateManager.getScoreLvl1() + gameStateManager.getScoreLvl2()) + scoreFinalLv3;
                graphics2D.drawString("Score total : " + scoreFinalLv3, 20, 76);
            } else {
                graphics2D.drawString("Score total : " + scoreFinalLv3, 20, 76);
            }


            graphics2D.drawString("Niveau  3 terminé !", 20, 90);
            Graphics2D g = (Graphics2D) graphics2D;
            g.setColor(Color.RED);
            g.drawString("CODE NIVEAU 3 : TROIS", 20, 150);
        } else if (prev instanceof Level4State) {
            Level4State lv4 = (Level4State) prev;
            player = lv4.getPlayer();
            int seconds = player.getTime();
            int scoreFinalLv4 = seconds * 100;
            scoreFinalLv4 = scoreFinalLv4 * player.getLife() / 3;
            graphics2D.drawString("" + seconds, 95, 36);
            graphics2D.drawString("Score niveau : " + scoreFinalLv4, 20, 56);
            gameStateManager.setScoreLvl4(scoreFinalLv4);
            if (gameStateManager.getScoreLvl1() > 0 || gameStateManager.getScoreLvl2() > 0 || gameStateManager.getScoreLvl3() > 0) {
                scoreFinalLv4 = (gameStateManager.getScoreLvl1() + gameStateManager.getScoreLvl2() + gameStateManager.getScoreLvl3()) + scoreFinalLv4;
                graphics2D.drawString("Score total : " + scoreFinalLv4, 20, 76);
            } else {
                graphics2D.drawString("Score total : " + scoreFinalLv4, 20, 76);
            }
            graphics2D.drawString("Niveau  4 terminé !", 20, 90);
            Graphics2D g = (Graphics2D) graphics2D;
            g.setColor(Color.RED);
            g.drawString("CODE NIVEAU 4 : QUATRE", 20, 150);
        } else if (prev instanceof Level5State) {
            Level5State lv5 = (Level5State) prev;
            player = lv5.getPlayer();
            int seconds = player.getTime();
            int scoreFinalLv5 = seconds * 100;
            scoreFinalLv5 = scoreFinalLv5 * player.getLife() / 3;
            graphics2D.drawString("" + seconds, 95, 36);
            graphics2D.drawString("Score niveau : " + scoreFinalLv5, 20, 56);
            if (gameStateManager.getScoreLvl1() > 0 || gameStateManager.getScoreLvl2() > 0 || gameStateManager.getScoreLvl3() > 0 || gameStateManager.getScoreLvl4() > 0) {
                scoreFinalLv5 = (gameStateManager.getScoreLvl1() + gameStateManager.getScoreLvl2() + gameStateManager.getScoreLvl3() + gameStateManager.getScoreLvl4()) + scoreFinalLv5;
                graphics2D.drawString("Score total : " + scoreFinalLv5, 20, 76);
            } else {
                graphics2D.drawString("Score total : " + scoreFinalLv5, 20, 76);
            }
            graphics2D.drawString("Niveau  5 terminé !", 20, 90);
            Graphics2D g = (Graphics2D) graphics2D;
            g.setColor(Color.RED);
            g.drawString("CODE NIVEAU 5 : CINQ", 20, 150);
        } else if (prev instanceof AdvancedLevel1State) {
            graphics2D.drawString("Niveau 1 auto fait !", 20, 90);
            Graphics2D g = (Graphics2D) graphics2D;
            g.setColor(Color.RED);
            g.drawString("CODE NIVEAU 1A : AUTO1", 20, 150);
        } else if (prev instanceof AdvancedLevel2State) {
            graphics2D.drawString("Niveau 2 auto fait !", 20, 90);
            Graphics2D g = (Graphics2D) graphics2D;
            g.setColor(Color.RED);
            g.drawString("CODE NIVEAU 2A : AUTO2", 20, 150);
        }
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString("ENTRER: CONTINUER", 20, 110);
        graphics2D.drawString("ECHAP: MENU PRINCIPAL", 20, 130);
    }

    /**
     * Intercepter les entrées
     */
    @Override
    public void handleInput() {
        if (Inputs.isPressed(Inputs.ESCAPE)) {
            gameStateManager.setState(GameStateManager.MENU);
            SoundFX.stop("snoopyStage1");
            SoundFX.stop("snoopyStage2");
            SoundFX.stop("snoopyStage3");
            SoundFX.stop("snoopyStage4");
            SoundFX.stop("snoopyStage5");
        }
        if (Inputs.isPressed(Inputs.ENTER)) {
            if (gameStateManager.getPreviousState() instanceof Level1State) {
                gameStateManager.setState(GameStateManager.LEVEL2);
                SoundFX.stop("snoopyStage1");
            } else if (gameStateManager.getPreviousState() instanceof Level2State) {
                gameStateManager.setState(GameStateManager.LEVEL3);
                SoundFX.stop("snoopyStage1");
                SoundFX.stop("snoopyStage2");
                SoundFX.stop("snoopyStage3");
            } else if (gameStateManager.getPreviousState() instanceof Level3State) {
                gameStateManager.setState(GameStateManager.LEVEL4);
                SoundFX.stop("snoopyStage1");
                SoundFX.stop("snoopyStage2");
                SoundFX.stop("snoopyStage3");
                SoundFX.stop("snoopyStage4");

            } else if (gameStateManager.getPreviousState() instanceof Level4State) {
                gameStateManager.setState(GameStateManager.LEVEL5);
                SoundFX.stop("snoopyStage1");
                SoundFX.stop("snoopyStage2");
                SoundFX.stop("snoopyStage3");
                SoundFX.stop("snoopyStage4");
            } else if (gameStateManager.getPreviousState() instanceof Level5State) {
                SoundFX.stop("snoopyStage1");
                SoundFX.stop("snoopyStage2");
                SoundFX.stop("snoopyStage3");
                SoundFX.stop("snoopyStage4");
                SoundFX.stop("snoopyStage5");
                gameStateManager.setState(GameStateManager.MENU);

            } else if (gameStateManager.getPreviousState() instanceof AdvancedLevel1State) {
                SoundFX.stop("snoopyStage1");
                SoundFX.stop("snoopyStage2");
                SoundFX.stop("snoopyStage3");
                SoundFX.stop("snoopyStage4");
                SoundFX.stop("snoopyStage5");
                gameStateManager.setState(GameStateManager.LEVEL2AUTO);

            } else if (gameStateManager.getPreviousState() instanceof AdvancedLevel2State) {
                SoundFX.stop("snoopyStage1");
                SoundFX.stop("snoopyStage2");
                SoundFX.stop("snoopyStage3");
                SoundFX.stop("snoopyStage4");
                SoundFX.stop("snoopyStage5");
                gameStateManager.setState(GameStateManager.LEVEL1);

            }
        }
    }
}
