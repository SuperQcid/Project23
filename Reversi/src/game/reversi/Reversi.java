package game.reversi;

import knof.ai.NegaMax;
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

        playerTypes.put("Heuristic", (connection, game, side, playerName, options) -> new AIPlayer(playerName,side,connection,game) {

            NegaMax negaMax = new NegaMax(((ReversiGame) game).getBoard(), 5);

                    @Override
                    protected int calculateMove() {
                        return negaMax.getBestScoringMoveWithoutRecursion(((ReversiGame) game).getBoard(),side).toInt();
                    }
                }
        );

        playerTypes.put("Heuristic+", (connection, game, side, playerName, options) -> new AIPlayer(playerName,side,connection,game) {

                    NegaMax negaMax = new NegaMax(((ReversiGame) game).getBoard(), 5);
                    public Side getOtherSide(Side side1) {
                        if (side1.getName().equals("WHITE")) {
                            return new Side("BLACK");
                        }
                        return new Side("WHITE");
                    }

                    @Override
                    protected int calculateMove() {
                        return negaMax.getBestScoringMoveWithNegaMax(((ReversiGame) game).getBoard(), side, getOtherSide(side), 5).toInt();
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
