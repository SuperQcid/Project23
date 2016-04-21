package knof.controllers;

import knof.event.events.GameResultEvent;
import knof.gamelogic.Move;

/**
 * Created by Henk Dieter Oordt on 21-4-2016.
 */
public abstract class GameController {
    public abstract void update(Move move);

    public abstract void terminate(GameResultEvent grev);
}
