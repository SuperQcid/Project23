package knof.model.game;

import knof.command.Command;
import knof.connection.Connection;

public abstract class AIPlayer extends LocalPlayer {
    public AIPlayer(String name, Side side, Connection connection, Game game) {
        super(name, side, connection, game);
    }

    @Override
    public void setTurn() {
        connection.sendCommand(Command.MOVE, calculateMove());
    }

    protected abstract int calculateMove();
}
