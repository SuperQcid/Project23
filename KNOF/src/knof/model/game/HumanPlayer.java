package knof.model.game;

import knof.command.Command;
import knof.connection.Connection;

public class HumanPlayer extends LocalPlayer {
    private boolean ready = false;

    public HumanPlayer(String name, Side side, Connection connection, Game game) {
        super(name, side, connection, game);
    }

    public void receiveMove(int move) {
        if(this.ready) {
            this.connection.sendCommand(Command.MOVE, move);
            this.ready = false;
        }
        else {
            System.err.println("Not ready!");
        }
    }

    @Override
    public void setTurn() {
        this.ready = true;
    }
}
