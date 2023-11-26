import javax.xml.namespace.QName;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ChessGame {
    private ChessBoard board;
    private boolean whiteTurn;

    private Scanner scanner;



    String name = "";
    public ChessGame() {
        board = new ChessBoard();
        whiteTurn = true;
        scanner = new Scanner(System.in);
    }

    public void play() {
        System.out.println("Welcome to Chess!");
        System.out.println(board);

        while (!isGameOver()) {
            System.out.println(whiteTurn ? "White's turn" : "Black's turn");
            if (isKingInCheck(board, whiteTurn ? "white" : "black"))
            {
                System.out.println("king is in check");
            }
            if (isKingInCheck(board, whiteTurn ? "black" : "white"))
            {
                System.out.println("king is in check");
            }
            if (whiteTurn) {
                // Player's turn
                System.out.print("Enter your move (e.g. e2 e4): ");
                String moveInput = scanner.nextLine().trim();

                if (moveInput.equalsIgnoreCase("quit")) {
                    System.out.println("Game ended.");
                    break;
                }

                String[] moveParts = moveInput.split(" ");
                if (moveParts.length != 2) {
                    System.out.println("Invalid move format. Try again.");
                    continue;
                }

                String fromSquare = moveParts[0];
                String toSquare = moveParts[1];

                if (!isValidSquare(fromSquare) || !isValidSquare(toSquare)) {
                    System.out.println("Invalid square format. Try again.");
                    continue;
                }

                int fromRow = fromSquare.charAt(1) - '1';
                int fromCol = fromSquare.charAt(0) - 'a';
                int toRow = toSquare.charAt(1) - '1';
                int toCol = toSquare.charAt(0) - 'a';

                Move move = new Move(fromRow, fromCol, toRow, toCol, name);
                if (!isValidMove(fromRow, fromCol, toRow, toCol)) {
                    System.out.println("Invalid move. Try again.");
                    continue;
                }

                board.makeMove(move);
                if (isKingInCheck(board, "black")) {
                    System.out.println("Your move does not get the king out of check. Try again.");
                    board.undoLastMove(); // Undo the invalid move
                    continue;
                }

                System.out.println(move.getToRow());
                whiteTurn = !whiteTurn; // Flip the turn
                System.out.println(board);
            } else {
                if (isKingInCheck(board, whiteTurn ? "black" : "white"))
                {
                    System.out.println("king is in check");
                }
                if (isKingInCheck(board, whiteTurn ? "white" : "black"))
                {
                    System.out.println("king is in check");
                }
                // AI's turn
                List<Move> legalMoves = board.generateLegalMovesForBlack(); // AI is playing black pieces
                if (legalMoves.isEmpty()) {
                    System.out.println("No legal moves for the AI. Game Over!");
                    break;
                }

                Random random = new Random();
                Move randomMove = legalMoves.get(random.nextInt(legalMoves.size()));
                board.makeMove(randomMove);
                System.out.println("AI moved: " + getAlgebraicNotation(randomMove));
                whiteTurn = !whiteTurn; // Flip the turn
                System.out.println(board);
            }
        }

        System.out.println("Game Over!");
    }

    private String getAlgebraicNotation(Move move) {
        char fromCol = (char) ('a' + move.getFromCol());
        char fromRow = (char) ('1' + move.getFromRow());
        char toCol = (char) ('a' + move.getToCol());
        char toRow = (char) ('1' + move.getToRow());
        return "" + fromCol + fromRow + " to " + toCol + toRow;
    }


    private boolean isGameOver() {
        List<Move> legalMoves = board.generateLegalMoves();

        // Check if the current player's king is in check
        boolean isKingInCheck = isKingInCheck(board, whiteTurn ? "white" : "black");

        // Check if there are no legal moves for the current player
        if (legalMoves.isEmpty() && isKingInCheck) {
            System.out.println("Checkmate! " + (whiteTurn ? "Black" : "White") + " wins.");
            return true;
        } else if (legalMoves.isEmpty()) {
            System.out.println("Stalemate! It's a draw.");
            return true;
        }

        return false;
    }

    private boolean isValidSquare(String square) {
        return square.matches("[a-h][1-8]");
    }

    private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        Piece piece = board.getPiece(fromRow, fromCol);


        // Check if there is a piece at the starting position and if it belongs to the current player
        if (piece == null || piece.isWhite() != whiteTurn) {
            System.out.println("returns true");
            return false; // Return false if there is no piece or if it's not the current player's piece
        }

        // Generate legal moves for the selected piece
        List<Move> legalMoves = piece.generateLegalMoves(fromRow, fromCol, board);

        // Check if the destination position is a valid move based on the generated legal moves
        for (Move move : legalMoves) {
            //System.out.println("legalMoves: " + move.getFromRow() +" "+ move.getFromCol() + " "+ move.getToRow() +" "+ move.getToCol() + " "+ move.getName());
        }
        for (Move move : legalMoves) {
            if (move.getToRow() == toRow && move.getToCol() == toCol) {
                return true; // Return true if the move is found in the list of legal moves
            }

        }
        // If the destination position is not a valid move, return false
        return false;
    }

    private boolean isKingInCheck(ChessBoard board, String color) {
        // Find the king's position
        int kingRow = -1;
        int kingCol = -1;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null && piece.isWhite() == (color.equals("white")) && piece instanceof King) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
            if (kingRow != -1) {
                break;
            }
        }

        // Check for attacks from opponent's pieces
        String opponentColor = color.equals("white") ? "black" : "white";
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null && piece.isWhite() == (opponentColor.equals("white"))) {
                    List<Move> pieceMoves = piece.generateLegalMoves(row, col, board);
                    for (Move move : pieceMoves) {
                        if (move.getToRow() == kingRow && move.getToCol() == kingCol) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }





    public static void main(String[] args) {
        ChessGame game = new ChessGame();
        game.play();
    }
}



