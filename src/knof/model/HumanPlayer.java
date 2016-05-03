package knof.model;

import knof.command.Command;
import knof.connection.Connection;

public class HumanPlayer extends Player {
    private boolean ready = false;

    public HumanPlayer(String name, String side, Connection connection) {
        super(name, side, connection);
    }

    public void receiveMove(int move) {
        if(this.ready) {
            this.connection.sendCommand(Command.MOVE, move);
            this.ready = false;
        }
    }

    @Override
    public void setTurn() {
        this.ready = true;
    }
}
