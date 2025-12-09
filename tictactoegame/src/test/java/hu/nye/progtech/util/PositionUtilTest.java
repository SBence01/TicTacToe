package hu.nye.progtech.util;

import hu.nye.progtech.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PositionUtilTest {

    @Test
    void formatShouldReturnCorrectStringForPositionA1() {
        // Given
        Position position = new Position(0, 0);

        // WHEN
        String result = PositionUtil.format(position);

        // THEN
        assertEquals("a1", result);
    }

    @Test
    void parseShouldReturnCorrectPositionForA1() {
        // GIVEN
        String input = "a1";

        // WHEN
        Position result = PositionUtil.parse(input);

        // THEN
        assertEquals(new Position(0, 0), result);
    }

    @Test
    void parseShouldReturnNullForInvalidInput() {
        // GIVEN
        String input = "invalid";

        // WHEN
        Position result = PositionUtil.parse(input);

        // THEN
        assertNull(result);
    }

    @Test
    void parseShouldReturnNullForEmptyString() {
        // GIVEN
        String input = "";

        // WHEN
        Position result = PositionUtil.parse(input);

        // THEN
        assertNull(result);
    }

    @Test
    void parseShouldReturnNullForSpecialCharacter() {
        // GIVEN
        String input = "@5";

        // WHEN
        Position result = PositionUtil.parse(input);

        // THEN
        assertNull(result);
    }
}
