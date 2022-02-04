package main.by.library.dao.impl;

import main.by.library.dao.GenericDao;
import main.by.library.dao.UserDao;
import main.by.library.entity.*;
import main.by.library.jdbs.ConnectionPoolImpl;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserDaoImpl extends GenericDao<User> implements UserDao {

    private static UserDaoImpl instance;
    public static final String SQL_GET_COUNT_USER = "SELECT count(username) AS countRow FROM library.user";
    public static final String SQL_UPDATE_USER_DATA = "UPDATE library.user SET password = ?, first_name = ?,last_name = ?,phone_number = ?, email = ? WHERE username = ?";
    public static final String SQL_ADD_NEW_USER = "INSERT INTO library.user (username, password, role, first_name, last_name, phone_number, email) VALUES (?,?,?,?,?,?,?)";
    public static final String SQL_FIND_USER = "SELECT id, username,password, role, first_name, last_name, phone_number, email FROM library.user";
    public static final String SQL_FIND_ALL_USERS = SQL_FIND_USER + " LIMIT 10 OFFSET ?";
    public static final String SQL_ADD_USER_IN_BLACK_LIST = "UPDATE library.user SET is_banned = TRUE WHERE username = (?);";
    public static final String SQL_FIND_USER_BY_USERNAME = SQL_FIND_USER + " WHERE username = (?)";
    public static final String SQL_FIND_USER_BY_ID = SQL_FIND_USER + " WHERE id = (?)";

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean isUserExist(String value) {
        return isExist(ConnectionPoolImpl.getInstance().getConnection(), SQL_FIND_USER_BY_USERNAME, value);
    }

    @Override
    public User checkAuthentication(String username) {
        Optional<User> userByUsername = findUserByUsername(username);
        return userByUsername.orElse(null);
    }

    @Override
    public int getCountUser() {
        return getCountRow(ConnectionPoolImpl.getInstance().getConnection(), SQL_GET_COUNT_USER);
    }

    @Override
    protected User mapToEntity(ResultSet userSet) throws SQLException {
        int userId = userSet.getInt("id");
        return new User(
                userId, userSet.getString("username"),
                userSet.getString("password"),
                userSet.getString("role"),
                new UserData(
                        userSet.getString("first_name"),
                        userSet.getString("last_name"),
                        userSet.getString("phone_number"),
                        userSet.getString("email")));
    }

    @Override
    public boolean updateUserData(User user) {
        return updateObject(user, ConnectionPoolImpl.getInstance().getConnection(), SQL_UPDATE_USER_DATA);
    }

    @Override
    protected PreparedStatement updateMapToTable(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getPassword());
        statement.setString(2, user.getUserData().getName());
        statement.setString(3, user.getUserData().getSurname());
        statement.setString(4, user.getUserData().getPhoneNumber());
        statement.setString(5, user.getUserData().getEmailAddress());
        statement.setString(6, user.getUsername());
        return statement;
    }

    @Override
    public boolean addNewUser(User user) {
        return addNewObject(user, ConnectionPoolImpl.getInstance().getConnection(), SQL_ADD_NEW_USER);
    }

    @Override
    protected PreparedStatement setObjectsForAddMethod(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getRole());
        statement.setString(4, user.getUserData().getName());
        statement.setString(5, user.getUserData().getSurname());
        statement.setString(6, user.getUserData().getPhoneNumber());
        statement.setString(7, user.getUserData().getEmailAddress());
        return statement;
    }

    @Override
    public List<User> findAllUsers(int offset) {
        return findAll(ConnectionPoolImpl.getInstance().getConnection(), SQL_FIND_ALL_USERS, offset);
    }

    @Override
    public Optional<User> findUserById(int userId) {
        return findEntityByParameter(ConnectionPoolImpl.getInstance().getConnection(), SQL_FIND_USER_BY_ID, userId);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return findEntityByParameter(ConnectionPoolImpl.getInstance().getConnection(), SQL_FIND_USER_BY_USERNAME, username);
    }

    @Override
    public boolean blockUser(User user) {
        boolean result = false;
        PreparedStatement userStatement = null;
        Connection connection = ConnectionPoolImpl.getInstance().getConnection();
        if (Objects.nonNull(user)) {
            try {
                connection.setAutoCommit(false);
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                userStatement = connection.prepareStatement(SQL_ADD_USER_IN_BLACK_LIST);
                userStatement.setString(1, user.getUsername());
                result = userStatement.executeUpdate() == 1;
                connection.commit();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                rollbackConnection(connection);
            } finally {
                closeStatement(userStatement);
                release(connection);
            }
        }
        return result;
    }
}
