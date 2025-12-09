package hu.nye.progtech.businesslogic;

import hu.nye.progtech.model.Board;
import hu.nye.progtech.model.Mark;
import hu.nye.progtech.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RandomStepTest {

    private RandomStep underTest;
    private Board board;

    @BeforeEach
    void setup() {
        underTest = new RandomStep();
        board = new Board(10, 10);
    }

    @Test
    void generateMoveShouldReturnNullForEmptyBoard() {
        // GIVEN

        // WHEN
        Position result = underTest.generateMove(board);

        // THEN
        assertNull(result);
    }

    @Test
    void generateMoveShouldReturnTrue() {
        // GIVEN
        board.setMark(new Position(5, 5), Mark.X);

        // WHEN
        Position result = underTest.generateMove(board);

        // THEN
        assertNotNull(result);
        assertTrue(board.isEmpty(result));
    }
}
