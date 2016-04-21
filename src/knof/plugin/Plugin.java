package knof.plugin;

import knof.controllers.GameController;
import knof.model.Game;

/**
 * Abstract class for game plugins.
 */
public interface Plugin {
    String getGameName();
	//TODO
    public Game createGame(String playerOne, String playerTwo);

    public GameController createGameController(Game game);
}
