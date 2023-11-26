import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    String name = "Pawn";
    private boolean justMovedTwoSquares;

    public Pawn(boolean white) {
        super(white);
    }

    @Override
    public List<Move> generateLegalMoves(int row, int col, ChessBoard board) {
        List<Move> legalMoves = new ArrayList<>();

        int direction = isWhite() ? +1 : -1; // For white, direction = +1; for black, direction = -1
        int startRow = isWhite() ? 1 : 6; // For white, starting row is 1; for black, starting row is 6

        // Move one square forward
        int newRow = row + direction;
        int newCol = col;
        if (board.isSquareOnBoard(newRow, newCol) && board.isSquareEmpty(newRow, newCol)) {
            legalMoves.add(new Move(row, col, newRow, newCol, name));
        }

        // Move two squares forward from the starting position
        newRow = row + 2 * direction;
        if (row == startRow && board.isSquareOnBoard(newRow, newCol) && board.isSquareEmpty(newRow, newCol) && board.isSquareEmpty(row + direction, newCol)) {
            legalMoves.add(new Move(row, col, newRow, newCol, name));
        }

        // Capture diagonally to the left
        newRow = row + direction;
        newCol = col - 1;
        if (board.isSquareOnBoard(newRow, newCol)) {
            Piece pieceAtLeft = board.getPiece(newRow, newCol);
            if (pieceAtLeft != null && pieceAtLeft.isWhite() != isWhite()) {
                legalMoves.add(new Move(row, col, newRow, newCol, name));
            }
        }

        // Reset newRow to its original value
        newRow = row;

        // Capture diagonally to the right
        newRow = row + direction;
        newCol = col + 1;
        if (board.isSquareOnBoard(newRow, newCol)) {
            Piece pieceAtRight = board.getPiece(newRow, newCol);
            if (pieceAtRight != null && pieceAtRight.isWhite() != isWhite()) {
                legalMoves.add(new Move(row, col, newRow, newCol, name));
            }
        }

        // Reset newRow and newCol to their original values
        newRow = row;
        newCol = col;

        // Handle en passant capture
        Move lastMove = board.getLastMove();
        if (lastMove != null) {
            Piece lastMovedPiece = board.getPiece(lastMove.getToRow(), lastMove.getToCol());
            if (lastMovedPiece instanceof Pawn &&
                    Math.abs(lastMove.getFromRow() - lastMove.getToRow()) == 2 &&
                    Math.abs(lastMove.getToRow() - row) == 1 &&
                    Math.abs(lastMove.getToCol() - col) == 1) {
                // The last move was a two-square pawn move, and the current pawn can capture en passant
                legalMoves.add(new Move(row, col, lastMove.getToRow() + direction, lastMove.getToCol(), name));
            }
        }

        // Debug prints to check the generated moves for the pawn
        return legalMoves;
    }

    @Override
    public Piece clone() {
        return new Pawn(this.isWhite());
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "P" : "p";
    }
}