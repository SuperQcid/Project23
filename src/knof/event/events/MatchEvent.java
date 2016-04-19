package knof.event.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import knof.event.IEvent;

/**
 * Created by Henk Dieter Oordt on 19-4-2016.
 */
public class MatchEvent implements IEvent {
    @JsonProperty("GAMETYPE")
    public String gameType;

    @JsonProperty("PLAYERTOMOVE")
    public String playerToMove;

    @JsonProperty("OPPONENT")
    public String opponent;
}
