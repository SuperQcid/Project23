package knof.model;

import knof.connection.Connection;

/**
 * Created by Henk Dieter Oordt on 3-5-2016.
 */
public abstract class LocalPlayer extends Player {
    protected boolean isAI;
    /**
     * Game, used to get the current representation in order to calculate a move
     */
    protected Game game;

    public LocalPlayer(String name, String side, Connection connection, Game game) {
        super(name, side, connection);
        this.game = game;
    }

    public void setAI(boolean isAI){
        this.isAI = isAI;
    }

    public boolean getAI(){
        return isAI;
    }

    /**
     * Returns an AI move
     * @return
     */
    public abstract int getAIMove();
}
