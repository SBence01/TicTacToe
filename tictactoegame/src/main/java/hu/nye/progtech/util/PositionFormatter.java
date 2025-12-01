package hu.nye.progtech.util;

import hu.nye.progtech.model.Position;

public class PositionFormatter {

    /*
     Converts a position to a string format
     */
    public static String format(Position position) {
        char col = (char) ('a' + position.getCol());
        int row = position.getRow() + 1;
        return "" + col + row;
    }

    /*
     Converts a string input to a position
     */
    public static Position parse(String input) {
        if (input == null || input.length() < 2) {
            return null;
        }

        input = input.trim().toLowerCase();

        char colChar = input.charAt(0);
        String rowString = input.substring(1);

        if (colChar < 'a' || colChar > 'z') {
            return null;
        }

        try {
            int row = Integer.parseInt(rowString) - 1;
            int col = colChar - 'a';
            return new Position(row, col);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}