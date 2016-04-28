package knof.plugin;

import knof.connection.Connection;
import knof.controllers.GameController;
import knof.model.Game;

/**
 * Interface for game plugins.
 */
public interface Plugin {
    String getGameName();

    Game createGame(String playerOneName, String playerTwoName, boolean playerOneIsLocal, Connection connection);
}
