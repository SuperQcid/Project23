package knof.gamelogic.controllers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import knof.gamelogic.Board;

public abstract class PieceRenderer {
    private final Color color;
    protected final double lineWidth;
    protected final double piecePadding;

    public PieceRenderer(Color color, double lineWidth, double piecePadding) {
        this.color = color;
        this.lineWidth = lineWidth;
        this.piecePadding = piecePadding;
    }

    public void render(GraphicsContext gc, BoardGame boardGame, Board.Pos pos) {
        gc.setFill(this.color);
        gc.setStroke(this.color);
        gc.setLineWidth(lineWidth);
        this.renderShape(gc, boardGame, pos);
    }

    public abstract void renderShape(GraphicsContext gc, BoardGame boardGame, Board.Pos pos);

}
