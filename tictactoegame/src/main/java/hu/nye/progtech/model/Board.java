package hu.nye.progtech.model;

public class Board {

    private final int rows;
    private final int cols;
    private final Mark[][] grid;

    public Board(int rows, int cols, Mark[][] grid) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Mark[rows][cols];
        initializeBoard();
    }

    public void initializeBoard() {
        for (int i = 0; i <rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = Mark.EMPTY;
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Mark getMark(Position position) {
        return grid[position.getRow()][position.getCol()];
    }

    public void setMark(Position position, Mark mark) {
        grid[position.getRow()][position.getCol()] = mark;
    }

    public boolean isEmpty(Position position) {
        return getMark(position) == Mark.EMPTY;
    }

    public Mark[][] getGrid() {
        return grid;
    }

    @Override
    public String toString() {
        return "Board{" + "rows=" + rows + ", cols=" + cols + "}";
    }
}
