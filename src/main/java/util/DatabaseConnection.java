package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private DatabaseConnection() {}
    private static final Properties properties = new Properties();
    static{
        loadProperties();
    }
    private static void loadProperties(){
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException("Unable to find config.properties");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Unable to load database properties", e);
        }
    }
    public static Connection getConnection() throws SQLException {
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");
        return DriverManager.getConnection(url, user, password);
    }
}
