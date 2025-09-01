package src.com.chess.utils;

import src.com.chess.game.CursorHandler;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class UIBuilder {
    static final int CURSOR_RESET_DELAY_MS = 150;
    static final CardLayoutHandler cardLayoutHandler = new CardLayoutHandler();

    public static JLabel buildLabel(String text, EmptyBorder emptyBorder, Font font) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(220, 220, 220));
        label.setFont(font);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(emptyBorder);

        return label;
    }

    private static JButton buildBaseButton(String text, Font font, EmptyBorder emptyBorder) {
        JButton button = new JButton(text);

        button.setBackground(new Color(60, 60, 60));
        button.setForeground(Color.WHITE);
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBorder(emptyBorder);

        button.getModel().addChangeListener(changeEvent -> {
            ButtonModel model = (ButtonModel) changeEvent.getSource();

            if (model.isRollover()) {
                button.setBackground(new Color(80, 80, 80));
            } else {
                button.setBackground(new Color(60, 60, 60));
            }
        });

        return  button;
    }

    public static JButton buildNavButton(
            String text,
            String goTo,
            Font font,
            EmptyBorder emptyBorder
    ) {
        JButton button = buildBaseButton(text, font, emptyBorder);
        CursorHandler cursorHandler = new CursorHandler();

        button.addActionListener(_ -> {
            cursorHandler.setCursor("grab");
            cardLayoutHandler.show(goTo);
            SoundManager.play("move-self");

            Timer cursorResetTimer = new Timer(
                    CURSOR_RESET_DELAY_MS, _ -> cursorHandler.setCursor("normal")
            );

            cursorResetTimer.setRepeats(false); // to ensure it only runs once
            cursorResetTimer.start();
        });

        return button;
    }

    public static JButton buildButton(
            String text,
            Font font,
            CursorHandler cursorHandler,
            ActionCall callback,
            EmptyBorder emptyBorder
    ) {
        JButton button = buildBaseButton(text, font, emptyBorder);

        button.addActionListener(_ -> {
            cursorHandler.setCursor("grab");
            callback.call();

            Timer cursorResetTimer = new Timer(
                    CURSOR_RESET_DELAY_MS, _ -> cursorHandler.setCursor("normal")
            );
            cursorResetTimer.setRepeats(false); // ensure it only runs once
            cursorResetTimer.start();
        });

        return button;
    }
}