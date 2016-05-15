package knof.model.game;

import knof.event.events.GameResultEvent;

import java.util.HashMap;
import java.util.Map;

public class GameResult {
    public final String message;
    public final int playerOneScore, playerTwoScore;
    public final Result result;

    private static final Map<Class<? extends GameResultEvent>, Result> lookup;
    static {
        lookup = new HashMap<>();
        lookup.put(GameResultEvent.Win.class, Result.WIN);
        lookup.put(GameResultEvent.Loss.class, Result.LOSS);
        lookup.put(GameResultEvent.Draw.class, Result.DRAW);
        lookup.put(GameResultEvent.Forfeit.class, Result.FORFEIT);
    }

    public GameResult(GameResultEvent ev) {
        this.message = ev.comment;
        this.playerOneScore = ev.playerOneScore;
        this.playerTwoScore = ev.playerTwoScore;
        this.result = lookup.get(ev.getClass());
    }

    public enum Result {
        WIN,
        LOSS,
        DRAW,
        FORFEIT
    }
}
