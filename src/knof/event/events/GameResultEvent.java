package knof.event.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import knof.event.IEvent;

/**
 * Created by Thijs on 19/04/2016.
 */
public class GameResultEvent extends GameEvent {

    @JsonProperty("PLAYERONESCORE")
    public int playerOneScore;

    @JsonProperty("PLAYERTWOSCORE")
    public int playerTwoScore;

    @JsonProperty("COMMENT")
    public String comment;

    public static class Win extends GameResultEvent {}
    public static class Loss extends GameResultEvent {}
    public static class Draw extends GameResultEvent {}
    public static class Forfeit extends GameResultEvent {}

}