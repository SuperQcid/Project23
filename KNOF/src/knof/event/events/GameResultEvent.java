package knof.event.events;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public String getMessage() {
        return "";
    }

    public static class Win extends GameResultEvent {
        @Override
        public String getMessage() {
            return "You won!";
        }
    }
    public static class Loss extends GameResultEvent {
        @Override
        public String getMessage() {
            return "You lost!";
        }
    }
    public static class Draw extends GameResultEvent {
        @Override
        public String getMessage() {
            return "The game is a draw!";
        }
    }
    public static class Forfeit extends GameResultEvent {
        @Override
        public String getMessage() {
            return "Someone forfeited!";
        }
    }

}