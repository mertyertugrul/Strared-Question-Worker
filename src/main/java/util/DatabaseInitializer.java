package util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Logger;

public class DatabaseInitializer {
    private static final Logger LOGGER = Logger.getLogger(DatabaseInitializer.class.getName());

    public static void initializeDatabase() {
        try(Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS questions (" +
                    "id VARCHAR(255) PRIMARY KEY, " +
                    "note TEXT, " +
                    "timesSolved INT)";
            stmt.execute(sql);
            if (isTableExists(conn, "questions")) {
                LOGGER.info("Table 'questions' exists or created successfully");
            } else {
                LOGGER.warning("Table 'questions' does not exist and was not created");
            }
        } catch (Exception e) {
            LOGGER.severe("Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean isTableExists(Connection conn, String tableName) throws Exception {
        DatabaseMetaData dbmd = conn.getMetaData();
        try (ResultSet rs = dbmd.getTables(null, null, tableName.toUpperCase(), null)) {
            return rs.next();
        }
    }
}
