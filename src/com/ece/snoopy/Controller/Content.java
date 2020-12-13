package com.ece.snoopy.Controller;

import com.ece.snoopy.Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Content {

    //Sprite des objets
    public static BufferedImage[][] BACKGROUNDMENU = load("/Intro/backgroundMenu.jpg", GamePanel.WIDTH * GamePanel.SCALE,GamePanel.HEIGHT2 * GamePanel.SCALE );
    public static BufferedImage[][] CURSOR = load("/Intro/cursor.png", 16, 16);
    public static BufferedImage[][] PLAYER = load("/Sprites/SpriteSnoopy.gif",16,16);
    public static BufferedImage[][] BIRD = load("/Sprites/diamond.gif",16 ,16);
    public static BufferedImage[][] BALL = load("/Sprites/ball.png",16,16);
    public static BufferedImage[][] ROCK = load("/Sprites/rock.png",16,16);
    public static BufferedImage[][] SPEED = load("/Sprites/speed.png",16,16);
    public static BufferedImage[][] MECHANT = load("/Sprites/SpriteSnoopyMechant.gif",16,16);
    public static BufferedImage[][] BAR = load("/Sprites/barHUD.gif",160,16);
    public static BufferedImage[][] HEART = load("/Sprites/heart.gif",12,12);
    public static BufferedImage[][] font = load("/HUD/font.gif", 8, 8);

    /**
     * Charger les images
     * @param s Chemin du fichier
     * @param width Largeur de l'image
     * @param height Hauteur de l'image
     * @return Une image adaptée aux dimension de la fenêtre de jeu
     */
    private static BufferedImage[][] load(String s, int width, int height) {
        BufferedImage[][] image;

        try{
            BufferedImage sprite = ImageIO.read(Content.class.getResourceAsStream(s));
            int w = sprite.getWidth() / width;
            int h = sprite.getHeight() / height;
            image = new BufferedImage[h][w];
            for (int i = 0; i < h; i ++) {
                for (int j = 0; j < w; j++) {
                    image[i][j] = sprite.getSubimage(j * width, i * height, width,height );
                }
            }
            return image;

        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Not found");
            System.exit(0);
        }
        return null;
    }
    public static void drawString(Graphics2D g, String s, int x, int y) {
        s = s.toUpperCase();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == 47) c = 36; // slash
            if(c == 58) c = 37; // cote
            if(c == 32) c = 38; // espace
            if(c >= 65 && c <= 90) c -= 65; // lettres
            if(c >= 48 && c <= 57) c -= 22; // nombres
            int row = c / font[0].length;
            int col = c % font[0].length;
            g.drawImage(font[row][col], x + 8 * i, y, null);
        }
    }
}
