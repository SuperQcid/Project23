package knof.event.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import knof.event.IEvent;

/**
 * Created by Henk Dieter en Thijs on 19-4-2016.
 */
public class MoveEvent implements IEvent {
    @JsonProperty("PLAYER")
    public String player;

    @JsonProperty("DETAILS")
    public String details;

    @JsonProperty("MOVE")
    public int move;
}
