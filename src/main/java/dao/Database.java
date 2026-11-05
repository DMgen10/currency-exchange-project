package dao;

import org.sqlite.SQLiteConfig;
import java.net.URL;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {

    private static final String URL = buildUrl();

    private Database(){}

    private static String buildUrl() {

        try {
            Class.forName("org.sqlite.JDBC");
            URL resource = Database.class.getClassLoader().getResource("currency.db");
            if (resource == null){
                throw new IllegalStateException("File currency.db not found in resources");
            }
            return "jdbc:sqlite" + Path.of(resource.toURI());
        } catch (Exception e) {
            throw new IllegalStateException("failed initialization data base", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        return DriverManager.getConnection(URL, config.toProperties());
    }
}
