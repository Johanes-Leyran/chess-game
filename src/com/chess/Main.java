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
            try {
                Globals.setLevel(Integer.parseInt(args[0]));
            } catch (NumberFormatException e) {
                System.out.println("First argument must be a number!");
            }
        }

        if(args.length >= 2) {
            try {
                Globals.setFps(Integer.parseInt(args[1]));
            } catch (NumberFormatException e) {
                System.out.println("Second argument must be a number !");
            }
        }

        SwingUtilities.invokeLater(() -> {
            ChessFrame chess = new ChessFrame();
            chess.initUI();
        });
    }
}
