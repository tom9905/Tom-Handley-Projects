import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    String name = "Queen";
    public Queen(boolean white) {
        super(white);
    }

    @Override
    public List<Move> generateLegalMoves(int row, int col, ChessBoard board) {
        List<Move> legalMoves = new ArrayList<>();

        int[][] directions = {
                { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, // Rook-like moves
                { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } // Bishop-like moves
        };

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            while (board.isSquareOnBoard(newRow, newCol)) {
                if (board.isSquareEmpty(newRow, newCol)) {
                    legalMoves.add(new Move(row, col, newRow, newCol, name));
                } else {
                    if (board.isSquareOccupiedByOpponent(newRow, newCol)) {
                        legalMoves.add(new Move(row, col, newRow, newCol, name));
                    }
                    break; // Stop searching in this direction if there is a piece blocking
                }

                newRow += dir[0];
                newCol += dir[1];
            }
        }

        return legalMoves;
    }

    @Override
    public Piece clone() {
        return new Queen(this.isWhite());
    }
    @Override
    public String getSymbol() {
        return isWhite() ? "Q" : "q";
    }
}
