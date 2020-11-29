package com.ece.snoopy.Controller;

import com.ece.snoopy.Main.GamePanel;

import javax.imageio.ImageIO;
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
}
