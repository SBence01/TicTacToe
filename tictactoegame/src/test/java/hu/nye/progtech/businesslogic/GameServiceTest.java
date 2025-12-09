package hu.nye.progtech.businesslogic;

import hu.nye.progtech.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {

    private GameService underTest;
    private Board board;

    @BeforeEach
    void setup() {
        underTest = new GameService();
        board = new Board(10, 10);
    }

    @Test
    void placeFirstMoveShouldPlaceInMiddle() {
        // GIVEN
        Mark mark = Mark.X;

        // WHEN
        underTest.placeFirstMove(board, mark);

        // THEN
        assertEquals(Mark.X, board.getMark(new Position(5, 5)));
    }

    @Test
    void isValidMoveShouldReturnTrue() {
        // GIVEN
        Position existingPosition = new Position(5, 5);
        board.setMark(existingPosition, Mark.X);

        Position adjacentPosition = new Position(6, 6);

        Player player = new Player("Player", Mark.X);
        Player computer = new Player("Computer", Mark.O);
        Game game = new Game(player, computer, board);

        // WHEN
        boolean result = underTest.isValidMove(adjacentPosition, game);

        // THEN
        assertTrue(result);
    }

    @Test
    void isValidMoveShouldReturnFalse() {

        // GIVEN
        Position position = new Position(5, 5);

        Player player = new Player("Player", Mark.X);
        Player computer = new Player("Computer", Mark.O);
        Game game = new Game(player, computer, board);

        // WHEN
        boolean result = underTest.isValidMove(position, game);

        // THEN
        assertFalse(result);
    }

    @Test
    void checkWinConditionShouldReturnPlayerWon() {

        // GIVEN
        for (int i = 0; i < 5; i++) {
            board.setMark(new Position(5, i), Mark.X);
        }

        Player player = new Player("Player", Mark.X);
        Player computer = new Player("Computer", Mark.O);
        Game game = new Game(player, computer, board);

        // WHEN
        GameStatus result = underTest.checkWinCondition(game);

        // THEN
        assertEquals(GameStatus.PLAYER_WON, result);
    }

    @Test
    void checkWinConditionShouldReturnComputerWon() {

        // GIVEN
        for (int i = 0; i < 5; i++) {
            board.setMark(new Position(5, i), Mark.O);
        }

        Player player = new Player("Player", Mark.X);
        Player computer = new Player("Computer", Mark.O);
        Game game = new Game(player, computer, board);

        // WHEN
        GameStatus result = underTest.checkWinCondition(game);

        // THEN
        assertEquals(GameStatus.COMPUTER_WON, result);
    }

    @Test
    void checkWinConditionShouldReturnInProgress() {

        // GIVEN
        board.setMark(new Position(5, 5), Mark.X);
        board.setMark(new Position(6, 6), Mark.O);

        Player player = new Player("Player", Mark.X);
        Player computer = new Player("Computer", Mark.O);
        Game game = new Game(player, computer, board);

        // WHEN
        GameStatus result = underTest.checkWinCondition(game);

        // THEN
        assertEquals(GameStatus.IN_PROGRESS, result);
    }
}
