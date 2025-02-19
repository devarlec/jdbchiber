package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static final String DEFAULT_USERNAME = "root";
    public static final String DEFAULT_PASSWORD = "admin";
    public static final String DEFAULT_DB_NAME = "myschema";
    public static final String USER_TABLE_NAME = "pizzafb";


    // Connect to MySQL
    public static Connection getMySQLConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" +
                DEFAULT_DB_NAME, DEFAULT_USERNAME, DEFAULT_PASSWORD);
        return connection;
    }
}
