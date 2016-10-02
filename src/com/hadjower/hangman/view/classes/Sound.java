package com.hadjower.hangman.view.classes;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 08.06.2016.
 */
public class Sound {
    private static Map<SoundType, Media> sounds;
    private static boolean isOn;

    public static void playSound(SoundType soundType) {
        if (sounds == null) {
            initSounds();
        }
        if (!isOn)
            return;
        new MediaPlayer(sounds.get(soundType)).play();
    }

    private static void initSounds() {
        isOn = true;
        sounds = new HashMap<>();
        sounds.put(SoundType.SCRATCH, new Media(Sound.class.getResource("/com/hadjower/hangman/assets/sounds/scratch.wav").toString()));
        sounds.put(SoundType.WIN, new Media(Sound.class.getResource("/com/hadjower/hangman/assets/sounds/win.wav").toString()));
        sounds.put(SoundType.LOSE, new Media(Sound.class.getResource("/com/hadjower/hangman/assets/sounds/lose.wav").toString()));
    }

    public static void turnSound() {
        isOn = !isOn;
    }

    public static boolean isOn() {
        return isOn;
    }
}
