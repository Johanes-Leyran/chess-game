package src.com.chess.utils;

import java.awt.*;
import java.io.IOException;


public class FontHandler {
    private static Font font;


    public void loadFont(String name){
        if(font != null) {
            return;
        }

        try {
            font = Font.createFont(
                    Font.TRUETYPE_FONT, ResourceHandler.loadDynamicPath("fonts/" + name)
            );
        } catch (IOException e) {
            throw new RuntimeException("Font not found: " + name);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public Font getFont(int size) {
        return font.deriveFont(Font.PLAIN, size);
    }
}