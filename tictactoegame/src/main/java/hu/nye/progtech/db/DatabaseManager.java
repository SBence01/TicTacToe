package hu.nye.progtech.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseManager {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

    private static final String DB_URL = "jdbc:h2:./tictactoe";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "";

    private Connection connection;
    private Server webServer;

    public void connect() throws SQLException {

        startWebServer();
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        initializeDatabase();
    }

    private void startWebServer() {

        try {
            webServer = Server.createWebServer("-web","-webPort","8082").start();
            logger.info("H2 Console started at http://localhost:8082");
        } catch (SQLException e) {
            logger.info("Could not start H2 Console", e);
        }
    }

    private void initializeDatabase() throws SQLException {

        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS high_scores (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    player_name VARCHAR(255) NOT NULL,
                    wins INT DEFAULT 0
                )
                """;

        try (Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
        }
    }

    public Connection getConnection() {

        return connection;
    }

    public void close() {

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Failed to close database connection", e);
            }
        }
    }
}
