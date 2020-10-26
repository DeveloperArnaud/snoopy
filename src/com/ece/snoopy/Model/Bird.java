package com.ece.snoopy.Model;

import com.ece.snoopy.Map.TileMap;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class Bird extends Model{

    BufferedImage[] bufferedImages;

    public Bird(TileMap tm) {
        super(tm);

        width = 16;
        height = 16;
        cwidth = 12;
        cheight = 12;
    }
}
