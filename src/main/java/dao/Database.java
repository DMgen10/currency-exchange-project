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

            String custom = System.getenv("CURRENCY_DB_PATH");
            Path dataBasePath = custom != null ? Path.of(custom) : Path.of(System.getProperty("user.home"), ".currency-exchange","currency.db");

            JDBC_URL = "jdbc:sqlite:" + dataBasePath.toAbsolutePath();

            if (Files.notExists(dataBasePath)){
                Files.createDirectories(dataBasePath.getParent());
                initSchema();
            }
        } catch (Exception exception){
            throw new IllegalStateException("failed initialization database", exception);
        }
    }

    private static void initSchema() throws IOException, SQLException {
        String script;

        try(InputStream in = Database.class.getResourceAsStream("/schema.sql")){
            if (in == null){
                throw new IllegalStateException("file schema.sql not found in resources");
            }
            script = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }

        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()){
            for (String sql: script.split(";")){
                if (!sql.isBlank()){
                    statement.executeUpdate(sql);
                }
            }
        }
    }

    private Database(){}

    public static Connection getConnection() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        return DriverManager.getConnection(JDBC_URL, config.toProperties());
    }
}
