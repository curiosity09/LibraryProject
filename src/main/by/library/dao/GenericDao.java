package main.by.library.dao;

import main.by.library.jdbs.ConnectionPool;
import main.by.library.jdbs.ConnectionPoolImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class GenericDao<T> {

    protected ConnectionPool connectionPool;

    protected GenericDao() {
        connectionPool = ConnectionPoolImpl.getInstance();
    }

    protected int getCountRow(Connection connection, String sqlQuery) {
        int result = 0;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                result = resultSet.getInt("countRow");
                connection.commit();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            rollbackConnection(connection);
        } finally {
            closeStatement(statement);
            release(connection);
            //TODO close connection or not?
            closeResultSet(resultSet);
        }
        return result;
    }

    protected List<T> findAll(Connection connection, String sqlQuery, int offset) {
        List<T> list = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, offset);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(mapToEntity(resultSet));
            }
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            rollbackConnection(connection);
        } finally {
            closeStatement(statement);
            release(connection);
            //TODO close connection or not?
            closeResultSet(resultSet);
        }
        return list;
    }

    protected abstract T mapToEntity(ResultSet resultSet) throws SQLException;

    protected int addNew(T t, Connection connection, String sqlQuery) {
        int result = 0;
        if (Objects.nonNull(t)) {
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
                setObjectsForAddMethod(statement, t).executeUpdate();
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if(generatedKeys.next()){
                   return generatedKeys.getInt("id");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                rollbackConnection(connection);
            } finally {
                closeStatement(statement);
            }
        }
        return result;
    }

    protected abstract PreparedStatement setObjectsForAddMethod(PreparedStatement statement, T t) throws SQLException;

    protected boolean addNewObject(T t, Connection connection, String sqlQuery) {
        //TODO to come up with new normal name for add method
        boolean result = false;
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            result = addNew(t, connection, sqlQuery)!=0;
            if (result) {
                connection.commit();
            } else {
                rollbackConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            rollbackConnection(connection);
        } finally {
            release(connection);
        }
        return result;
    }

    protected Optional<T> findEntityByParameter(Connection connection, String sqlQuery, Object parameter) {
        Optional<T> result = Optional.empty();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sqlQuery);
            setParameter(statement, parameter);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = Optional.of(mapToEntity(resultSet));
            }
            //TODO com  mit
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatement(statement);
            release(connection);
            //TODO close resultSet
        }
        return result;
    }

    protected List<T> findAllByParameter(Object parameter, Connection connection, String sqlQuery) {
        List<T> list = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        if (Objects.nonNull(parameter)) {
            try {
                connection.setAutoCommit(false);
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                statement = connection.prepareStatement(sqlQuery);
                setParameter(statement, parameter);
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    list.add(mapToEntity(resultSet));
                }
                connection.commit();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                rollbackConnection(connection);
            } finally {
                closeStatement(statement);
                release(connection);
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
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                statement.setString(1, value);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
                connection.commit();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                rollbackConnection(connection);
            } finally {
                closeStatement(statement);
                release(connection);
                closeResultSet(resultSet);
            }
        }
        return false;
    }

    public boolean updateObject(T t, Connection connection, String sqlQuery) {
        PreparedStatement statement = null;
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            statement = connection.prepareStatement(sqlQuery);
            return updateMapToTable(statement, t).executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            rollbackConnection(connection);
        } finally {
            release(connection);
            closeStatement(statement);
        }
        return false;
    }

    protected abstract PreparedStatement updateMapToTable(PreparedStatement statement, T t) throws SQLException;

    public boolean deleteObjectById(Connection connection, String sqlQuery, int idObject) {
        boolean result = false;
        PreparedStatement statement = null;
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, idObject);
            result = statement.executeUpdate() == 1;
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            rollbackConnection(connection);
        } finally {
            closeStatement(statement);
            release(connection);
        }
        return result;
    }

    protected void release(Connection connection) {
        try {
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void closeStatement(Statement statement) {
        if (Objects.nonNull(statement)) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void closeConnection(Connection connection) {
        if (Objects.nonNull(connection)) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void rollbackConnection(Connection connection) {
        if (Objects.nonNull(connection)) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void closeResultSet(ResultSet resultSet) {
        if (Objects.nonNull(resultSet)) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

