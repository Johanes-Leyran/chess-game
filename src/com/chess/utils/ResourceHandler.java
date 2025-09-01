package src.com.chess.utils;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class ResourceHandler {
    public static InputStream loadDynamicPath(String path){
        return ResourceHandler.class
                .getClassLoader()
                .getResourceAsStream("./resource/" + path);
    };

    public static BufferedImage loadImage(String path){
        try {
            BufferedImage img = ImageIO.read(loadDynamicPath("sprites/" + path));
            Log.INFO(String.format(
                    "%s Load Image: %s",
                    ResourceHandler.class.getSimpleName(),
                    path
            ));
            return img;
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
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(loadDynamicPath("sounds/" + path));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);

        return clip;
    }
}
