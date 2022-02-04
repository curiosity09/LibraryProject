package main.by.library.dao.impl;

import main.by.library.dao.BookDao;
import main.by.library.dao.GenericDao;
import main.by.library.dao.OrderDao;
import main.by.library.dao.UserDao;
import main.by.library.entity.*;
import main.by.library.jdbs.ConnectionPoolImpl;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class OrderDaoImpl extends GenericDao<Order> implements OrderDao {

    private static OrderDaoImpl instance;
    public static final String SQL_GET_COUNT_ORDER = "SELECT count(id) AS countRow FROM library.order_card";
    public static final String SQL_GET_ALL_ORDER = "SELECT o.id AS order_id, reader_id, username, rental_time,  rental_period FROM library.order_card o JOIN library.user u ON o.reader_id = u.id";
    public static final String SQL_LIMIT_OFFSET = " LIMIT 10 OFFSET ?";
    public static final String SQL_GET_ALL_ORDER_WITH_LIMIT_OFFSET = SQL_GET_ALL_ORDER + SQL_LIMIT_OFFSET;
    public static final String SQL_GET_ORDER_BY_ID = SQL_GET_ALL_ORDER + " WHERE o.id = ?";
    public static final String SQL_GET_ALL_ORDER_BY_CUSTOMER_USERNAME = SQL_GET_ALL_ORDER + " WHERE username = ?";
    public static final String SQL_ADD_ORDER = "INSERT INTO library.order_card (reader_id, rental_time, rental_period) VALUES ((SELECT id FROM library.user WHERE username = ?), ?, ?)";
    public static final String SQL_ADD_BOOK_IN_ORDER = "INSERT INTO library.order_book (order_id, book_id) VALUES (?,?)";
    public static final String SQL_DELETE_ORDER = "DELETE FROM library.order_card WHERE id = (?)";

    private OrderDaoImpl() {
    }

    public static OrderDaoImpl getInstance() {
        if (instance == null) {
            instance = new OrderDaoImpl();
        }
        return instance;
    }

    @Override
    protected PreparedStatement setObjectsForAddMethod(PreparedStatement statement, Order order) throws SQLException {
        statement.setString(1, order.getUser().getUsername());
        statement.setObject(2, order.getRentalTime());
        statement.setObject(3, order.getRentalPeriod());
        return statement;
    }

    @Override
    public int getCountOrder() {
        return getCountRow(ConnectionPoolImpl.getInstance().getConnection(), SQL_GET_COUNT_ORDER);
    }

    @Override
    public List<Order> findOrderByCustomerUsername(String username) {
        return findAllByParameter(username, ConnectionPoolImpl.getInstance().getConnection(), SQL_GET_ALL_ORDER_BY_CUSTOMER_USERNAME);
    }

    @Override
    public List<Order> findAllOrder(int offset) {
        return findAll(ConnectionPoolImpl.getInstance().getConnection(), SQL_GET_ALL_ORDER_WITH_LIMIT_OFFSET, offset);
    }

    @Override
    public Order findOrderById(int orderId) {
        Optional<Order> orderOptional = findEntityByParameter(ConnectionPoolImpl.getInstance().getConnection(), SQL_GET_ORDER_BY_ID, orderId);
        return orderOptional.orElseGet(Order::new);
        //TODO Optional
    }

    @Override
    protected Order mapToEntity(ResultSet orderSet) throws SQLException {
        int orderId = orderSet.getInt("order_id");
        BookDao bookDao = BookDaoImpl.getInstance();
        UserDao userDao = UserDaoImpl.getInstance();
        Optional<User> userOptional = userDao.findUserById(orderSet.getInt("reader_id"));
        List<Book> bookList = bookDao.getByOrderId(orderSet.getInt("order_id"));
        return new Order(orderId, bookList, userOptional.orElse(null), orderSet.getObject("rental_time", LocalDateTime.class),
                orderSet.getObject("rental_period", LocalDateTime.class));
    }

    @Override
    public boolean addOrder(Order order) {
        Connection connection = ConnectionPoolImpl.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            order.setId(addNew(order, connection, SQL_ADD_ORDER));
            boolean addBookResult = addBookInCurrentOrder(order, connection);
            if (order.getId()!=0 && addBookResult) {
                connection.commit();
                return true;
            } else {
                rollbackConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            rollbackConnection(connection);
        } finally {
            release(connection);
        }
        return false;
    }

    private boolean addBookInCurrentOrder(Order order, Connection connection) {
        boolean result = false;
        if (Objects.nonNull(order)) {
            PreparedStatement statement = null;
            try {
                int count = 0;
                statement = connection.prepareStatement(SQL_ADD_BOOK_IN_ORDER);
                while (count < order.getBook().size()) {
                    statement.setInt(1, order.getId());
                    statement.setInt(2, order.getBook().get(count).getId());
                    result = statement.executeUpdate() != 0;
                    count++;
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

    @Override
    protected PreparedStatement updateMapToTable(PreparedStatement statement, Order order) throws SQLException {
        int quantityBook = order.getBook().size();
        int count = 0;
        while (count < quantityBook) {
            statement.setInt(1, order.getBook().get(count).getId());
            statement.setInt(2, order.getBook().get(count).getId());
            count++;
        }
        return statement;
    }

    @Override
    public boolean deleteOrder(Order order) {
        return deleteObjectById(ConnectionPoolImpl.getInstance().getConnection(), SQL_DELETE_ORDER, order.getId());
    }
}

