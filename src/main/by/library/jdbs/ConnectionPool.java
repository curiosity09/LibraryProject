package main.by.library.jdbs;

import main.by.library.jdbs.ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
//release connection in genericDAO
public class ConnectionPoolImpl {

    private static Queue<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private static ConnectionPoolImpl instance;
    private static final int INITIAL_POOL_SIZE = 10;
    public static final String DB_URL_KEY = "db.url";
    public static final String DB_USERNAME_KEY = "db.username";
    public static final String DB_PASS_KEY = "db.pass";

    private ConnectionPoolImpl() {
    }

    public static ConnectionPoolImpl getInstance() {
        if (instance == null) {
            instance = create();
        }
        return instance;
    }

    private static ConnectionPoolImpl create() {
        try {
            connectionPool = new ArrayBlockingQueue<>(INITIAL_POOL_SIZE);
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                connectionPool.add(createConnection(
                        PropertiesManager.getPropertyByKey(DB_URL_KEY),
                        PropertiesManager.getPropertyByKey(DB_USERNAME_KEY),
                        PropertiesManager.getPropertyByKey(DB_PASS_KEY)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ConnectionPoolImpl();
    }

    public Connection getConnection() {
        Connection connection = connectionPool.poll();
        usedConnections.add(connection);
        return connection;
    }

    public boolean releaseConnection(Connection connection) throws SQLException {
        if(!connection.getAutoCommit()){
            connection.setAutoCommit(true);
        }
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public int getSizeUnused() {
        return connectionPool.size() ;
    }
    public int getSizeUsed() {
        return  usedConnections.size();
    }
}
