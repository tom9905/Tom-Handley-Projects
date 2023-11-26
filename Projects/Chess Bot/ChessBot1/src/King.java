import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    String name = "King";
    public King(boolean white) {
        super(white);
    }

    @Override
    public List<Move> generateLegalMoves(int row, int col, ChessBoard board) {
        List<Move> legalMoves = new ArrayList<>();

        // Possible king moves (including diagonals and adjacent squares)
        int[][] moves = { {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1}, {1, 0}, {1, 1} };

        for (int[] move : moves) {
            int newRow = row + move[0];
            int newCol = col + move[1];

            if (board.isSquareOnBoard(newRow, newCol) && (board.isSquareEmpty(newRow, newCol) || board.isSquareOccupiedByOpponent(newRow, newCol))) {
                legalMoves.add(new Move(row, col, newRow, newCol, name));
            }
        }

        return legalMoves;
    }
    @Override
    public Piece clone() {
        return new King(this.isWhite());
    }
    @Override
    public String getSymbol() {
        return isWhite() ? "K" : "k";
    }
}