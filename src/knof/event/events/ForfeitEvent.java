package knof.event.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import knof.event.IEvent;

/**
 * Created by Henk Dieter Oordt on 19-4-2016.
 */
public class ForfeitEvent implements IEvent {
    @JsonProperty("PLAYERONESCORE")
    public int playerOneScore;

    @JsonProperty("PLAYERTWOSCORE")
    public int playerTwoScore;

    @JsonProperty("COMMENT")
    public String comment;
}
