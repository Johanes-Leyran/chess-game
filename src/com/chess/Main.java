package src.com.chess;

import src.com.chess.components.ChessFrame;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessFrame chess = new ChessFrame();

            chess.initUI();
        });
    }
}
