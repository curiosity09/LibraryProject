package main.by.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class GenericDAO<T> {

    public List<T> findAll(Connection connection, String sqlQuery) {
        List<T> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(mapToEntityForGlobalSearch(resultSet));
                connection.commit();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    protected abstract T mapToEntityForGlobalSearch(ResultSet resultSet) throws SQLException;

    public boolean addNew(T t, Connection connection, String sqlQuery) {
        boolean result = false;
        if (Objects.nonNull(t)) {
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                setFieldToStatement(statement, t);
                result = statement.executeUpdate() == 1;
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

    protected abstract void setFieldToStatement(PreparedStatement statement, T t) throws SQLException;

    public List<T> findEntityByYardstick(String yardstick,Connection connection, String sqlQuery) {
        List<T> list = new ArrayList<>();
        if (Objects.nonNull(yardstick)) {
            try(PreparedStatement statement =
                        connection.prepareStatement(sqlQuery)) {
                statement.setString(1, yardstick);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    list.add(mapToEntityForSingleSearch(resultSet));
                    connection.commit();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    protected abstract T mapToEntityForSingleSearch(ResultSet resultSet) throws SQLException;
}

