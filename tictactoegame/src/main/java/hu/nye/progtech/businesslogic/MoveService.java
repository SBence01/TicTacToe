package hu.nye.progtech.businesslogic;

import hu.nye.progtech.model.Board;
import hu.nye.progtech.model.Game;
import hu.nye.progtech.model.Move;

public class MoveService {

    public void applyMove(Game game, Move move) {

        Board board = game.getBoard();
        board.setMark(move.getPosition(), move.getMark());
    }

    public void switchPlayer(Game game) {

        game.switchCurrentPlayer();
    }
}
