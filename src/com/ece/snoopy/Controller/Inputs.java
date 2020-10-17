package com.ece.snoopy.Controller;

import java.awt.event.KeyEvent;

public class Inputs {
    public static final int NUM_KEYS = 8;

    public static boolean keyState[] = new boolean[NUM_KEYS];
    public static boolean prevKeyState[] = new boolean[NUM_KEYS];

    public static int ENTER = 5;
    public static int UP = 0;
    public static int DOWN = 2;

    public static void keySet(int i , boolean b) {
            switch(i) {

                case KeyEvent.VK_UP: keyState[UP] = b;
                break;
                case KeyEvent.VK_DOWN: keyState[DOWN] = b;
                break;
                case KeyEvent.VK_ENTER: keyState[ENTER] = b;
                break;
            }
    }

    public static void update() {
        for (int i = 0; i < NUM_KEYS; i++) {
            prevKeyState[i] = keyState[i];
        }
    }

    public static boolean isPressed(int i) {
        return keyState[i] && prevKeyState[i];
    }

}
