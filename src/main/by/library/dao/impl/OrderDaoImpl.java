package main.by.library.dao.impl;

import main.by.library.dao.GenericDAO;
import main.by.library.dao.OrderDao;
import main.by.library.entity.*;
import main.by.library.jdbs.ConnectionPoolImpl;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class OrderDaoImpl extends GenericDAO<Order> implements OrderDao {

    private static OrderDaoImpl instance;
    public static final String SQL_GET_QUANTITY_BOOK = "SELECT quantity FROM library.book WHERE name = (?) OR id = (?)";
    public static final String SQL_UPDATE_BOOK_STORAGE = "UPDATE library.book SET quantity = ? WHERE name = ? OR id = ?";
    public static final String SQL_SELECT_ALL_ORDER = "SELECT order_id, reader_id, username, role, u.first_name AS user_name, u.last_name AS user_surname, book_id, b.name AS book_name, a.first_name AS author_name, a.last_name AS author_surname, g.name AS genre_name, s.name AS section_name, quantity,  publication_year,  rental_time,  rental_period, phone_number,  email FROM library.order_book ob JOIN library.order_card o ON ob.order_id = o.id JOIN library.book b ON ob.book_id = b.id JOIN library.user u ON o.reader_id = u.id JOIN library.author a ON b.author_id = a.id JOIN library.genre g ON b.genre_id = g.id JOIN library.section s ON b.section_id = s.id";
    public static final String SQL_ADD_ORDER = "INSERT INTO library.order_card (reader_id, rental_time, rental_period) VALUES ((SELECT id FROM library.user WHERE username = ?), ?, ?)";
    public static final String SQL_ADD_BOOK_IN_ORDER = "INSERT INTO library.order_book (order_id, book_id) VALUES ((SELECT o.id FROM library.order_card o JOIN library.user u ON o.reader_id = u.id WHERE username = ? AND rental_time = ?), (SELECT id FROM library.book WHERE name = ? OR id = ?))";
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
    protected Order mapToEntityForGlobalSearch(ResultSet orderSet) throws SQLException {
        int orderId = orderSet.getInt("order_id");
        int userId = orderSet.getInt("reader_id");
        int bookId = orderSet.getInt("book_id");
        Author authorForResult = new Author(
                orderSet.getString("author_name"),
                orderSet.getString("author_surname"));
        List<Book> booksForResult = new ArrayList<>();
        booksForResult.add(new Book(bookId, orderSet.getString("book_name"), authorForResult,
                orderSet.getInt("publication_year")));
        User userForResult = new User(
                userId, orderSet.getString("username"),
                orderSet.getString("role"),
                new UserData(
                        orderSet.getString("user_name"),
                        orderSet.getString("user_surname"),
                        orderSet.getString("phone_number"),
                        orderSet.getString("email")));
        return new Order(orderId, booksForResult, userForResult,
                orderSet.getObject("rental_time", LocalDateTime.class),
                orderSet.getObject("rental_period", LocalDateTime.class));
    }

    @Override
    protected void setFieldToStatement(PreparedStatement orderStatement, Order order) throws SQLException {
        orderStatement.setString(1, order.getUser().getUsername());
        orderStatement.setObject(2, order.getRentalTime());
        orderStatement.setObject(3, order.getRentalPeriod());
    }

    @Override
    protected Order mapToEntityForSingleSearch(ResultSet resultSet) throws SQLException {
        return null;
        //TODO do something
    }

    @Override
    protected PreparedStatement updateMapToTable(PreparedStatement statement, Order order) throws SQLException {
        return null;
        //TODO do something
    }

    @Override
    public List<Order> findAllOrder() {
        return toListForms(findAll(ConnectionPoolImpl.getInstance().getConnection(), SQL_SELECT_ALL_ORDER));
    }

    private List<Order> toListForms(List<Order> orders) {
        for (int i = 0; i < orders.size() - 1; i++) {
            if (orders.get(i).getId() == orders.get(i + 1).getId()) {
                for (Book book : orders.get(i + 1).getBook()) {
                    orders.get(i).getBook().add(book);
                }
                orders.remove(i + 1);
            }
        }
        return orders;
    }

    @Override
    public boolean addOrder(Order order) {
        Connection connection = ConnectionPoolImpl.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            List<Integer> quantityBook = getQuantityBook(order, connection);
            if (!quantityBook.contains(0)) {
                boolean addOrderResult = addNew(order, connection, SQL_ADD_ORDER);
                boolean addBookResult = addBookInCurrentOrder(order, connection);
                boolean updateResult = updateBookStorage(order, connection, quantityBook);
                connection.commit();
                return addOrderResult && addBookResult && updateResult;
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
        return addOrderSupportedMethod(order, connection, SQL_ADD_BOOK_IN_ORDER);
    }

    private boolean updateBookStorage(Order order, Connection connection, List<Integer> listAvailableBook) {
        boolean result = false;
        if (Objects.nonNull(order)) {
            PreparedStatement statement = null;
            int quantityBook = order.getBook().size();
            try {
                int count = 0;
                statement = connection.prepareStatement(SQL_UPDATE_BOOK_STORAGE);
                while (count < quantityBook) {
                    statement.setInt(1, listAvailableBook.get(count) - 1);
                    statement.setString(2, order.getBook().get(count).getName());
                    statement.setInt(3, order.getBook().get(count).getId());
                    result = statement.executeUpdate() != 0;
                    count++;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                closeStatement(statement);
            }
        }
        return result;
    }


    private List<Integer> getQuantityBook(Order order, Connection connection) {
        PreparedStatement statement = null;
        List<Integer> quantityOfBook = new ArrayList<>();
        if (Objects.nonNull(order)) {
            int size = order.getBook().size();
            int count = 0;
            try {
                statement = connection.prepareStatement(SQL_GET_QUANTITY_BOOK);
                //TODO засунуть в дженерек или что-то красивое придумать
                while (count < size) {
                    statement.setString(1, order.getBook().get(count).getName());
                    statement.setInt(2, order.getBook().get(count).getId());
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        int quantity = resultSet.getInt("quantity");
                        quantityOfBook.add(quantity);
                    }
                    count++;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                closeStatement(statement);
            }
        }
        return quantityOfBook;
    }

    @Override
    public boolean deleteOrder(Order order) {
        return deleteObjectById(ConnectionPoolImpl.getInstance().getConnection(), SQL_DELETE_ORDER, order.getId());
    }
}

