package knof.model;

import javafx.application.Platform;
import knof.gamelogic.Side;

/**
 * Created by Henk Dieter Oordt on 21-4-2016.
 */
public class Player {
    public String name;
    public Side side;
    public Player(String name, Side side){
        this.name = name;
        this.side = side;
    }
}
