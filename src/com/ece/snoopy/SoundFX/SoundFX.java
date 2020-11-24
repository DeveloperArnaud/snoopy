package com.ece.snoopy.SoundFX;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;

public class SoundFX {

    private static HashMap<String, Clip> sounds;
    private static int gap;

    public static void init() {
        sounds = new HashMap<>();
        gap = 0;
    }

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

    public static void stop(String s) {
        if(sounds.get(s) == null) return;
        if(sounds.get(s).isRunning()) sounds.get(s).stop();
    }

    public static void resumeLoop(String s) {
        Clip c = sounds.get(s);
        if(c == null) return;
        c.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
