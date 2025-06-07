package src.com.chess.game;

public class Globals {
    static int logLevel = 0;
    static int fps = 60;

    public static void print(String msg) {
        System.out.printf("%s[GLOBAL]%s: %s%n", "\033[0;100m", "\033[0m", msg);
    }

    public static void setLevel(int n) {
        print("Set log level to: " + n);
        logLevel = n;
    }

    public static int getLevel() {
        return logLevel;
    }

    public static void setFps(int frames) {
        print("Set FPS level to: " + frames);
        fps = frames;
    }

    public static int getFps () {
        return fps;
    }
}