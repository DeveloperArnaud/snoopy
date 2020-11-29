package com.ece.snoopy.Map;

import java.awt.image.BufferedImage;

public class Tile {

    //Image
    private BufferedImage image;
    private int type;

    //Type
    public static final int NORMAL = 0;
    public static final int BLOCKED = 1;

    /**
     * Constructeur
     * @param image BufferedImage
     * @param type int
     */
    public Tile(BufferedImage image, int type) {
        this.image = image;
        this.type = type;
    }

    /**
     * @return BufferedImage
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * @return Le type de case (0 = NORMAL / 1 = BLOCKED)
     */
    public int getType() {
        return type;
    }
}
