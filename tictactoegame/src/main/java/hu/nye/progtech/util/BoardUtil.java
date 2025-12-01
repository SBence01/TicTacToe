package hu.nye.progtech.util;

import hu.nye.progtech.model.Board;
import hu.nye.progtech.model.Mark;
import hu.nye.progtech.model.Position;

public class BoardUtil {

    public static boolean isWithinBounds(int row, int col, Board board) {

        return row >= 0 && row < board.getRows() && col >= 0 && col < board.getCols();
    }

    public static boolean isAdjacentToExistingMark(Position position, Board board) {
        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1}, {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        for (int[] dir : directions) {
            int newRow = position.getRow() + dir[0];
            int newCol = position.getCol() + dir[1];

            if (isWithinBounds(newRow, newCol, board)) {
                Mark mark = board.getMark(new Position(newRow, newCol));
                if (mark != Mark.EMPTY) {
                    return true;
                }
            }
        }
        return false;
    }
}
