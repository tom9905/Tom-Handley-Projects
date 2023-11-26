import java.util.List;

public abstract class Piece {
    private boolean white;

    public Piece(boolean white) {
        this.white = white;
    }

    public boolean isWhite() {
        return white;
    }

    // Define an abstract method to generate legal moves for a piece
    public abstract List<Move> generateLegalMoves(int row, int col, ChessBoard board);

    // Make clone method abstract
    public abstract Piece clone();

    public abstract String getSymbol();
}
