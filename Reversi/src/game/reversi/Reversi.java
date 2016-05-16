package game.reversi;

import knof.ai.HeuristicAI;
import knof.connection.Connection;
import knof.gamelogic.HumanGridGamePlayer;
import knof.gamelogic.players.RandomGridPlayer;
import knof.model.game.AIPlayer;
import knof.model.game.Game;
import knof.model.game.Side;
import knof.plugin.Plugin;

public class Reversi extends Plugin {
    public Reversi() {
        playerTypes.put(
                "human",
                (connection, game, side, playerName, options) -> new HumanGridGamePlayer<>(
                        playerName,
                        side,
                        connection,
                        (ReversiGame) game,
                        true
                )
        );
        playerTypes.put(
                "random",
                (connection, game, side, playerName, options) -> new RandomGridPlayer(
                        playerName,
                        side,
                        connection,
                        (ReversiGame) game
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

        playerTypes.put("Heuristic", (connection, game, side, playerName, options) -> new AIPlayer(playerName, side, connection, game) {

                    HeuristicAI ai = new HeuristicAI();

                    @Override
                    protected int calculateMove() {
                        return ai.getHeuristicMove(((ReversiGame) game).getBoard(), side).toInt();
                    }
                }
        );

        playerTypes.put("Heuristic+", (connection, game, side, playerName, options) -> new AIPlayer(playerName, side, connection, game) {

                    HeuristicBacktracker ai = new HeuristicBacktracker(side, getOtherSide(side), (((ReversiGame) game).getBoard()));

                    public Side getOtherSide(Side side) {
                        if (game.getSide1().getName().equals(side.getName())) {
                            return game.getSide2();
                        }
                        return game.getSide1();
                    }

                    @Override
                    protected int calculateMove() {
                        return ai.getBestMove(5).toInt();
                    }
                }
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
