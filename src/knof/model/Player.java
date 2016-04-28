package knof.model;

import knof.command.Command;
import knof.command.CommandTask;
import knof.connection.Connection;
import knof.controllers.ConnectionController;
import knof.gamelogic.Move;
import knof.gamelogic.Side;

/**
 * Created by Henk Dieter Oordt on 21-4-2016.
 */
public abstract class Player {
    protected final Side side;
    protected final String name;
    private boolean isUp = false;
    private Connection connection;

    public Player(String name, Side side, Connection connection){
        this.name = name;
        this.side = side;
        this.connection = connection;
    }

    public String getName(){ return name; }

    public Side getSide(){ return  side; }

    public abstract void doMove();

    public void sendMove(int move){
        if(isUp){
            Command command = Command.MOVE;
            connection.sendCommand(command, move); // TODO fix
        }
    }
}
