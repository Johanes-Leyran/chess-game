package src.com.chess.game;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class ResourceHandler {
    public static InputStream getDynamicPath(String path){
        return ResourceHandler.class
                .getClassLoader()
                .getResourceAsStream("./resource/" + path);
    };

    public static BufferedImage loadImage(String path){
        try {
            return ImageIO.read(getDynamicPath("sprites/" + path));
        } catch (IOException e) {
            throw new RuntimeException("Unable to load image at path: " + path);
        }
    }

    // Does not always work for some reason but directly loading and adding the clip
    // in sound manager does, probably because of the clip life-cycle
    @Deprecated
    public static Clip loadSound(String path) throws
            IOException, UnsupportedAudioFileException, LineUnavailableException
    {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getDynamicPath("sounds/" + path));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);

        return clip;
    }
}
