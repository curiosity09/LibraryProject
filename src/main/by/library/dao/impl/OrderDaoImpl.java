package main.by.library.dao.impl;

import main.by.library.dao.BookDao;
import main.by.library.dao.GenericDaoImpl;
import main.by.library.dao.OrderDao;
import main.by.library.dao.UserDao;
import main.by.library.entity.*;
import main.by.library.jdbs.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class OrderDaoImpl extends GenericDaoImpl<Order> implements OrderDao {

    public static final int OFFSET_ZERO = 0;
    private static OrderDaoImpl instance;
    public static final String SQL_GET_COUNT_ORDER = "SELECT count(id) AS countRow FROM library.order_card";
    public static final String SQL_WHERE_USERNAME_PREDICATE = " WHERE username = ?";
    public static final String SQL_GET_COUNT_ORDER_BY_USER = SQL_GET_COUNT_ORDER + SQL_WHERE_USERNAME_PREDICATE;
    public static final String SQL_GET_ALL_ORDER = "SELECT o.id AS order_id, reader_id, username, rental_time, rental_period FROM library.order_card o JOIN library.user u ON o.reader_id = u.id";
    public static final String SQL_LIMIT_OFFSET = " ORDER BY rental_time DESC LIMIT ? OFFSET ?";
    public static final String SQL_GET_ALL_ORDER_WITH_LIMIT_OFFSET = SQL_GET_ALL_ORDER + SQL_LIMIT_OFFSET;
    public static final String SQL_GET_ORDER_BY_ID = SQL_GET_ALL_ORDER + " WHERE o.id = ?";
    public static final String SQL_GET_ALL_ORDER_BY_USERNAME = SQL_GET_ALL_ORDER + SQL_WHERE_USERNAME_PREDICATE + SQL_LIMIT_OFFSET;
    public static final String SQL_ADD_ORDER = "INSERT INTO library.order_card (reader_id, rental_time, rental_period) VALUES ((SELECT id FROM library.user WHERE username = ?), ?, ?)";
    public static final String SQL_ADD_BOOK_IN_ORDER = "INSERT INTO library.order_book (order_id, book_id) VALUES (?,?)";
    public static final String SQL_DELETE_FROM_ORDER_BOOK = "DELETE FROM library.order_book WHERE order_id = ?";
    public static final String SQL_DELETE_FROM_ORDER_CARD = "DELETE FROM library.order_card WHERE id = ?";
    private static final Logger LOGGER = LogManager.getLogger(OrderDaoImpl.class);

    private OrderDaoImpl() {
    }

    /**
     * Returns instance if the object has already been created
     * @return instance
     */
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
    public int getCount() {
        return getCountRow(connectionPool.getConnection(), SQL_GET_COUNT_ORDER);
    }

    @Override
    public boolean delete(Order order) {
        return deleteOrder(order.getId());
    }

    @Override
    public List<Order> findOrderByUsername(String username, int limit, int offset) {
        return findAllByParameter(username, limit, offset, connectionPool.getConnection(), SQL_GET_ALL_ORDER_BY_USERNAME);
    }

    @Override
    public List<Order> findAll(int limit, int offset) {
        return findAll(connectionPool.getConnection(), SQL_GET_ALL_ORDER_WITH_LIMIT_OFFSET, limit, offset);
    }

    @Override
    public Optional<Order> findById(int orderId) {
        return findEntityByParameter(connectionPool.getConnection(), SQL_GET_ORDER_BY_ID, orderId);
    }

    @Override
    protected Order mapToEntity(ResultSet orderSet) throws SQLException {
        int orderId = orderSet.getInt("order_id");
        BookDao bookDao = BookDaoImpl.getInstance();
        UserDao userDao = UserDaoImpl.getInstance();
        Optional<User> userOptional = userDao.findById(orderSet.getInt("reader_id"));
        User user = User.builder().build();
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }
        List<Book> bookList = bookDao.getByOrderId(orderSet.getInt("order_id"), 10, OFFSET_ZERO);
        return new Order(orderId, bookList, user, orderSet.getObject("rental_time",
                LocalDateTime.class), orderSet.getObject("rental_period", LocalDateTime.class));
    }

    @Override
    public boolean addNew(Order order) {
        Connection connection = connectionPool.getConnection();
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            order.setId(addNew(order, connection, SQL_ADD_ORDER));
            boolean addBookResult = addBookInCurrentOrder(order, connection);
            if (order.getId() != 0 && addBookResult) {
                connection.commit();
                return true;
            } else {
                rollbackConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error(DAO_METHODS_EXCEPTION_MESSAGE, e);
            rollbackConnection(connection);
        } finally {
            closeConnection(connection);
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
            } catch (SQLException e) {
                e.printStackTrace();
                LOGGER.error(DAO_METHODS_EXCEPTION_MESSAGE, e);
                rollbackConnection(connection);
            } finally {
                closeStatement(statement);
            }
        }
        return result;
    }

    @Override
    public boolean update(Order order) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected PreparedStatement updateMapToTable(PreparedStatement statement, Order order) {
        return statement;
    }

    private boolean deleteOrder(int orderId) {
        Connection connection = connectionPool.getConnection();
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            boolean delFromOrderBook = deleteOrderById(connection, SQL_DELETE_FROM_ORDER_BOOK, orderId);
            boolean delFromOrderCard = deleteOrderById(connection, SQL_DELETE_FROM_ORDER_CARD, orderId);
            if (delFromOrderBook && delFromOrderCard) {
                connection.commit();
                return true;
            } else {
                rollbackConnection(connection);
            }
        } catch (SQLException e) {
            LOGGER.error(DAO_METHODS_EXCEPTION_MESSAGE, e);
            e.printStackTrace();
            rollbackConnection(connection);
        } finally {
            closeConnection(connection);
        }
        return false;
    }

    private boolean deleteOrderById(Connection connection, String sqlQuery, int idObject) {
        boolean result = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, idObject);
            result = statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error(DAO_METHODS_EXCEPTION_MESSAGE, e);
            rollbackConnection(connection);
        } finally {
            closeStatement(statement);
        }
        return result;
    }
}

