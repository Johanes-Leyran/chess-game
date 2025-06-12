package src.com.chess.utils;

import java.awt.*;
import java.io.IOException;


public class FontHandler {
    private static Font font;

    public FontHandler(String name) {
        try {
            loadFont(name);
        } catch (IOException e) {
            throw new RuntimeException("Font not found: " + name);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadFont(String name) throws IOException, FontFormatException {
        if(font != null) {
            return;
        }

        font = Font.createFont(
                Font.TRUETYPE_FONT, ResourceHandler.loadDynamicPath("fonts/" + name)
        );
    }

    public Font getFont(int size) {
        return font.deriveFont(Font.PLAIN, size);
    }
}