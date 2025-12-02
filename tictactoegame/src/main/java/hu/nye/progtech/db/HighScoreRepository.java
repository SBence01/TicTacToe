package hu.nye.progtech.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HighScoreRepository {

    private static final Logger logger = LoggerFactory.getLogger(HighScoreRepository.class);
    private final DatabaseManager databaseManager;

    public HighScoreRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void saveWin(String playerName) {

        try {
            if (playerExists(playerName)) {
                incrementWins(playerName);
            } else {
                insertNewPlayer(playerName);
            }
        } catch (SQLException e) {
            logger.error("Failed to save win for player: {}", playerName, e);
        }
    }

    private boolean playerExists(String playerName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM high_scores WHERE player_name = ?";
        Connection connection = databaseManager.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, playerName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        }
        return false;
    }

    private void incrementWins(String playerName) throws SQLException {
        String sql = "UPDATE high_scores SET wins = wins + 1 WHERE player_name = ?";
        Connection connection = databaseManager.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, playerName);
            preparedStatement.executeUpdate();
        }
    }

    private void insertNewPlayer(String playerName) throws SQLException {
        String sql = "INSERT INTO high_scores (player_name, wins) VALUES (?, 1)";
        Connection connection = databaseManager.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, playerName);
            preparedStatement.executeUpdate();
        }
    }

    public List<PlayerScore> getHighScores() {

        List<PlayerScore> scores = new ArrayList<>();
        String sql = "SELECT player_name, wins FROM high_scores ORDER BY wins DESC";
        Connection connection = databaseManager.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String playerName = resultSet.getString("player_name");
                int wins = resultSet.getInt("wins");
                scores.add(new PlayerScore(playerName, wins));
            }
        } catch (SQLException e) {
            logger.error("Failed to retrieve high scores", e);
        }

        return scores;
    }

    public static class PlayerScore {

        private final String playerName;
        private final int wins;

        public PlayerScore(String playerName, int wins) {
            this.playerName = playerName;
            this.wins = wins;
        }

        public String getPlayerName() {
            return playerName;
        }

        public int getWins() {
            return wins;
        }
    }
}
