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
import hu.nye.progtech.model.Move;
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

        consoleDisplay.displayBoard(board);

        moveService.switchPlayer(game);

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

            consoleDisplay.displayBoard(game.getBoard());

            GameStatus status = gameService.checkWinCondition(game);
            game.setStatus(status);

            if (status == GameStatus.PLAYER_WON) {
                consoleDisplay.displayWinner(game.getPlayer().getName());
                break;
            } else if (status == GameStatus.COMPUTER_WON) {
                consoleDisplay.displayWinner(game.getComputer().getName());
                break;
            } else if (status == GameStatus.DRAW) {
                consoleDisplay.displayDraw();
                break;
            }

            moveService.switchPlayer(game);
        }
    }

    private void executePlayerMove() {
        while (true) {
            Position position = consoleUI.getPlayerMove();

            if (gameService.isValidMove(position, game)) {
                Move move = new Move(position, Mark.X);
                moveService.applyMove(game, move);
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
            Move move = new Move(position, Mark.O);
            moveService.applyMove(game, move);
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