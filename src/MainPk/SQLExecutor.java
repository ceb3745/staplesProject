package MainPk;

import java.sql.*;

/**
 * A class that manages the connection to the database
 * and queries to that database.
 */
public class SQLExecutor {
    private final String dbLocation;
    private Connection conn;

    /**
     * Creates a new MainPk.SQLExecutor
     */
    public SQLExecutor() {
        dbLocation = "jdbc:h2:./staplesProject";
    }

    /**
     * Starts a new connection using the given username and password
     *
     * @param user the username of the user attempting to connect to the database
     * @param password the password of the user attempting to connect to the database
     */
    public void startConnection(String user, String password) {
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(dbLocation, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes an already started connection
     */
    public void closeConnection() {
        try {
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes a given query and retrieves data if it exists
     *
     * @param query the query to perform
     * @return a ResultSet containing the query results
     */
    public ResultSet executeQuery(String query) {
        ResultSet result = null;
        try {
            Statement statement = conn.createStatement();
            result = statement.executeQuery(query);
        } catch(SQLException e) {
            System.out.println("Something went wrong during query execution: " + e);
        }
        return result;
    }
}
