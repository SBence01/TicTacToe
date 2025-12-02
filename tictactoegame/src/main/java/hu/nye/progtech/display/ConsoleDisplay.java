package hu.nye.progtech.display;

import java.util.List;

import hu.nye.progtech.db.HighScoreRepository;
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
        System.out.print("   ");
        for (int i = 0; i < cols; i++) {
            System.out.print(" " + (char) ('a' + i) + " ");
        }
        System.out.println();
    }

    private void printSeparator(int cols) {

        System.out.print("  +");
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

    public void displayHighScores(List<HighScoreRepository.PlayerScore> scores) {

        System.out.println();
        System.out.println("=============");
        System.out.println(" HIGH SCORES");
        System.out.println("=============");

        if (scores.isEmpty()) {
            System.out.println("No scores yet. Play a game.");
        } else {
            System.out.printf("%-20s %s%n", "Player", "Wins");
            System.out.println("-------------");

            for (HighScoreRepository.PlayerScore score : scores) {
                System.out.printf("%-20s %d%n", score.getPlayerName(), score.getWins());
            }
        }

        System.out.println();
    }
}