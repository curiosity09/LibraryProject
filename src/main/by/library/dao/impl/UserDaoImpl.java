package main.by.library.dao;

import main.by.library.entity.*;
import main.by.library.jdbs.ConnectionPool;
import main.by.library.jdbs.ConnectionPoolImpl;
import main.by.library.jdbs.PropertiesManager;

import java.sql.*;
import java.util.List;
import java.util.Objects;

public class UserDaoImpl extends GenericDAO<User> implements UserDao {

    public static final String DB_URL_KEY = "db.url";
    public static final String DB_USERNAME_KEY = "db.username";
    public static final String DB_PASS_KEY = "db.pass";
    public static final String SQL_ADD_NEW_USER = "INSERT INTO library.account (username, password, role, first_name, last_name, phone_number, email) VALUES (?,?,?,?,?,?,?)";
    public static final String SQL_FIND_ALL_USERS = "SELECT id, username,role, first_name, last_name, phone_number, email FROM library.account";
    public static final String SQL_ADD_USER_IN_BLACK_LIST = "INSERT INTO library.black_list (user_id) VALUES ((SELECT id FROM library.account WHERE username = (?)))";
    public static final String SQL_FIND_USER_BY_USERNAME = "SELECT id, username, role, first_name, last_name, phone_number, email FROM library.account WHERE username = (?)";
    private ConnectionPool connectionPool;
    private Connection connection;

    public UserDaoImpl() {
        try {
            Class.forName("org.postgresql.Driver");
            connectionPool = ConnectionPoolImpl.create(
                    PropertiesManager.getPropertyByKey(DB_URL_KEY),
                    PropertiesManager.getPropertyByKey(DB_USERNAME_KEY),
                    PropertiesManager.getPropertyByKey(DB_PASS_KEY));
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected User mapToEntityForGlobalSearch(ResultSet userSet) throws SQLException {
        int userId = userSet.getInt("id");
        return new User(
                userId, userSet.getString("username"),
                userSet.getString("role"),
                new UserData(
                        userSet.getString("first_name"),
                        userSet.getString("last_name"),
                        userSet.getString("phone_number"),
                        userSet.getString("email")));
    }

    @Override
    protected void setFieldToStatement(PreparedStatement userStatement, User user) throws SQLException {
        userStatement.setString(1, user.getUsername());
        userStatement.setString(2, user.getPassword());
        userStatement.setString(3, user.getRole());
        userStatement.setString(4, user.getUserData().getName());
        userStatement.setString(5, user.getUserData().getSurname());
        userStatement.setString(6, user.getUserData().getPhoneNumber());
        userStatement.setString(7, user.getUserData().getEmailAddress());
    }

    @Override
    protected User mapToEntityForSingleSearch(ResultSet userSet) throws SQLException {
        int userId = userSet.getInt("id");
        return new User(
                userId, userSet.getString("username"),
                userSet.getString("role"),
                new UserData(
                        userSet.getString("first_name"),
                        userSet.getString("last_name"),
                        userSet.getString("phone_number"),
                        userSet.getString("email")));
    }

    @Override
    public boolean AddNewUser(User user) {
        return addNew(user, connection, SQL_ADD_NEW_USER);
    }

    @Override
    public List<User> findAllUsers() {
        return findAll(connection, SQL_FIND_ALL_USERS);
    }

    @Override
    public List<User> findUserByUsername(String username) {
        return findEntityByYardstick(username, connection, SQL_FIND_USER_BY_USERNAME);
    }

    @Override
    public boolean blockUser(User user) {
        boolean result = false;
        if (Objects.nonNull(user)) {
            try (PreparedStatement userStatement = connection.prepareStatement(SQL_ADD_USER_IN_BLACK_LIST)) {
                userStatement.setString(1, user.getUsername());
                result = userStatement.executeUpdate() == 1;
                connection.commit();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
