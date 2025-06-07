package src.com.chess.game;

public class Globals {
    static int logLevel = 0;
    static int fps = 60;
    static boolean showRect = false;

    public static void print(String msg) {
        System.out.printf("%s[GLOBAL]%s %s%n", "\033[0;34m", "\033[0m", msg);
    }
    public static void printError(String msg) {
        System.out.printf("%s[GLOBAL WARNING]%s %s%n", "\033[0;31m", "\033[0m", msg);
    }

    public static void setLevel(String arg) {
        if(!arg.matches("\\d+")) {
            printError("First argument must be a number");
            return;
        }

        int n = Integer.parseInt(arg);
        print("LOG LEVEL: " + n);
        logLevel = n;
    }

    public static int getLevel() { return logLevel; }

    public static void setFps(String arg) {
        if(!arg.matches("\\d+")) {
            printError("Second argument must be a number");
            return;
        }

        int frames = Integer.parseInt(arg);
        print("FPS: " + frames);
        fps = frames;
    }

    public static int getFps() { return fps; }

    public static void setShowRect(String arg) {
        if(!arg.matches("show-rect")) {
            printError(String.format("Unknow parameter: \"%s\"", arg));
            return;
        }

        showRect = true;
        print("Show Piece Rectangle: true");
    }

    public static boolean getShowRect() {
        return showRect;
    }
}