package src.com.chess;

import src.com.chess.components.ChessFrame;
import src.com.chess.game.Globals;
import src.com.chess.utils.Log;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        if(args.length >= 1) {
            Globals.setLevel(args[0]);
        }
        if(args.length >= 2) {
            Globals.setFps(args[1]);
        }
        if(args.length >= 3) {
            Globals.setShowRect(args[2]);
        }

        SwingUtilities.invokeLater(() -> {
            ChessFrame chess = new ChessFrame();
            chess.initUI();
        });
    }
}
