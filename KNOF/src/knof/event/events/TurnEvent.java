package knof.event.events;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Henk Dieter Oordt on 19-4-2016.
 */
public class TurnEvent extends GameEvent {
    @JsonProperty("TURNMESSAGE")
    public String turnMessage;
}
