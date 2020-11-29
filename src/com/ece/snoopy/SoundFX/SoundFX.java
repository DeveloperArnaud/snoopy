package com.ece.snoopy.SoundFX;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
/*
    SON DU JEU
 */
public class SoundFX {

    // Clip
    private static HashMap<String, Clip> sounds;
    private static int gap;

    public static void init() {
        sounds = new HashMap<>();
        gap = 0;
    }

    /**
     * Charger une piste audio à partir de son chemin (seules les .wav peut être lues car plus léger)
     * @param s
     * @param n
     */
    public static void loadSound(String s, String n) {
        if (sounds.get(n) != null) return;
        Clip clip;
        try {
            InputStream in = SoundFX.class.getResourceAsStream(s);
            InputStream bin = new BufferedInputStream(in);
            AudioInputStream ais = AudioSystem.getAudioInputStream(bin);
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(), 16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false);
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(dais);
            sounds.put(n, clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Jouer une piste audio
     * @param s
     */
    public static void play(String s) {
        play(s, gap);
    }

    public static void play(String s, int i) {
        Clip c = sounds.get(s);
        if(c == null) return;
        if(c.isRunning()) c.stop();
        c.setFramePosition(i);
        while(!c.isRunning()) c.start();
    }

    /**
     * Stopper une piste audio
     * @param s
     */
    public static void stop(String s) {
        if(sounds.get(s) == null) return;
        if(sounds.get(s).isRunning()) sounds.get(s).stop();
    }

    /**
     * Relancer une piste audio
     * @param s
     */
    public static void resumeLoop(String s) {
        Clip c = sounds.get(s);
        if(c == null) return;
        c.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Ajuster le volume
     * @param s
     * @param f
     */
    public static void setVolume(String s, float f) {
        Clip c = sounds.get(s);
        if(c == null) return;
        FloatControl vol = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
        vol.setValue(f);
    }
}
