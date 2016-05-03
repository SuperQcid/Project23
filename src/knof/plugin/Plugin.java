package knof.plugin;

import knof.connection.Connection;
import knof.model.Game;

/**
 * Interface for game plugins.
 */
public abstract class Plugin {
    public abstract String getGameName();

    public abstract Game createGame(String playerOneName, String playerTwoName, boolean playerOneIsLocal, Connection connection);
}
