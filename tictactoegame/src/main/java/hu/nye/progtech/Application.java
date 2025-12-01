package hu.nye.progtech;

import hu.nye.progtech.businesslogic.GameService;
import hu.nye.progtech.businesslogic.MoveService;
import hu.nye.progtech.businesslogic.RandomStep;
import hu.nye.progtech.command.ConsoleUI;
import hu.nye.progtech.display.ConsoleDisplay;
import hu.nye.progtech.engine.GameEngine;

public class Application {

    public void start() {
        GameService gameService = new GameService();
        MoveService moveService = new MoveService();
        RandomStep randomStep = new RandomStep();
        ConsoleUI consoleUI = new ConsoleUI();
        ConsoleDisplay consoleDisplay = new ConsoleDisplay();

        GameEngine gameEngine = new GameEngine(
                gameService, moveService, randomStep, consoleUI, consoleDisplay
        );

        gameEngine.run();
    }
}
