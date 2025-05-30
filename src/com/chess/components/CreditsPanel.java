package src.com.chess.components;

import src.com.chess.game.CursorHandler;
import src.com.chess.game.FontHandler;
import src.com.chess.game.SoundManager;
import src.com.chess.game.SpriteSheetHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class CreditsPanel extends JPanel {
    String[] names = new String[4];
    JPanel mainPanel;
    JButton backBtn;
    JFrame frame;
    CardLayout cardLayout;
    CursorHandler cursorHandler;
    FontHandler fontHandler;


    public CreditsPanel(
            JPanel mainPanel,
            CardLayout cardLayout,
            JFrame frame,
            CursorHandler cursorHandler,
            FontHandler fontHandler
    ) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.frame = frame;
        this.cursorHandler = cursorHandler;
        this.fontHandler = fontHandler;
        this.setBorder(new EmptyBorder(25, 25, 25, 25));

        this.names[0] = "Leyran, Johanes Lawrence Scott II S.";
        this.names[1] = "Baldonado, Kate Ashly F.";
        this.names[2] = "Mojica, John Lawrence V.";
        this.names[3] = "Togueno, Chlarenz P.";

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(45, 45, 45));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(28, 28, 28));
        contentPanel.setAlignmentX(SwingConstants.CENTER);
        contentPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        contentPanel.add(this.createLabel(
                "GROUP 2 MEMBERS",
                new EmptyBorder(0, 0, 30, 0),
                26
        ));

        for(JLabel label: generateLabelNames(names, names.length)) contentPanel.add(label);

        contentPanel.add(this.createLabel(
                "SPRITE",
                new EmptyBorder(35, 0, 30, 0),
                26
        ));

        // making the link clickable is a pain in the ass, so I did not implement it
        contentPanel.add(this.createLabel(
                "https://dani-maccari.itch.io/pixel-chess",
                new EmptyBorder(0, 0, 30, 0),
                20
        ));

        contentPanel.add(this.createLabel(
                "SOUNDS",
                new EmptyBorder(35, 0, 30, 0),
                26
        ));

        contentPanel.add(this.createLabel(
                "https://www.chess.com",
                new EmptyBorder(0, 0, 30, 0),
                20
        ));

        contentPanel.add(this.createLabel(
                "Version 1.0.0",
                new EmptyBorder(100, 0, 30, 0),
                24
        ));

        backBtn = new JButton("Back");
        backBtn.setBackground(new Color(60, 60, 60));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(fontHandler.getFont(20));
        backBtn.setFocusPainted(false);
        backBtn.setBorder(new EmptyBorder(20, 20, 15, 20));

        backBtn.getModel().addChangeListener(e -> {
            ButtonModel model = (ButtonModel) e.getSource();

            if (model.isRollover()) {
                backBtn.setBackground(new Color(80, 80, 80));
            } else {
                backBtn.setBackground(new Color(60, 60, 60));
            }
        });

        backBtn.addActionListener(_ -> {
            this.cursorHandler.setCursor("grab");
            cardLayout.show(mainPanel, "MENU");
            SoundManager.play("capture");

            final int CURSOR_RESET_DELAY_MS = 150;

            Timer cursorResetTimer = new Timer(CURSOR_RESET_DELAY_MS, _ -> {
                this.cursorHandler.setCursor("normal");
            });
            cursorResetTimer.setRepeats(false); // Ensure it only runs once
            cursorResetTimer.start();
        });

        this.add(contentPanel, BorderLayout.CENTER);
        this.add(backBtn, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text, EmptyBorder emptyBorder, int fontSize) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(220, 220, 220));
        label.setFont(fontHandler.getFont(fontSize));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(emptyBorder);

        return label;
    }

    public JLabel[] generateLabelNames(String[] list, int size) {
        JLabel[] labels = new JLabel[size];

        for(int i = 0; i < list.length;i++) {
            JLabel labelName = this.createLabel(
                    list[i], new EmptyBorder(0, 0, 15, 0), 20
            );
            labels[i] = labelName;
        }

        return labels;
    }
}
