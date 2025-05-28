package src.com.chess.game;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;


public class SoundManager {
    private static final HashMap<String, Clip> soundMap = new HashMap<>();

    // must be called before playing any sounds
    public static void loadSounds(){
        if(!soundMap.isEmpty()) {
            return;
        }

        String[] arr = {
                "capture", "castle", "move-check", "move-self", "promote", "notify"
        };

        for(String name: arr) {
            AudioInputStream audioInputStream = null;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(
                        ResourceHandler.getDynamicPath(String.format("sounds/%s.wav", name))
                );
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                soundMap.put(name, clip);
            } catch (UnsupportedAudioFileException e) {
                throw new RuntimeException("Only .wav format is supported");
            } catch (LineUnavailableException e) {
                throw new RuntimeException("");
            } catch (IOException e) {
                throw new RuntimeException("Could not found audio: " + name);
            }
        }
    };

    public static void play(String name){
        if(soundMap.isEmpty()) {
            loadSounds();
        }

        Clip clip = soundMap.get(name);

        if(clip == null) {
            throw new NoSuchElementException(
                    String.format("No such sound \"%s\" in soundMap", name)
            );
        }

        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }
}
