import java.util.ArrayList;
import java.util.List;

public class ChessBoard {
    private Piece[][] board;
    private boolean whiteTurn;

    private Move lastMove;

    public ChessBoard() {
        board = new Piece[8][8];
        initializeBoard();
        whiteTurn = true;
    }



    private void initializeBoard() {

        board[0][0] = new Rook(true);
        board[0][1] = new Knight(true);
        board[0][2] = new Bishop(true);
        board[0][3] = new Queen(true);
        board[0][4] = new King(true);
        board[0][5] = new Bishop(true);
        board[0][6] = new Knight(true);
        board[0][7] = new Rook(true);

        board[7][0] = new Rook(false);
        board[7][1] = new Knight(false);
        board[7][2] = new Bishop(false);
        board[7][3] = new Queen(false);
        board[7][4] = new King(false);
        board[7][5] = new Bishop(false);
        board[7][6] = new Knight(false);
        board[7][7] = new Rook(false);

        // Set up pawns
        for (int col = 0; col < 8; col++) {
            board[1][col] = new Pawn(true);
            board[6][col] = new Pawn(false);
        }
    }

    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    public List<Move> generateLegalMoves() {
        List<Move> legalMoves = new ArrayList<>();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece.isWhite() == whiteTurn) {
                    List<Move> pieceMoves = piece.generateLegalMoves(row, col, this);
                    legalMoves.addAll(pieceMoves);
                }
            }
        }

        return legalMoves;
    }

    public int evaluateBoard() {
        int score = 0;
        // Add code here to evaluate the board position and return a score
        // based on material count and board position.
        return score;
    }


    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    // Helper methods to check if a square is within the board boundaries
    protected boolean isSquareOnBoard(int row, int col) {
        //System.out.println("square on board test");
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    protected boolean isSquareEmpty(int row, int col) {
        //System.out.println("is square empty test");
        return isSquareOnBoard(row, col) && board[row][col] == null;
    }

    protected boolean isSquareOccupiedByOpponent(int row, int col) {
        return isSquareOnBoard(row, col) && board[row][col] != null &&
                board[row][col].isWhite() != whiteTurn;
    }

    public List<Move> generateLegalMovesForBlack() {

        List<Move> legalMoves = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && !piece.isWhite()) {
                    List<Move> pieceMoves = piece.generateLegalMoves(row, col, this);
                    legalMoves.addAll(pieceMoves);
                }
            }
        }
        //System.out.println("below is returned loegal moves from generateloegalmovesforblack");
        //System.out.println(legalMoves.toArray().length);
        return legalMoves;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  a b c d e f g h \n"); // Changed the order of letters
        for (int row = 7; row >= 0; row--) { // Changed the loop order to print rows in reverse
            sb.append((row + 1) + " ");
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece == null) {
                    sb.append(". ");
                } else {
                    sb.append(piece.getSymbol() + " ");
                }
            }
            sb.append("\n");
        }
        sb.append("  a b c d e f g h \n"); // Changed the order of letters
        return sb.toString();
    }

    public void makeMove(Move move) {
        int fromRow = move.getFromRow();
        int fromCol = move.getFromCol();
        int toRow = move.getToRow();
        int toCol = move.getToCol();

        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = null;

        // Update the lastMove field
        lastMove = move;

        whiteTurn = !whiteTurn;
    }

    // Add a getter method for the lastMove field
    public Move getLastMove() {
        return lastMove;
    }

    public void undoLastMove() {
        if (lastMove != null) {
            int fromRow = lastMove.getFromRow();
            int fromCol = lastMove.getFromCol();
            int toRow = lastMove.getToRow();
            int toCol = lastMove.getToCol();

            Piece movedPiece = getPiece(toRow, toCol);
            setPiece(toRow, toCol, null); // Remove the piece from its current position
            setPiece(fromRow, fromCol, movedPiece); // Place the piece back to its original position

            // Flip the turn
            whiteTurn = !whiteTurn;

            // Clear the lastMove field, as the move has been undone
            lastMove = null;
        }
    }

    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }
    public ChessBoard clone() {
        ChessBoard clonedBoard = new ChessBoard();

        // Copy the state of the board (assuming a 2D array of Piece objects)
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null) {
                    // Create a new instance of the piece and place it on the cloned board
                    clonedBoard.board[row][col] = piece.clone(); // Assuming Piece has a clone method
                }
            }
        }

        // Copy other properties like 'whiteTurn' or any other board state information

        return clonedBoard;
    }



    public static void main(String[] args) {
    }
    // Other methods for playing chess can be added here
}