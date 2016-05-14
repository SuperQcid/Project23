package knof.plugin;

import knof.connection.Connection;
import knof.model.game.Side;
import knof.model.game.Game;
import knof.model.game.Player;

import java.util.HashMap;
import java.util.Set;

/**
 * Interface for game plugins.
 */
public abstract class Plugin {
    protected HashMap<String, PlayerFactory> playerTypes = new HashMap<>();

    public abstract String getGameName();

    public abstract Game createGame(String playerOneName, String playerTwoName, boolean playerOneIsLocal, Connection connection);

    /**
     * Return a list of names possible AI players
     * @return list of names
     */
    public Set<String> getPlayerTypes(boolean includeHumanPlayer) {
        Set<String> typeNames = playerTypes.keySet();
        if(!includeHumanPlayer) {
            typeNames.remove(getPlayerTypeName());
        }
        return typeNames;
    }

    /**
     * Type name of player type
     * @return type name
     */
    public abstract String getPlayerTypeName();

    /**
     * Create player
     * @param type type of player
     * @param connection connection to server
     * @param game game to play
     * @param playerName username of player
     * @return player
     */
    public Player createPlayer(String type, Connection connection, Game game, Side side, String playerName) {
        PlayerFactory factory = playerTypes.get(type);
        if(factory==null) return null;

        return factory.createPlayer(connection, game, side, playerName);
    }

    @FunctionalInterface
    public interface PlayerFactory {
        Player createPlayer(Connection connection, Game game, Side side, String playerName, Object... options);
    }
}
