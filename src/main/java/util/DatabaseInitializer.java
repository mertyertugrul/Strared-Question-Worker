package util;

import java.sql.*;
import java.util.logging.Logger;

public class DatabaseInitializer {
    private DatabaseInitializer() {}
    private static final Logger LOGGER = Logger.getLogger(DatabaseInitializer.class.getName());

    public static void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS questions (" + // Table name in lowercase
                    "id VARCHAR(255) PRIMARY KEY, " +
                    "note TEXT, " +
                    "timesSolved INT)";
            stmt.execute(sql);
        } catch (Exception e) {
            LOGGER.severe("Database initialization failed: " + e.getMessage());
        }
    }


}
