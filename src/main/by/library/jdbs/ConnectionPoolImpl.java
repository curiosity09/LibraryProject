package main.by.library.jdbs;

import main.by.library.jdbs.ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class ConnectionPoolImpl
        implements ConnectionPool {

    private String url;
    private String user;
    private String password;
    private Queue<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private static final int INITIAL_POOL_SIZE = 10;
    public static final String DB_URL_KEY = "db.url";
    public static final String DB_USERNAME_KEY = "db.username";
    public static final String DB_PASS_KEY = "db.pass";

    public ConnectionPoolImpl(String url, String user, String password, Queue<Connection> connectionPool) {
        this.url = DB_URL_KEY;
        this.user = DB_USERNAME_KEY;
        this.password = DB_PASS_KEY;
        this.connectionPool = connectionPool;

    }

    public static ConnectionPoolImpl create(String url, String user, String password) throws SQLException {
        Queue<Connection> pool = new ArrayBlockingQueue<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
        }
        return new ConnectionPoolImpl(url, user, password, pool);
    }


    @Override
    public Connection getConnection() {
        Connection connection = connectionPool.poll();
        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
