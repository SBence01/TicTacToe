package hu.nye.progtech.util;

import hu.nye.progtech.model.Board;
import hu.nye.progtech.model.Mark;
import hu.nye.progtech.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardUtilTest {

    private Board board;

    @BeforeEach
    void setup() {
        board = new Board(10, 10);
    }

    // For isWithinBounds
    @Test
    void isWithinBoundsShouldReturnTrueForValidPosition() {
        // GIVEN
        int row = 5;
        int col = 5;

        // WHEN
        boolean result = BoardUtil.isWithinBounds(row, col, board);

        // THEN
        assertTrue(result);
    }

    @Test
    void isWithinBoundsShouldReturnTrueForTopLeftCorner() {
        // GIVEN
        int row = 0;
        int col = 0;

        // WHEN
        boolean result = BoardUtil.isWithinBounds(row, col, board);

        // THEN
        assertTrue(result);
    }

    @Test
    void isWithinBoundsShouldReturnTrueForBottomRightCorner() {
        // GIVEN
        int row = 9;
        int col = 9;

        // WHEN
        boolean result = BoardUtil.isWithinBounds(row, col, board);

        // THEN
        assertTrue(result);
    }

    @Test
    void isWithinBoundsShouldReturnFalseForNegativeRow() {
        // GIVEN
        int row = -1;
        int col = 5;

        // WHEN
        boolean result = BoardUtil.isWithinBounds(row, col, board);

        // THEN
        assertFalse(result);
    }

    @Test
    void isWithinBoundsShouldReturnFalseForNegativeColumn() {
        // GIVEN
        int row = 5;
        int col = -1;

        // WHEN
        boolean result = BoardUtil.isWithinBounds(row, col, board);

        // THEN
        assertFalse(result);
    }

    @Test
    void isWithinBoundsShouldReturnFalseForRowTooLarge() {
        // GIVEN
        int row = 10;
        int col = 5;

        // WHEN
        boolean result = BoardUtil.isWithinBounds(row, col, board);

        // THEN
        assertFalse(result);
    }

    @Test
    void isWithinBoundsShouldReturnFalseForColumnTooLarge() {
        // GIVEN
        int row = 5;
        int col = 10;

        // WHEN
        boolean result = BoardUtil.isWithinBounds(row, col, board);

        // THEN
        assertFalse(result);
    }

    // For isAdjacentToExistingMark

    @Test
    void isAdjacentToExistingMarkShouldReturnTrueWhenMarkIsDiagonal() {
        // GIVEN
        Position existingMark = new Position(5, 5);
        board.setMark(existingMark, Mark.X);

        Position testPosition = new Position(6, 6);
        // WHEN
        boolean result = BoardUtil.isAdjacentToExistingMark(testPosition, board);

        // THEN
        assertTrue(result);
    }

    @Test
    void isAdjacentToExistingMarkShouldReturnFalseWhenNoAdjacentMark() {
        // GIVEN
        Position existingMark = new Position(5, 5);
        board.setMark(existingMark, Mark.X);

        Position testPosition = new Position(8, 8);
        // WHEN
        boolean result = BoardUtil.isAdjacentToExistingMark(testPosition, board);

        // THEN
        assertFalse(result);
    }
}
