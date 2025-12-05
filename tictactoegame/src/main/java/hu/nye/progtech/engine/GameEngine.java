package hu.nye.progtech.engine;

import java.util.List;

import hu.nye.progtech.businesslogic.GameService;
import hu.nye.progtech.businesslogic.MoveService;
import hu.nye.progtech.businesslogic.RandomStep;
import hu.nye.progtech.command.ConsoleUI;
import hu.nye.progtech.db.HighScoreRepository;
import hu.nye.progtech.display.ConsoleDisplay;
import hu.nye.progtech.filehandler.FileGameLoader;
import hu.nye.progtech.filehandler.FileGameWriter;
import hu.nye.progtech.model.Board;
import hu.nye.progtech.model.Game;
import hu.nye.progtech.model.GameStatus;
import hu.nye.progtech.model.Mark;
import hu.nye.progtech.model.Move;
import hu.nye.progtech.model.Player;
import hu.nye.progtech.model.Position;
import hu.nye.progtech.util.PositionUtil;

public class GameEngine {

    private final GameService gameService;
    private final MoveService moveService;
    private final RandomStep randomStep;
    private final ConsoleUI consoleUI;
    private final ConsoleDisplay consoleDisplay;
    private final HighScoreRepository highScoreRepository;
    private final FileGameLoader fileGameLoader;
    private final FileGameWriter fileGameWriter;

    private static final String SAVE_FILE = "saved_game.txt";

    private Game game;

    public GameEngine(
            GameService gameService,
            MoveService moveService,
            RandomStep randomStep,
            ConsoleUI consoleUI,
            ConsoleDisplay consoleDisplay,
            HighScoreRepository highScoreRepository,
            FileGameLoader fileGameLoader,
            FileGameWriter fileGameWriter) {
        this.gameService = gameService;
        this.moveService = moveService;
        this.randomStep = randomStep;
        this.consoleUI = consoleUI;
        this.consoleDisplay = consoleDisplay;
        this.highScoreRepository = highScoreRepository;
        this.fileGameLoader = fileGameLoader;
        this.fileGameWriter = fileGameWriter;
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
                showHighScores();
                break;
            case 4:
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

    private void loadGame() {

        Board board = fileGameLoader.loadBoard(SAVE_FILE);

        if (board == null) {
            consoleDisplay.displayMessage("Failed to load game. Starting new game instead.");
            startNewGame();
            return;
        }

        String playerName = consoleUI.getPlayerName();
        Player player = new Player(playerName, Mark.X);
        Player computer = new Player("Computer", Mark.O);
        game = new Game(player, computer, board);

        consoleDisplay.displayMessage("Game loaded successfully!");
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

            consoleDisplay.displayBoard(game.getBoard());

            GameStatus status = gameService.checkWinCondition(game);
            game.setStatus(status);

            if (status == GameStatus.PLAYER_WON) {
                consoleDisplay.displayWinner(game.getPlayer().getName());
                highScoreRepository.saveWin(game.getPlayer().getName());
                break;
            } else if (status == GameStatus.COMPUTER_WON) {
                consoleDisplay.displayWinner(game.getComputer().getName());
                highScoreRepository.saveWin(game.getPlayer().getName());
                break;
            } else if (status == GameStatus.DRAW) {
                consoleDisplay.displayDraw();
                break;
            }

            moveService.switchPlayer(game);
        }

        run();
    }

    private void executePlayerMove() {
        String action = consoleUI.getPlayerAction();

        switch (action) {
            case "1":
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
                break;

            case "2":
                fileGameWriter.saveBoard(game.getBoard(), SAVE_FILE);
                consoleDisplay.displayMessage("Game saved to " + SAVE_FILE);
                consoleDisplay.displayMessage("Thanks for playing!");
                consoleUI.close();
                System.exit(0);
                break;

            case "3":
                consoleDisplay.displayMessage("Thanks for playing!");
                consoleUI.close();
                System.exit(0);
                break;

            default:
                break;
        }

    }

    private void executeComputerMove() {

        consoleDisplay.displayMessage("Computer's turn");

        Position position = randomStep.generateMove(game.getBoard());

        if (position != null) {
            Move move = new Move(position, Mark.O);
            moveService.applyMove(game, move);
            consoleDisplay.displayMessage("Computer moved: " + PositionUtil.format(position));
        }
    }

    private void showHighScores() {

        List<HighScoreRepository.PlayerScore> scores = highScoreRepository.getHighScores();
        consoleDisplay.displayHighScores(scores);
        run();
    }

    private void exit() {

        consoleDisplay.displayMessage("Thanks for playing!");
        consoleUI.close();
        System.exit(0);
    }
}