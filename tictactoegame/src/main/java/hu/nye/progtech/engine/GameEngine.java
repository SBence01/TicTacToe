package hu.nye.progtech.engine;

import hu.nye.progtech.businesslogic.GameService;
import hu.nye.progtech.businesslogic.MoveService;
import hu.nye.progtech.businesslogic.RandomStep;
import hu.nye.progtech.command.ConsoleUI;
import hu.nye.progtech.display.ConsoleDisplay;
import hu.nye.progtech.model.Board;
import hu.nye.progtech.model.Game;
import hu.nye.progtech.model.GameStatus;
import hu.nye.progtech.model.Mark;
import hu.nye.progtech.model.Player;
import hu.nye.progtech.model.Position;
import hu.nye.progtech.util.PositionFormatter;

public class GameEngine {

    private final GameService gameService;
    private final MoveService moveService;
    private final RandomStep randomStep;
    private final ConsoleUI consoleUI;
    private final ConsoleDisplay consoleDisplay;

    private Game game;

    public GameEngine(
            GameService gameService,
            MoveService moveService,
            RandomStep randomStep,
            ConsoleUI consoleUI,
            ConsoleDisplay consoleDisplay) {
        this.gameService = gameService;
        this.moveService = moveService;
        this.randomStep = randomStep;
        this.consoleUI = consoleUI;
        this.consoleDisplay = consoleDisplay;
    }

    public void run() {
        consoleUI.showMenu();
        int choice = consoleUI.getMenuChoice();

        switch (choice) {
            case 1:
                startNewGame();
                break;
            case 2:
                loadGame();
                break;
            case 3:
                exit();
                break;
            default:
                break;
        }
    }

    private void startNewGame() {

        String playerName = consoleUI.getPlayerName();

        int rows = consoleUI.getBoardRows();
        int cols = consoleUI.getBoardCols();

        Board board = new Board(rows, cols);
        Player player = new Player(playerName, Mark.X);
        Player computer = new Player("Computer", Mark.O);
        game = new Game(player, computer, board);

        gameService.placeFirstMove(board, Mark.X);
        game.setLastMove(new Position(rows / 2, cols / 2));

        consoleDisplay.displayBoard(board);

        gameLoop();
    }

    private void gameLoop() {

        while (game.getStatus() == GameStatus.IN_PROGRESS) {
            Player currentPlayer = game.getCurrentPlayer();

            if (currentPlayer.getMark() == Mark.X) {
                executePlayerMove();
            } else {
                executeComputerMove();
            }
        }
    }

    private void executePlayerMove() {
        while (true) {
            Position position = consoleUI.getPlayerMove();

            if (gameService.isValidMove(position, game)) {
                moveService.applyMove(game, position, Mark.X);
                break;
            } else {
                consoleDisplay.displayMessage("Invalid move! Try again.");
            }
        }
    }

    private void executeComputerMove() {

        consoleDisplay.displayMessage("Computer's turn");

        Position position = randomStep.generateMove(game.getBoard());

        if (position != null) {
            moveService.applyMove(game, position, Mark.O);
            consoleDisplay.displayMessage("Computer moved: " + PositionFormatter.format(position));
        }
    }

    private void loadGame() {
        consoleDisplay.displayMessage("");
        run();
    }

    private void exit() {

        consoleDisplay.displayMessage("Thanks for playing!");
        consoleUI.close();
        System.exit(0);
    }
}