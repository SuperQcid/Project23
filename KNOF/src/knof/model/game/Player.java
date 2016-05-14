package knof.model.game;

import knof.command.Command;
import knof.connection.Connection;

/**
 * Created by Henk Dieter Oordt on 21-4-2016.
 */
public abstract class Player {
    protected final Side side;
    protected final String name;
    protected boolean turn = false;
    protected Connection connection;

    public Player(String name, Side side, Connection connection){
        this.name = name;
        this.side = side;
        this.connection = connection;
    }

    public String getName(){ return name; }

    public Side getSide(){ return side; }

    /**
     * Method used to notify the player it's its turn
     */
    public abstract void setTurn();

    /**
     * When the move is retrieved, send it to the remote server.
     * @param move The move that is sent
     */
    public void sendMove(int move){
        if(turn){
            connection.sendCommand(Command.MOVE, move);
        }
    }
}
