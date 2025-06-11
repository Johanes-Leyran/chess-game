package src.com.chess.utils;

import src.com.chess.game.Globals;

public class Log {
    /*
    *   no logging >= 0
    *   debug >= 1
    *   warning >= 2
    *   error >= 3
    */

    // not all console supports colors, works well with intellij console
    static final String RESET = "\033[0m";
    static final String GREEN = "\033[0;32m";
    static final String YELLOW = "\033[0;33m";
    static final String RED = "\033[0;31m";

    public static void print(String label, String msg,String color, int level) {
        if(Globals.getLevel() >= level) {
            System.out.printf("%s%s%s %s%n", color, label, RESET, msg);
        }
    }

    public static void INFO(String msg) {
        print("[INFO]", msg, GREEN, 1);
    }

    public static void DEBUG(String msg) {
        print("[DEBUG]", msg, YELLOW, 2);
    }

    public static void WARN(String msg) {
        print("[WARNING]", msg, RED, 3);
    }
}

