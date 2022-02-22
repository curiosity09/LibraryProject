package main.by.library.dao.impl;

import main.by.library.dao.GenericDaoImpl;
import main.by.library.dao.UserDao;
import main.by.library.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static main.by.library.util.LoggerUtil.*;

public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

    private static UserDaoImpl instance;
    public static final String SQL_GET_COUNT_USER = "SELECT count(username) AS countRow FROM library.user";
    public static final String SQL_UPDATE_USER_DATA = "UPDATE library.user SET username = ?, password = ?, first_name = ?,last_name = ?,phone_number = ?, email = ? WHERE id = ?";
    public static final String SQL_ADD_NEW_USER = "INSERT INTO library.user (username, password, role, first_name, last_name, phone_number, email) VALUES (?,?,?,?,?,?,?)";
    public static final String SQL_FIND_ALL_USERS = "SELECT u.id AS id, username,password, role, first_name, last_name, phone_number, email, is_banned FROM library.user u";
    public static final String SQL_LIMIT_OFFSET = " ORDER BY id LIMIT ? OFFSET ?";
    public static final String SQL_FIND_ALL_DEBTORS = "SELECT DISTINCT u.id AS id, username,password, role, first_name, last_name, phone_number, email, is_banned FROM library.user u JOIN library.order_card oc ON u.id = oc.reader_id WHERE rental_period<=CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Minsk' AND is_banned = FALSE" + SQL_LIMIT_OFFSET;
    public static final String SQL_FIND_ALL_USERS_WITH_LIMIT_OFFSET = SQL_FIND_ALL_USERS + SQL_LIMIT_OFFSET;
    public static final String SQL_ADD_USER_IN_BLACK_LIST = "UPDATE library.user SET is_banned = TRUE WHERE username = (?);";
    public static final String SQL_FIND_USER_BY_USERNAME = SQL_FIND_ALL_USERS + " WHERE username = (?)";
    public static final String SQL_FIND_USER_BY_ID = SQL_FIND_ALL_USERS + " WHERE u.id = (?)";
    public static final String SQL_DELETE_USER = "DELETE FROM library.user WHERE id = ?";
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    private UserDaoImpl() {
    }

    /**
     * Returns instance if the object has already been created
     * @return instance
     */
    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean isUserExist(String username) {
        return isExist(connectionPool.getConnection(), SQL_FIND_USER_BY_USERNAME, username);
    }

    @Override
    public int getCount() {
        return getCountRow(connectionPool.getConnection(), SQL_GET_COUNT_USER);
    }

    @Override
    public boolean delete(User user) {
        return deleteObjectById(connectionPool.getConnection(), SQL_DELETE_USER, user.getId());
    }

    @Override
    protected User mapToEntity(ResultSet userSet) throws SQLException {
        return User.builder().id(userSet.getInt("id"))
                .username(userSet.getString("username"))
                .password(userSet.getString("password"))
                .role(userSet.getString("role"))
                .userData(UserData.builder()
                        .name(userSet.getString("first_name"))
                        .surname(userSet.getString("last_name"))
                        .phoneNumber(userSet.getString("phone_number"))
                        .emailAddress(userSet.getString("email")).build())
                .isBanned(userSet.getBoolean("is_banned")).build();
    }

    @Override
    public boolean update(User user) {
        return updateObject(user, connectionPool.getConnection(), SQL_UPDATE_USER_DATA);
    }

    @Override
    protected PreparedStatement updateMapToTable(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getUserData().getName());
        statement.setString(4, user.getUserData().getSurname());
        statement.setString(5, user.getUserData().getPhoneNumber());
        statement.setString(6, user.getUserData().getEmailAddress());
        statement.setInt(7, user.getId());
        return statement;
    }

    @Override
    public boolean addNew(User user) {
        return addNewObject(user, connectionPool.getConnection(), SQL_ADD_NEW_USER);
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
    public List<User> findAll(int limit, int offset) {
        return findAll(connectionPool.getConnection(), SQL_FIND_ALL_USERS_WITH_LIMIT_OFFSET, limit, offset);
    }

    @Override
    public Optional<User> findById(int userId) {
        return findEntityByParameter(connectionPool.getConnection(), SQL_FIND_USER_BY_ID, userId);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return findEntityByParameter(connectionPool.getConnection(), SQL_FIND_USER_BY_USERNAME, username);
    }

    @Override
    public boolean blockUser(User user) {
        boolean result = false;
        PreparedStatement userStatement = null;
        Connection connection = connectionPool.getConnection();
        if (Objects.nonNull(user)) {
            try {
                connection.setAutoCommit(false);
                connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                userStatement = connection.prepareStatement(SQL_ADD_USER_IN_BLACK_LIST);
                userStatement.setString(1, user.getUsername());
                result = userStatement.executeUpdate() == 1;
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                LOGGER.error(DAO_METHODS_EXCEPTION_MESSAGE, e);
                rollbackConnection(connection);
            } finally {
                closeStatement(userStatement);
                closeConnection(connection);
            }
        }
        return result;
    }

    @Override
    public List<User> findAllDebtors(int limit, int offset) {
        return findAll(connectionPool.getConnection(), SQL_FIND_ALL_DEBTORS, limit, offset);
    }
}
