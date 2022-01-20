package main.by.library.dao.impl;

import main.by.library.dao.GenericDAO;
import main.by.library.dao.OrderDao;
import main.by.library.entity.*;
import main.by.library.jdbs.ConnectionPool;
import main.by.library.jdbs.ConnectionPoolImpl;
import main.by.library.jdbs.PropertiesManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderDaoImpl extends GenericDAO<Order> implements OrderDao {

    private static OrderDaoImpl instance;
    public static final String SQL_SELECT_ALL_ORDER = "SELECT o.id AS order_id,name, u.first_name AS author_name, u.last_name AS author_surname, publication_year, a.id AS reader_id, username,role, a.first_name AS reader_name, a.last_name AS reader_surname,phone_number,email,rental_time,rental_period\n" +
            "FROM library.order_card o\n" +
            "JOIN library.book b ON o.book_id = b.id\n" +
            "JOIN library.author u ON b.author_id = u.id\n" +
            "JOIN library.account a ON o.reader_id = a.id\n" +
            "GROUP BY o.id, name,u.first_name, u.last_name, publication_year,a.id, username, role, a.first_name, a.last_name, phone_number, email, rental_time, rental_period;";
    public static final String SQL_ADD_ORDER = "INSERT INTO library.order_card (book_id, reader_id, rental_time) VALUES ((SELECT id FROM library.book b WHERE b.name = (?)),(SELECT id FROM library.account a WHERE a.username = (?)),(date(now())))";
    public static final String SQL_DELETE_ORDER = "DELETE FROM library.order_card WHERE id = (?)";
    private ConnectionPool connectionPool;
    private Connection connection;

    private OrderDaoImpl() {
    }

    public static OrderDaoImpl getInstance() {
        if (instance == null) {
            instance = new OrderDaoImpl();
        }
        return instance;
    }

    @Override
    protected Order mapToEntityForGlobalSearch(ResultSet orderSet) throws SQLException {
        int orderId = orderSet.getInt("order_id");
        int userId = orderSet.getInt("reader_id");
        Author authorForResult = new Author(
                orderSet.getString("author_name"),
                orderSet.getString("author_surname"));
        Book bookForResult = new Book(
                orderSet.getString("name"), authorForResult,
                orderSet.getInt("publication_year"));
        User userForResult = new User(
                userId, orderSet.getString("username"),
                orderSet.getString("role"),
                new UserData(
                        orderSet.getString("reader_name"),
                        orderSet.getString("reader_surname"),
                        orderSet.getString("phone_number"),
                        orderSet.getString("email")));
        return new Order(orderId, bookForResult, userForResult, orderSet.getDate("rental_time"));
    }

    @Override
    protected void setFieldToStatement(PreparedStatement orderStatement, Order order) throws SQLException {
        orderStatement.setString(1, order.getBook().getName());
        orderStatement.setString(2, order.getUser().getUsername());
    }

    @Override
    protected Order mapToEntityForSingleSearch(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public List<Order> findAllOrder() {
        return findAll( ConnectionPoolImpl.getInstance().getConnection(), SQL_SELECT_ALL_ORDER);
    }

    @Override
    public boolean addOrder(Order order) {
        return addNew(order,  ConnectionPoolImpl.getInstance().getConnection(), SQL_ADD_ORDER);
    }

    @Override
    public boolean deleteOrder(Order order) {
        boolean result = false;
        if (Objects.nonNull(order)) {
            try (PreparedStatement orderStatement = connection.prepareStatement(SQL_DELETE_ORDER)) {
                orderStatement.setInt(1, order.getId());
                result = orderStatement.executeUpdate() == 1;
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
