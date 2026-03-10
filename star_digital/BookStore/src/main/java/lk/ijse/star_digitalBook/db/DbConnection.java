package lk.ijse.star_digitalBook.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    
    private final String DB_URL = "jdbc:mysql://localhost:3306/bookshopnew";
    private final String DB_USERNAME = "root";
    private final String DB_PASSWORD = "mysql";
    
    private static DbConnection dbc;
    private final Connection connection;
    
    private DbConnection() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
    
    public static DbConnection getInstance()throws SQLException {
        return (dbc==null) ? dbc = new DbConnection() : dbc;
    }
    
    public Connection getConnection() {
        return connection;
    }
}