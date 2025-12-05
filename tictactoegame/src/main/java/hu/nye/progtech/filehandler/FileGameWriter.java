package hu.nye.progtech.filehandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import hu.nye.progtech.model.Board;
import hu.nye.progtech.model.Mark;
import hu.nye.progtech.model.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileGameWriter {

    private static final Logger logger = LoggerFactory.getLogger(FileGameWriter.class);

    public void saveBoard(Board board, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(board.getRows() + "," + board.getCols());
            writer.newLine();

            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getCols(); j++) {
                    Mark mark = board.getMark(new Position(i, j));
                    writer.write(mark.getSymbol());

                    if (j < board.getCols() - 1) {
                        writer.write(" ");
                    }
                }
                writer.newLine();
            }

            logger.info("Board saved to {}", filename);
        } catch (IOException e) {
            logger.error("Failed to save board to {}", filename, e);
        }
    }
}
