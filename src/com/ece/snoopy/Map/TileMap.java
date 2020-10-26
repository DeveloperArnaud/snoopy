package com.ece.snoopy.Map;

import com.ece.snoopy.Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileMap {

    //Position
    private int x;
    private int y;
    private int xdest;
    private int ydest;
    private int speed;
    private boolean moving;

    //bounds
    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;

    //map
    private int [][] map;
    private int tileSize;
    private int numRows;
    private int numCols;
    private int width;
    private int height;

    private BufferedImage tileset;
    private int numTilesAcross;
    private Tile[][] tiles;

    //drawing
    private int rowOffset;
    private int colOffset;
    private int numRowsToDraw;
    private int numsColsToDraw;

    public TileMap(int tileSize) {
        this.tileSize = tileSize;
        numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
        numsColsToDraw = GamePanel.WIDTH / tileSize + 2;
        speed = 4;
    }

    public void update() {
        if(x < xdest) {
            x += speed;

            if(x > xdest) {
                x = xdest;
            }
        }

        if (y < ydest) {
            y += speed;
            if(y > ydest) {
                y = ydest;
            }
        }

        fixBounds();

        colOffset = -this.x / tileSize;
        rowOffset = -this.y / tileSize;

        if(x != xdest || y != ydest) moving = true;
        else moving = false;
    }

    public void fixBounds() {
        if(x < xmin) x = xmin;
        if(y <ymin) y = ymin;
        if(x  > xmax) x = xmax;
        if(y  > ymax) y = ymax;
    }

    public void loadTiles(String s) {
        try {
            tileset = ImageIO.read(getClass().getResourceAsStream(s));
            numTilesAcross = tileset.getWidth() / tileSize;
            tiles = new Tile[2][numTilesAcross];

            BufferedImage subImage;

            for(int col = 0; col < numTilesAcross; col ++) {
                subImage = tileset.getSubimage(
                        col * tileSize,
                        0,
                        tileSize,
                        tileSize
                );
                tiles[0][col] = new Tile(subImage, Tile.NORMAL);
                subImage = tileset.getSubimage(
                        col * tileSize,
                        tileSize,
                        tileSize,
                        tileSize
                );
                tiles[1][col] = new Tile(subImage, Tile.BLOCKED);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String s) {
        try {
            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            numCols = Integer.parseInt(br.readLine());
            numRows = Integer.parseInt(br.readLine());
            map = new int[numRows][numCols];
            width = numCols * tileSize;
            height = numRows * tileSize;

            xmin = GamePanel.WIDTH - width;
            xmin = -width;
            xmax = 0;
            ymin = GamePanel.HEIGHT - height;
            ymin = -height;
            ymax = 0;

            String delims = "\\s";
            for (int row = 0; row < numRows; row++) {
                String line = br.readLine();
                String[] tokens = line.split(delims);
                for (int col = 0; col < numCols; col ++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getWidth() {
        return width;
    }

    public int getType(int row, int col) {
        int rowCol = map[row][col];
        int r = rowCol / numTilesAcross;
        int c = rowCol % numTilesAcross;
        return tiles[r][c].getType();
    }

    public int getIndex(int row, int col) {
        return map[row][col];
    }

    public boolean isMoving(){
        return moving;
    }

    public void setPosition(int x, int y) {
        xdest = x;
        ydest = y;
    }


    public void setTile(int row, int col, int index){
        map[row][col] = index;
    }
    public void draw(Graphics2D graphics2D) {
        for(int row = rowOffset; row < rowOffset + numRowsToDraw; row ++) {
            if(row >= numRows) break;
            for (int col = colOffset; col < colOffset + numsColsToDraw; col ++) {
                if(col >= numCols) break;
                if(map[row][col] == 0) continue;

                int rowCol = map[row][col];
                int r = rowCol / numTilesAcross;
                int c = rowCol % numTilesAcross;

                graphics2D.drawImage(tiles[r][c].getImage(),
                        x + col * tileSize,
                        y + row * tileSize,
                        null);
            }
        }
    }

    public void setInitPosition(int i, int i1) {
        this.x = i;
        this.y = i1;
    }
}
