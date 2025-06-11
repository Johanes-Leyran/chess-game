package src.com.chess.game;


public class GameState {
    String state;

    public GameState(String state) { this.state = state; }

    public void setState(String state) { this.state = state; }

    public String getState() {
        return this.state;
    }
}
