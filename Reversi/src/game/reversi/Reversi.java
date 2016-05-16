package game.reversi;

import knof.connection.Connection;
import knof.gamelogic.HumanGridGamePlayer;
import knof.gamelogic.players.RandomGridPlayer;
import knof.model.game.Game;
import knof.plugin.Plugin;

public class Reversi extends Plugin {
    public Reversi() {
        playerTypes.put(
                "human",
                (connection, game, side, playerName, options) -> new HumanGridGamePlayer<>(
                        playerName,
                        side,
                        connection,
                        (ReversiGame)game,
                        true
                )
        );
        playerTypes.put(
                "random",
                (connection, game, side, playerName, options) -> new RandomGridPlayer(
                        playerName,
                        side,
                        connection,
                        (ReversiGame)game
                )
        );
        playerTypes.put(
                "cornerLove",
                (connection, game, side, playerName, options) -> new ReversiCornerPlayer(
                        playerName,
                        side,
                        connection,
                        (ReversiGame) game
                )
        );
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
