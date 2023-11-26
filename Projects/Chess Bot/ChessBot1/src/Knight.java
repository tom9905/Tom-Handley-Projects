import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    String name = "Knight";
    public Knight(boolean white) {
        super(white);
    }

    @Override
    public List<Move> generateLegalMoves(int row, int col, ChessBoard board) {
        System.out.println("Generating legal moves for " + (isWhite() ? "White" : "Black") + " pawn at " + row + ", " + col);
        List<Move> legalMoves = new ArrayList<>();

        int[][] moves = { { -2, -1 }, { -2, 1 }, { -1, -2 }, { -1, 2 }, { 1, -2 }, { 1, 2 }, { 2, -1 }, { 2, 1 } }; // Possible knight moves

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
        return new Knight(this.isWhite());
    }
    @Override
    public String getSymbol() {
        return isWhite() ? "N" : "n";
    }
}

