import java.util.Objects;

public class Move {
    private int fromRow;
    private int fromCol;
    private int toRow;
    private int toCol;

    private String name;

    public Move(int fromRow, int fromCol, int toRow, int toCol, String name) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.name = name;
    }

    public int getFromRow() {
        return fromRow;
    }

    public int getFromCol() {
        return fromCol;
    }

    public int getToRow() {
        return toRow;
    }

    public int getToCol() {
        return toCol;
    }

    public String getName()
    {
        return name;
    }
}
