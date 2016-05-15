package knof.event.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import knof.event.IEvent;

public class ChallengeEvent implements IEvent {
    @JsonProperty("CHALLENGER")
    public String challenger;
    @JsonProperty("CHALLENGENUMBER")
    public int id;
    @JsonProperty("GAMETYPE")
    public String gameType;
    @JsonProperty("TURNTIME")
    public int turnTime;

    public static class Cancel implements IEvent {
        public Cancel(){}

        @JsonProperty("CHALLENGENUMBER")
        public int id;
    }
}
