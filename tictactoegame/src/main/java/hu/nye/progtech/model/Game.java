package hu.nye.progtech.model;

public class Game {

    private final Player player;
    private final Player computer;
    private final Board board;
    private Player currentPlayer;
    private GameStatus status;
    private Position lastMove;

    public Game(Player player, Player computer, Board board) {
        this.player = player;
        this.computer = computer;
        this.board = board;
        this.currentPlayer = player;
        this.status = GameStatus.IN_PROGRESS;
        this.lastMove = null;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getComputer() {
        return computer;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Position getLastMove() {
        return lastMove;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public void switchCurrentPlayer() {
        currentPlayer = currentPlayer == player ? computer : player;
    }

    public void setLastMove(Position lastMove) {
        this.lastMove = lastMove;
    }

    @Override
    public String toString() {
        return "Game{" + "player=" + player + ", computer=" + computer + ", status=" + status + ", lastMove=" + lastMove + "}";
    }
}
