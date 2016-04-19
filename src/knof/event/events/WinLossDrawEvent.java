package knof.event.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import knof.event.IEvent;

/**
 * Created by Thijs on 19/04/2016.
 */
public class WinLossDrawEvent implements IEvent {

    @JsonProperty("PLAYERONESCORE")
    public int playerOneScore;

    @JsonProperty("PLAYERTWOSCORE")
    public int playerTwoScore;

    @JsonProperty("COMMENT")
    public String comment;

    public static class Win extends WinLossDrawEvent {}
    public static class Lose extends WinLossDrawEvent {}
    public static class Draw extends WinLossDrawEvent {}

}