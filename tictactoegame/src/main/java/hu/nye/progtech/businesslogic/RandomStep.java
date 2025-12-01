package hu.nye.progtech.businesslogic;

import hu.nye.progtech.model.Board;
import hu.nye.progtech.model.Position;
import hu.nye.progtech.util.BoardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomStep {

    private final Random random = new Random();

    public Position generateMove(Board board) {
        List<Position> validPositions = new ArrayList<>();

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                Position position = new Position(i, j);
                if (board.isEmpty(position) && BoardUtil.isAdjacentToExistingMark(position, board)) {
                    validPositions.add(position);
                }
            }
        }

        if (validPositions.isEmpty()) {
            return null;
        }

        return validPositions.get(random.nextInt(validPositions.size()));
    }
}
