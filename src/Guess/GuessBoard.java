package guess;

import knof.gamelogic.Board;
import knof.gamelogic.Move;
import knof.gamelogic.Side;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Henk Dieter Oordt on 25-4-2016.
 */
public class GuessBoard extends Board {
    private List<Move> legalMoves = new ArrayList<>();
    Map<Side, Integer> scores = Collections.emptyMap();

    public GuessBoard(int width, int height) {
        super(width, height);
        for(int i = 0; i < width; i++){
            this.legalMoves.add(new Move(i, 1, Side.PLAYERONE));
            this.legalMoves.add(new Move(i, 1, Side.PLAYERTWO));
        }
    }

    @Override
    public List<Move> getLegalMoves(Side side) {
        return legalMoves;
    }

    @Override
    public int getScore(Side side) {
        return scores.get(side);
    }
}
