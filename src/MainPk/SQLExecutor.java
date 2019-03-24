package MainPk;

import org.h2.tools.Server;

import java.sql.*;

/**
 * A class that manages the connection to the database
 * and queries to that database.
 */
public class SQLExecutor {
    private final String dbLocation;
    private Connection conn;
    private Server server;

    /**
     * Creates a new MainPk.SQLExecutor
     */
    public SQLExecutor() {
        dbLocation = "jdbc:h2:tcp://localhost:8082/home/";
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
            server = Server.createTcpServer().start();
            conn = DriverManager.getConnection("jdbc:h2:" + server.getURL() + "/./staplesProject", user, password);
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
            server.stop();
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
        ResultSet result;
        try {
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            result = statement.executeQuery(query);
        } catch(SQLException e) {
            try {
                Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statement.executeUpdate(query);
            } catch(SQLException f) {
                System.out.println("Could not execute sql statement");
            }
            return null;
        }
        return result;
    }
}
