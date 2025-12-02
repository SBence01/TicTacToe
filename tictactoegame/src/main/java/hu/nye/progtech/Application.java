package hu.nye.progtech;

import java.sql.SQLException;

import hu.nye.progtech.businesslogic.GameService;
import hu.nye.progtech.businesslogic.MoveService;
import hu.nye.progtech.businesslogic.RandomStep;
import hu.nye.progtech.command.ConsoleUI;
import hu.nye.progtech.db.DatabaseManager;
import hu.nye.progtech.db.HighScoreRepository;
import hu.nye.progtech.display.ConsoleDisplay;
import hu.nye.progtech.engine.GameEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public void start() {
        DatabaseManager databaseManager = new DatabaseManager();
        try {
            databaseManager.connect();
            logger.info("Database connected successfully");
        } catch (SQLException e) {
            logger.error("Failed to connect to database", e);
            return;
        }

        HighScoreRepository highScoreRepository = new HighScoreRepository(databaseManager);

        GameService gameService = new GameService();
        MoveService moveService = new MoveService();
        RandomStep randomStep = new RandomStep();
        ConsoleUI consoleUI = new ConsoleUI();
        ConsoleDisplay consoleDisplay = new ConsoleDisplay();

        GameEngine gameEngine = new GameEngine(
                gameService, moveService, randomStep, consoleUI, consoleDisplay, highScoreRepository
        );

        gameEngine.run();

        databaseManager.close();
        consoleUI.close();
    }
}
