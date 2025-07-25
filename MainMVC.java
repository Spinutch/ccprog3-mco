import controller.GameController;

/**
 * Main entry point for the MVC-based Fatal Fantasy: Tactics game.
 * This simplified main class just instantiates the GameController and starts the game.
 */
public class MainMVC {
    public static void main(String[] args) {
        GameController gameController = new GameController();
        gameController.startGame();
    }
}
