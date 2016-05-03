package knof.model;

import knof.command.Command;
import knof.connection.Connection;

public abstract class AIPlayer extends LocalPlayer {
    public AIPlayer(String name, String side, Connection connection, Game game) {
        super(name, side, connection, game);
    }

    @Override
    public void setTurn() {
        connection.sendCommand(Command.MOVE, calculateMove());
    }

    protected abstract int calculateMove();
}
