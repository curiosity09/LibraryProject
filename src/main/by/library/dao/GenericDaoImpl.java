package main.by.library.dao;

import main.by.library.jdbs.ConnectionPool;
import main.by.library.util.LoggerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static main.by.library.util.LoggerUtil.*;

public abstract class GenericDaoImpl<T> {

    protected ConnectionPool connectionPool;
    private static final Logger LOGGER = LogManager.getLogger(GenericDaoImpl.class);

    protected GenericDaoImpl() {
        connectionPool = ConnectionPool.getInstance();
    }

    protected int getCountRow(Connection connection, String sqlQuery) {
        int result = 0;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                result = resultSet.getInt("countRow");
                connection.commit();
            }
        } catch (SQLException e) {
            LOGGER.error(DAO_METHODS_EXCEPTION_MESSAGE,e);
            rollbackConnection(connection);
        } finally {
            closeConnection(connection);
            closeStatement(statement);
            closeResultSet(resultSet);
        }
        return result;
    }

    protected List<T> findAll(Connection connection, String sqlQuery, int limit, int offset) {
        List<T> list = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(mapToEntity(resultSet));
            }
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(DAO_METHODS_EXCEPTION_MESSAGE,e);
            rollbackConnection(connection);
        } finally {
            closeConnection(connection);
            closeStatement(statement);
            closeResultSet(resultSet);
        }
        return list;
    }

    protected abstract T mapToEntity(ResultSet resultSet) throws SQLException;

    protected int addNew(T t, Connection connection, String sqlQuery) {
        int result = 0;
        if (Objects.nonNull(t)) {
            PreparedStatement statement = null;
            ResultSet generatedKeys = null;
            try {
                statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
                setObjectsForAddMethod(statement, t).executeUpdate();
                generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt("id");
                }
            } catch (SQLException e) {
                LOGGER.error(DAO_METHODS_EXCEPTION_MESSAGE,e);
                rollbackConnection(connection);
            } finally {
                closeStatement(statement);
                closeResultSet(generatedKeys);
            }
        }
        return result;
    }

    protected abstract PreparedStatement setObjectsForAddMethod(PreparedStatement statement, T t) throws SQLException;

    protected boolean addNewObject(T t, Connection connection, String sqlQuery) {
        boolean result = false;
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            result = addNew(t, connection, sqlQuery) != 0;
            if (result) {
                connection.commit();
            } else {
                rollbackConnection(connection);
            }
        } catch (SQLException e) {
            LOGGER.error(DAO_METHODS_EXCEPTION_MESSAGE, e);
            rollbackConnection(connection);
        } finally {
            closeConnection(connection);
        }
        return result;
    }

    protected Optional<T> findEntityByParameter(Connection connection, String sqlQuery, Object parameter) {
        Optional<T> result = Optional.empty();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            statement = connection.prepareStatement(sqlQuery);
            setParameter(statement, parameter);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = Optional.of(mapToEntity(resultSet));
                connection.commit();
            }
        } catch (SQLException e) {
            LOGGER.error(DAO_METHODS_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(statement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }
        return result;
    }

    protected List<T> findAllByParameter(Object parameter, int limit, int offset, Connection connection, String sqlQuery) {
        List<T> list = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        if (Objects.nonNull(parameter)) {
            try {
                connection.setAutoCommit(false);
                connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                statement = connection.prepareStatement(sqlQuery);
                setParameter(statement, parameter);
                statement.setInt(2, limit);
                statement.setInt(3, offset);
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    list.add(mapToEntity(resultSet));
                }
                connection.commit();
            } catch (SQLException e) {
                LOGGER.error(DAO_METHODS_EXCEPTION_MESSAGE, e);
                rollbackConnection(connection);
            } finally {
                closeStatement(statement);
                closeConnection(connection);
                closeResultSet(resultSet);
            }
        }
        return list;
    }

    private void setParameter(PreparedStatement statement, Object parameter) throws SQLException {
        if (parameter instanceof Integer) {
            statement.setInt(1, (int) parameter);
        } else {
            statement.setString(1, (String) parameter);
        }
    }

    protected boolean isExist(Connection connection, String sqlQuery, String value) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        if (Objects.nonNull(value)) {
            try {
                statement = connection.prepareStatement(sqlQuery);
                connection.setAutoCommit(false);
                connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                statement.setString(1, value);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
                connection.commit();
            } catch (SQLException e) {
                LOGGER.error(DAO_METHODS_EXCEPTION_MESSAGE, e);
                rollbackConnection(connection);
            } finally {
                closeStatement(statement);
                closeConnection(connection);
                closeResultSet(resultSet);
            }
        }
        return false;
    }

    protected boolean updateObject(T t, Connection connection, String sqlQuery) {
        PreparedStatement statement = null;
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            statement = connection.prepareStatement(sqlQuery);
            if (updateMapToTable(statement, t).executeUpdate() != 0) {
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error(DAO_METHODS_EXCEPTION_MESSAGE, e);
            rollbackConnection(connection);
        } finally {
            closeConnection(connection);
            closeStatement(statement);
        }
        return false;
    }

    protected abstract PreparedStatement updateMapToTable(PreparedStatement statement, T t) throws SQLException;

    protected boolean deleteObjectById(Connection connection, String sqlQuery, int idObject) {
        boolean result = false;
        PreparedStatement statement = null;
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, idObject);
            result = statement.executeUpdate() != 0;
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(DAO_METHODS_EXCEPTION_MESSAGE, e);
            rollbackConnection(connection);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return result;
    }

    protected void closeStatement(Statement statement) {
        if (Objects.nonNull(statement)) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(DAO_METHODS_EXCEPTION_MESSAGE, e);
            }
        }
    }

    protected void closeConnection(Connection connection) {
        if (Objects.nonNull(connection)) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(DAO_METHODS_EXCEPTION_MESSAGE, e);
            }
        }
    }

    protected void rollbackConnection(Connection connection) {
        if (Objects.nonNull(connection)) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.error(DAO_METHODS_EXCEPTION_MESSAGE, e);
            }
        }
    }

    protected void closeResultSet(ResultSet resultSet) {
        if (Objects.nonNull(resultSet)) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.error(DAO_METHODS_EXCEPTION_MESSAGE, e);
            }
        }
    }
}
