package persistence.setup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnectionSupplier {
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("""
                jdbc:sqlserver://IFSQL-01.htl-stp.if:1433;databaseName=db_pokemon_goetz_pils;user=sa
                """);
    }
}
