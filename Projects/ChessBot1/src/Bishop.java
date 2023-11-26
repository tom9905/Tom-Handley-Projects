import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    String name = "Bishop";
    public Bishop(boolean white) {
        super(white);
    }

    @Override
    public List<Move> generateLegalMoves(int row, int col, ChessBoard board) {
        List<Move> legalMoves = new ArrayList<>();

        int[][] directions = { { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };

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
        return new Bishop(this.isWhite());
    }
    @Override
    public String getSymbol() {
        return isWhite() ? "B" : "b";
    }
}