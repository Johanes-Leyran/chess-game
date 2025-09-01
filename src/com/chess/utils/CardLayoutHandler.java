package src.com.chess.utils;

import javax.swing.*;
import java.awt.*;

public class CardLayoutHandler {
    public static JPanel mainPanel;
    static CardLayout cardLayout;

    public void load(JPanel mainPanel, CardLayout cardLayout) {
        CardLayoutHandler.mainPanel = mainPanel;
        CardLayoutHandler.cardLayout = cardLayout;
    }

    public void show(String goTo) {
        cardLayout.show(mainPanel, goTo);
    }
}
