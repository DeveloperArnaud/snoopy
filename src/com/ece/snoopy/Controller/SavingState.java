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

        if (retrival == JFileChooser.APPROVE_OPTION) {
            try {
                FileOutputStream fileOut = new FileOutputStream(chooser.getSelectedFile());
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(gameState);
                objectOut.close();
                JOptionPane.showMessageDialog(null, "La sauvegarde a été effectuée");
            } catch (NotSerializableException | FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
