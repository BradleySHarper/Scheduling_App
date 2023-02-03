package database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Abstract class contains code for connecting and disconnecting to/from the database.
 *
 * @author Bradley Harper
 */
public abstract class JDBC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static String password = "Passw0rd!";
    /**
     * The constant connection.
     */
    public static Connection connection;

    /**
     * Method for obtaining connection to the database.
     * Used on app initialization.
     *
     * @return connection connection
     */
    public static Connection openConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection successful!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Method for obtaining connection to the database.
     * Used when a method needs DB connection.
     *
     * @return connection connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Method for closing connection to the database.
     * Used on app termination.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
