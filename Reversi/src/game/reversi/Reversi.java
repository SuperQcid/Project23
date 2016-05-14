package game.reversi;

import knof.connection.Connection;
import knof.model.game.Game;
import knof.model.game.HumanPlayer;
import knof.plugin.Plugin;

public class Reversi extends Plugin {
    public Reversi() {
        playerTypes.put("human", (connection, game, side, playerName, options) -> new HumanPlayer(playerName, side, connection, game));
    }

    @Override
    public String getGameName() {
        return "Reversi";
    }

    @Override
    public Game createGame(Connection connection) {
        return new ReversiGame(connection);
    }

    @Override
    public String getPlayerTypeName() {
        return "human";
    }
}
