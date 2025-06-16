package src.com.chess.game;

import src.com.chess.utils.SpriteSheetHandler;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class CursorHandler {
    static HashMap<String, Cursor> cursorHashMap = new HashMap<>();
    JFrame frame;

    public CursorHandler(JFrame frame, Toolkit toolkit) {
        this.frame = frame;
        this.loadCursor(toolkit);
    }

    public void loadCursor(Toolkit toolkit) {
        if(cursorHashMap.isEmpty()) {
            SpriteSheetHandler cursorSheet;

            cursorSheet = new SpriteSheetHandler(
                    "cursor.png", 16, 16, 2.5
            );

            cursorHashMap.put("normal", toolkit.createCustomCursor(
                    cursorSheet.getSprite(0, 0), new Point(0, 0), "custom cursor")
            );
            cursorHashMap.put("toGrab", toolkit.createCustomCursor(
                    cursorSheet.getSprite(0, 1), new Point(0, 0), "custom cursor")
            );
            cursorHashMap.put("grab", toolkit.createCustomCursor(
                    cursorSheet.getSprite(0, 2), new Point(0, 0), "custom cursor")
            );
        }
    }

    public void setCursor(String name) {
        Cursor cursor = cursorHashMap.get(name);

        if(cursor == null) {
            throw new NoSuchElementException(
                    String.format("No such cursor \"%s\" in cursorHashMap", name)
            );
        }

        this.frame.setCursor(cursor);
    }
}
