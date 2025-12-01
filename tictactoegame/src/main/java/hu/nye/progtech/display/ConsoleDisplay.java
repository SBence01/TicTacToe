package hu.nye.progtech.display;

import hu.nye.progtech.model.Board;
import hu.nye.progtech.model.Mark;
import hu.nye.progtech.model.Position;

public class ConsoleDisplay {

    public void displayBoard(Board board) {
        System.out.println();
        printColumnHeaders(board.getCols());
        printSeparator(board.getCols());

        for (int i = 0; i < board.getRows(); i++) {
            printRow(i, board);
        }

        System.out.println();
    }

    private void printColumnHeaders(int cols) {
        System.out.print(" ");
        for (int i = 0; i < cols; i++) {
            System.out.print(" " + (char) ('a' + i) + " ");
        }
        System.out.println();
    }

    private void printSeparator(int cols) {

        System.out.print(" +");
        for (int i = 0; i < cols; i++) {
            System.out.print("---");
        }
        System.out.println("+");
    }

    private void printRow(int rowIndex, Board board) {

        System.out.printf("%2d|", rowIndex + 1);

        for (int i = 0; i < board.getCols(); i++) {
            Mark mark = board.getMark(new Position(rowIndex, i));
            System.out.print(" " + mark.getSymbol() + " ");
        }

        System.out.println("|");
    }

    public void displayMessage(String message) {

        System.out.println(message);
    }

    public void displayWinner(String winnerName) {

        System.out.println(winnerName + " WINS!");
    }

    public void displayDraw() {

        System.out.println("DRAW!");
    }
}