import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    String name = "Room";
    public Rook(boolean white) {
        super(white);
    }

    @Override
    public List<Move> generateLegalMoves(int row, int col, ChessBoard board) {
        List<Move> legalMoves = new ArrayList<>();

        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }; // Possible movement directions: up, down, left, right

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            while (board.isSquareOnBoard(newRow, newCol) && board.isSquareEmpty(newRow, newCol)) {
                legalMoves.add(new Move(row, col, newRow, newCol, name));
                newRow += direction[0];
                newCol += direction[1];
            }

            if (board.isSquareOnBoard(newRow, newCol) && board.isSquareOccupiedByOpponent(newRow, newCol)) {
                legalMoves.add(new Move(row, col, newRow, newCol, name));
            }
        }

        return legalMoves;
    }

    @Override
    public Piece clone() {
        return new Rook(this.isWhite());
    }
    @Override
    public String getSymbol() {
        return isWhite() ? "R" : "r";
    }

}