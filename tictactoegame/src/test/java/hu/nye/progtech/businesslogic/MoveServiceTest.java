package hu.nye.progtech.businesslogic;

import hu.nye.progtech.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveServiceTest {

    private MoveService underTest;
    private Board board;
    private Game game;
    private Player player;
    private Player computer;

    @BeforeEach
    void setup() {
        underTest = new MoveService();
        board = new Board(10, 10);
        player = new Player("Player", Mark.X);
        computer = new Player("Computer", Mark.O);
        game = new Game(player, computer, board);
    }

    @Test
    void applyMoveShouldPlaceMarkOnBoard() {
        // GIVEN
        Position position = new Position(5, 5);
        Move move = new Move(position, Mark.X);

        // WHEN
        underTest.applyMove(game, move);

        // THEN
        assertEquals(Mark.X, board.getMark(position));
    }

    @Test
    void switchPlayerShouldSwitchFromPlayerToComputer() {
        // GIVEN
        assertEquals(player, game.getCurrentPlayer());

        // WHEN
        underTest.switchPlayer(game);

        // THEN
        assertEquals(computer, game.getCurrentPlayer());
    }
}
