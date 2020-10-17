package com.ece.snoopy.Controller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Content {

    public static BufferedImage[][] BACKGROUNDMENU = load("/Intro/snoopymenu.png",160,144);
    public static BufferedImage[][] CURSOR = load("/Intro/cursor.png", 16, 16);

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
