package src.com.chess.utils;

import src.com.chess.game.CursorHandler;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class UIBuilder {
    public static JLabel buildLabel(String text, EmptyBorder emptyBorder, Font font) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(220, 220, 220));
        label.setFont(font);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(emptyBorder);

        return label;
    }

    private static JButton buildBaseButton(String text, Font font) {
        JButton button = new JButton(text);

        button.setBackground(new Color(60, 60, 60));
        button.setForeground(Color.WHITE);
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(30, 30, 30, 30));

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
            CursorHandler cursorHandler,
            CardLayout cardLayout,
            JPanel mainPanel
    ) {
        JButton button = buildBaseButton(text, font);

        button.addActionListener(_ -> {
            cursorHandler.setCursor("grab");
            cardLayout.show(mainPanel, goTo);
            SoundManager.play("move-self");

            final int CURSOR_RESET_DELAY_MS = 150;

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
            ActionCall callback
    ) {
        JButton button = buildBaseButton(text, font);

        button.addActionListener(_ -> {
            cursorHandler.setCursor("grab");
            callback.call();

            final int CURSOR_RESET_DELAY_MS = 150;

            Timer cursorResetTimer = new Timer(
                    CURSOR_RESET_DELAY_MS, _ -> cursorHandler.setCursor("normal")
            );
            cursorResetTimer.setRepeats(false); // ensure it only runs once
            cursorResetTimer.start();
        });

        return button;
    }
}