package com.ece.snoopy.Controller;

import com.ece.snoopy.Map.Tile;
import com.ece.snoopy.Map.TileMap;
import com.ece.snoopy.States.GameState;
import com.ece.snoopy.States.Level1State;

import javax.swing.*;
import java.io.*;



/*
 EN COURS DE DEVELOPPEMENT
 */
public class SavingState {

    public static void saveState(GameState gameState){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("C:/Users/arnau"));
        int retrival = chooser.showSaveDialog(null);
        if(gameState instanceof Level1State) {
            Level1State level1State = (Level1State) gameState;
        if (retrival == JFileChooser.APPROVE_OPTION) {
            try {
                FileWriter fw = new FileWriter(chooser.getSelectedFile() + ".txt");
                BufferedWriter out = new BufferedWriter(fw);
                out.write(level1State.getPlayer().getNbBirds() + "," + level1State.getPlayer().getTime() + "," + level1State.getPlayer().getLife() + ",");
                for (int i = 0; i < level1State.getTileMap().getNumRows(); i++) {
                    for (int j = 0; j < level1State.getTileMap().getNumCols(); j++) {
                        //out.write(getTileID(tileMap, i, j));
                        out.write(String.valueOf(level1State.getTileMap().getIndex(i, j)));
                    }
                }
                out.close();
                fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        }
    }

    public static TileMap loadMap(String fileName) {
        TileMap tileMap = new TileMap(16);
        try {

            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String data = br.readLine();
            for (int i = 0; i  < tileMap.getNumRows(); i ++) {
                for (int j = 0; j < tileMap.getNumCols(); j ++) {
                    tileMap.setTile(i,j,tileMap.getIndex(i,j));
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return tileMap;
    }

    public static String getTileID(TileMap tile, int i, int j) {
        String ID = "E";
        switch (tile.getType(i,j)) {
            case Tile.BLOCKED:
                ID = "1";
                break;
            case Tile.NORMAL:
                ID = "0";
                break;
        }
        return ID;
    }
}
