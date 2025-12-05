package hu.nye.progtech.filehandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import hu.nye.progtech.model.Board;
import hu.nye.progtech.model.Mark;
import hu.nye.progtech.model.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileGameLoader {

    private static final Logger logger = LoggerFactory.getLogger(FileGameLoader.class);

    public Board loadBoard(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String sizeLine = reader.readLine();
            String[] size = sizeLine.split(",");
            int rows = Integer.parseInt(size[0].trim());
            int cols = Integer.parseInt(size[1].trim());

            Board board = new Board(rows, cols);

            for (int i = 0; i < rows; i++) {
                String line = reader.readLine();
                String[] cells = line.split(" ");

                for (int j = 0; j < cols; j++) {
                    char symbol = cells[j].charAt(0);
                    Mark mark = getMark(symbol);
                    board.setMark(new Position(i, j), mark);
                }
            }

            logger.info("Board loaded from {}", filename);
            return board;
        } catch (IOException e) {
            logger.error("Failed to load board from {}", filename, e);
            return null;
        }
    }

    private Mark getMark(char symbol) {
        for (Mark mark : Mark.values()) {
            if (mark.getSymbol() == symbol) {
                return mark;
            }
        }
        return Mark.EMPTY;
    }
}
