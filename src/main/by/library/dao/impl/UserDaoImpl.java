package main.by.library.dao.impl;

import main.by.library.dao.GenericDAO;
import main.by.library.dao.UserDao;
import main.by.library.entity.*;
import main.by.library.jdbs.ConnectionPoolImpl;

import java.sql.*;
import java.util.List;
import java.util.Objects;

public class UserDaoImpl extends GenericDAO<User> implements UserDao {

    private static UserDaoImpl instance;
    public static final String SQL_UPDATE_USER_DATA = "UPDATE library.user SET password = ?, first_name = ?,last_name = ?,phone_number = ?, email = ? WHERE username = ?";
    public static final String SQL_ADD_NEW_USER = "INSERT INTO library.user (username, password, role, first_name, last_name, phone_number, email) VALUES (?,?,?,?,?,?,?)";
    public static final String SQL_FIND_ALL_USERS = "SELECT id, username,role, first_name, last_name, phone_number, email FROM library.user";
    public static final String SQL_ADD_USER_IN_BLACK_LIST = "UPDATE library.user SET is_banned = TRUE WHERE username = (?);";
    public static final String SQL_FIND_USER_BY_USERNAME = "SELECT id, username,password, role, first_name, last_name, phone_number, email FROM library.user WHERE username = (?)";

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
        User userForResult = null;
        List<User> userByUsername = findUserByUsername(username);
        for (User user : userByUsername) {
            userForResult = new User(user.getUsername(), user.getPassword(), user.getRole(), user.getUserData());
        }
        return userForResult;
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
    public boolean AddNewUser(User user) {
        return addNew(user, ConnectionPoolImpl.getInstance().getConnection(), SQL_ADD_NEW_USER);
        //TODO commit rollback
    }

    @Override
    public List<User> findAllUsers() {
        return findAll(ConnectionPoolImpl.getInstance().getConnection(), SQL_FIND_ALL_USERS);
    }

    @Override
    public List<User> findUserByUsername(String username) {
        return findEntityByYardstick(username, ConnectionPoolImpl.getInstance().getConnection(), SQL_FIND_USER_BY_USERNAME);
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
