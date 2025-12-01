package hu.nye.progtech.businesslogic;

import hu.nye.progtech.model.Board;
import hu.nye.progtech.model.Game;
import hu.nye.progtech.model.Mark;
import hu.nye.progtech.model.Position;

public class MoveService {

    public void applyMove(Game game, Position position, Mark mark) {

        Board board = game.getBoard();
        board.setMark(position, mark);
        game.setLastMove(position);
    }

    public void switchPlayer(Game game) {

        game.switchCurrentPlayer();
    }
}
