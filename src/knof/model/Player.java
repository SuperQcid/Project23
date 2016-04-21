package knof.model;

import knof.gamelogic.Side;

/**
 * Created by Henk Dieter Oordt on 21-4-2016.
 */
public class Player {
    private final Side side;
    private final String name;
    private boolean isUp = false;

    public Player(String name, Side side){
        this.name = name;
        this.side = side;
    }

    public String getName(){ return name; }

    public Side getSide(){ return  side; }

    public void doMove(){
        isUp = true;
        //TODO implement
        /*
        Get a move from either AI or controller and send command
         */
    }

    public void sendMove(){
        if(isUp){
            //Send it
        }
    }
}
