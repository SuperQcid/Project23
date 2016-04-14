package knof.event.events;

import knof.event.Event;
import knof.event.JsonField;

public class ChallengeEvent extends Event {
    @JsonField
    public String challenger;
    @JsonField(reparse = true)
    public int challengeNumber;
    @JsonField
    public String gameType;
    @JsonField(reparse = true)
    public int turnTime;

    public class Cancel extends Event {
        @JsonField
        public int challengeNumber;
    }
}
