package hu.nye.progtech.businesslogic;

import hu.nye.progtech.model.*;
import hu.nye.progtech.util.BoardUtil;

public class GameService {

    public void placeFirstMove(Board board, Mark mark) {

        int middleRow = board.getRows() / 2;
        int middleCol = board.getCols() / 2;

        Position middlePosition = new Position(middleRow, middleCol);
        board.setMark(middlePosition, mark);
    }

    public boolean isValidMove(Position position, Game game) {

        Board board = game.getBoard();

        if (!board.isEmpty(position)) {
            return false;
        }

        if (!hasAnyMark(board)) {
            return false;
        }

        if (!BoardUtil.isAdjacentToExistingMark(position, board)) {
            return false;
        }

        return true;
    }

    public GameStatus checkWinCondition(Game game) {
        Board board = game.getBoard();
        Position lastMove = game.getLastMove();

        if (checkWin(board, Mark.X, lastMove)) {
            return GameStatus.PLAYER_WON;
        }

        if (checkWin(board, Mark.O, lastMove)) {
            return GameStatus.COMPUTER_WON;
        }

        if (!hasEmptyCell(board)) {
            return GameStatus.DRAW;
        }

        return GameStatus.IN_PROGRESS;
    }

    private boolean hasAnyMark(Board board) {
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                Mark mark = board.getMark(new Position(i, j));
                if (mark != Mark.EMPTY) {
                    return true;
                }
            }
        }
        return false;
    }



    private boolean checkWin(Board board, Mark mark, Position lastMove) {

        if (lastMove == null) {
            return false;
        }
        return checkFromPosition(lastMove.getRow(), lastMove.getCol(), board, mark);
    }

    private boolean checkFromPosition(int row, int col, Board board, Mark mark) {

        int[][] directions = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};

        for (int[] dir :directions) {
            if (checkDirection(row, col, dir[0], dir[1], board, mark)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDirection(int row, int col, int rowStep, int colStep, Board board, Mark mark) {
        int count = 0;

        for (int i = 0; i < 5; i++) {
            int newRow = row + i * rowStep;
            int newCol = col + i * colStep;

            if (!BoardUtil.isWithinBounds(newRow, newCol, board)) {
                return false;
            }

            if (board.getMark(new Position(newRow, newCol)) != mark) {
                return false;
            }

            count++;
        }

        return count == 5;
    }



    private boolean hasEmptyCell(Board board) {

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j <board.getCols(); j++) {
                if (board.getMark(new Position(i, j)) == Mark.EMPTY) {
                    return true;
                }
            }
        }
        return false;
    }
}
