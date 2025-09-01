package src.com.chess.components;

import src.com.chess.constants.UIColors;
import src.com.chess.game.CursorHandler;
import src.com.chess.utils.FontHandler;
import src.com.chess.utils.SoundManager;
import src.com.chess.utils.UIBuilder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreditsPanel extends JPanel {
    String[] names = new String[4];
    JPanel mainPanel;
    JButton backBtn;
    JFrame frame;
    CardLayout cardLayout;
    CursorHandler cursorHandler;
    FontHandler fontHandler;


    public CreditsPanel(JFrame frame) {
        this.frame = frame;
        this.cursorHandler = new CursorHandler();
        this.fontHandler = new FontHandler();
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
                "Ver 1.0.0",
                new EmptyBorder(100, 0, 30, 0),
                24
        ));

        backBtn = UIBuilder.buildNavButton(
                "BACK",
                "MENU",
                fontHandler.getFont(20),
                new EmptyBorder(20, 20, 15, 20)
        );


        this.add(contentPanel, BorderLayout.CENTER);
        this.add(backBtn, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text, EmptyBorder emptyBorder, int fontSize) {
        return UIBuilder.buildLabel(text, emptyBorder, fontHandler.getFont(fontSize));
    }

    public JLabel[] generateLabelNames(String[] list, int size) {
        JLabel[] labels = new JLabel[size];

        for(int i = 0; i < list.length;i++) {
            JLabel labelName =  UIBuilder.buildLabel(
                    list[i], new EmptyBorder(0, 0, 15, 0), fontHandler.getFont(20)
            );
            labels[i] = labelName;
        }

        return labels;
    }
}