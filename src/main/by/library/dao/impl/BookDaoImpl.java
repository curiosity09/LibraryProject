package main.by.library.dao.impl;

import main.by.library.dao.BookDao;
import main.by.library.dao.GenericDao;
import main.by.library.entity.*;
import main.by.library.jdbs.ConnectionPoolImpl;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class BookDaoImpl extends GenericDao<Book> implements BookDao {

    private static BookDaoImpl instance;
    public static final String SQL_GET_COUNT_BOOK = "SELECT count(name) AS countRow FROM library.book";
    public static final String SQL_UPDATE_BOOK_DATA = "UPDATE library.book SET name = ?, author_id = ?, genre_id = ?, section_id = ?, quantity = ?, publication_year = ? WHERE id = ?";
    public static final String SQL_SELECT_ALL_BOOK = "SELECT b.id, b.name, b.publication_year, b.author_id, a.full_name AS full_name, b.genre_id, g.name AS genre_name, b.quantity, b.section_id, s.name AS section_name FROM library.book b JOIN library.author a ON b.author_id = a.id JOIN library.genre g ON b.genre_id = g.id JOIN library.section s ON b.section_id = s.id";
    public static final String SQL_LIMIT_OFFSET = " LIMIT 10 OFFSET ?";
    public static final String SQL_SELECT_ALL_BOOK_WITH_LIMIT_OFFSET = SQL_SELECT_ALL_BOOK + SQL_LIMIT_OFFSET;
    public static final String SQL_SELECT_BOOK_BY_ID = SQL_SELECT_ALL_BOOK + " WHERE b.id = (?)";
    public static final String SQL_SELECT_BOOK_BY_NAME = SQL_SELECT_ALL_BOOK + " WHERE b.name = (?)";
    public static final String SQL_SELECT_BOOK_BY_AUTHOR_FULL_NAME = SQL_SELECT_ALL_BOOK + " WHERE a.full_name = (?)";
    public static final String SQL_SELECT_BOOK_BY_GENRE = SQL_SELECT_ALL_BOOK + " WHERE g.name = (?)";
    public static final String SQL_SELECT_BOOK_BY_SECTION = SQL_SELECT_ALL_BOOK + " WHERE s.name = (?)";
    public static final String SQL_SELECT_BOOK_BY_ORDER = SQL_SELECT_ALL_BOOK + " JOIN library.order_book ob ON b.id = ob.book_id WHERE ob.order_id = ?";
    public static final String SQL_ADD_NEW_BOOK = "INSERT INTO library.book (name, author_id, genre_id,section_id,quantity, publication_year) VALUES ((?), (SELECT id FROM library.author WHERE full_name = (?)), (SELECT id FROM library.genre WHERE name = (?)),(SELECT id FROM library.section WHERE name = (?)),(?), (?))";

    private BookDaoImpl() {
    }

    public static BookDaoImpl getInstance() {
        if (instance == null) {
            instance = new BookDaoImpl();
        }
        return instance;
    }

    @Override
    protected Book mapToEntity(ResultSet resultSet) throws SQLException {
        int bookId = resultSet.getInt("id");
        Author authorForResult = new Author(resultSet.getInt("author_id"), resultSet.getString("full_name"));
        Genre genreForResult = new Genre(resultSet.getInt("genre_id"), resultSet.getString("genre_name"));
        Section sectionForResult = new Section(resultSet.getInt("section_id"), resultSet.getString("section_name"));
        return new Book(bookId,
                resultSet.getString("name"),
                authorForResult,
                genreForResult,
                sectionForResult,
                resultSet.getInt("quantity"),
                resultSet.getInt("publication_year"));
    }

    @Override
    public boolean updateBookData(Book book) {
        return updateObject(book, ConnectionPoolImpl.getInstance().getConnection(), SQL_UPDATE_BOOK_DATA);
    }

    @Override
    protected PreparedStatement updateMapToTable(PreparedStatement statement, Book book) throws SQLException {
        statement.setString(1, book.getName());
        statement.setInt(2, book.getAuthor().getId());
        statement.setInt(3, book.getGenre().getId());
        statement.setInt(4, book.getSection().getId());
        statement.setInt(5, book.getQuantity());
        statement.setInt(6, book.getPublicationYear());
        statement.setInt(7, book.getId());
        return statement;
    }

    @Override
    public int getCountBook() {
        return getCountRow(ConnectionPoolImpl.getInstance().getConnection(), SQL_GET_COUNT_BOOK);
    }

    @Override
    public Book findBookById(int bookId) {
        Optional<Book> optionalBook = findEntityByParameter(ConnectionPoolImpl.getInstance().getConnection(), SQL_SELECT_BOOK_BY_ID, bookId);
        return optionalBook.orElseGet(Book::new);
    }

    @Override
    public List<Book> findBookByName(String bookName) {
        return findAllByParameter(bookName, ConnectionPoolImpl.getInstance().getConnection(), SQL_SELECT_BOOK_BY_NAME);
    }

    @Override
    public List<Book> findAllBook(int offset) {
        return findAll(ConnectionPoolImpl.getInstance().getConnection(), SQL_SELECT_ALL_BOOK_WITH_LIMIT_OFFSET, offset);
    }

    @Override
    public List<Book> findBookByAuthorFullName(String authorFullName) {
        return findAllByParameter(authorFullName, ConnectionPoolImpl.getInstance().getConnection(), SQL_SELECT_BOOK_BY_AUTHOR_FULL_NAME);
    }

    @Override
    public List<Book> findBookByGenre(String genreName) {
        return findAllByParameter(genreName, ConnectionPoolImpl.getInstance().getConnection(), SQL_SELECT_BOOK_BY_GENRE);
    }

    @Override
    public List<Book> findBookBySection(String sectionName) {
        return findAllByParameter(sectionName, ConnectionPoolImpl.getInstance().getConnection(), SQL_SELECT_BOOK_BY_SECTION);
    }

    @Override
    public boolean addNewBook(Book book) {
        return addNewObject(book, ConnectionPoolImpl.getInstance().getConnection(), SQL_ADD_NEW_BOOK);
    }

    @Override
    public List<Book> getByOrderId(int id) {
        return findAllByParameter(id, ConnectionPoolImpl.getInstance().getConnection(), SQL_SELECT_BOOK_BY_ORDER);
    }

    @Override
    protected PreparedStatement setObjectsForAddMethod(PreparedStatement statement, Book book) throws SQLException {
        statement.setString(1, book.getName());
        statement.setString(2, book.getAuthor().getFullName());
        statement.setString(3, book.getGenre().getName());
        statement.setString(4, book.getSection().getName());
        statement.setInt(5, book.getQuantity());
        statement.setInt(6, book.getPublicationYear());
        return statement;
    }
}
