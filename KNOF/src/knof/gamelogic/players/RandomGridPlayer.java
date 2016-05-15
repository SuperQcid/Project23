package knof.gamelogic.players;

import knof.command.Command;
import knof.connection.Connection;
import knof.gamelogic.Board;
import knof.gamelogic.GridGame;
import knof.model.game.Player;
import knof.model.game.Side;

import java.util.List;
import java.util.Random;

public class RandomGridPlayer extends Player {
    private final GridGame game;
    private final Random random = new Random();

    public RandomGridPlayer(String name, Side side, Connection connection, GridGame game) {
        super(name, side, connection);
        this.game = game;
    }

    @Override
    public void setTurn() {
        List<Board.Pos> positions = game.getBoard().getValidPositions(this.side);
        Board.Pos pos = positions.get(random.nextInt(positions.size()));
        this.connection.sendCommand(Command.MOVE, pos.toInt());
    }
}
