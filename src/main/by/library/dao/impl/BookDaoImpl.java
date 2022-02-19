package main.by.library.dao.impl;

import main.by.library.dao.BookDao;
import main.by.library.dao.GenericDaoImpl;
import main.by.library.entity.*;
import main.by.library.jdbs.ConnectionPool;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class BookDaoImpl extends GenericDaoImpl<Book> implements BookDao {

    private static BookDaoImpl instance;
    public static final String SQL_GET_COUNT_BOOK = "SELECT count(name) AS countRow FROM library.book";
    public static final String SQL_UPDATE_BOOK_DATA = "UPDATE library.book SET name = ?, author_id = (SELECT id FROM library.author WHERE full_name = (?)), genre_id = (SELECT id FROM library.genre WHERE name = (?)), section_id = (SELECT id FROM library.section WHERE name = (?)), quantity = ?, publication_year = ? WHERE id = ?";
    public static final String SQL_SELECT_ALL_BOOK = "SELECT b.id, b.name, b.publication_year, b.author_id, a.full_name AS full_name, b.genre_id, g.name AS genre_name, b.quantity, b.section_id, s.name AS section_name FROM library.book b JOIN library.author a ON b.author_id = a.id JOIN library.genre g ON b.genre_id = g.id JOIN library.section s ON b.section_id = s.id";
    public static final String SQL_LIMIT_OFFSET = " ORDER BY b.quantity DESC LIMIT ? OFFSET ?";
    public static final String SQL_SELECT_ALL_BOOK_WITH_LIMIT_OFFSET = SQL_SELECT_ALL_BOOK + SQL_LIMIT_OFFSET;
    public static final String SQL_SELECT_BOOK_BY_ID = SQL_SELECT_ALL_BOOK + " WHERE b.id = (?)";
    public static final String SQL_SELECT_BOOK_BY_NAME = SQL_SELECT_ALL_BOOK + " WHERE b.name = (?)";
    public static final String SQL_SELECT_BOOK_BY_AUTHOR_FULL_NAME = SQL_SELECT_ALL_BOOK + " WHERE a.full_name = (?)";
    public static final String SQL_SELECT_BOOK_BY_GENRE = SQL_SELECT_ALL_BOOK + " WHERE g.name = (?)";
    public static final String SQL_SELECT_BOOK_BY_SECTION = SQL_SELECT_ALL_BOOK + " WHERE s.name = (?)";
    public static final String SQL_SELECT_BOOK_BY_ORDER = SQL_SELECT_ALL_BOOK + " JOIN library.order_book ob ON b.id = ob.book_id WHERE ob.order_id = ?" + SQL_LIMIT_OFFSET;
    public static final String SQL_ADD_NEW_BOOK = "INSERT INTO library.book (name, author_id, genre_id,section_id,quantity, publication_year) VALUES ((?), (SELECT id FROM library.author WHERE full_name = (?)), (SELECT id FROM library.genre WHERE name = (?)),(SELECT id FROM library.section WHERE name = (?)),(?), (?))";
    public static final String SQL_DELETE_BOOK = "DELETE FROM library.book WHERE id = ?";

    private BookDaoImpl() {
    }

    /**
     * Returns instance if the object has already been created
     * @return instance
     */
    public static BookDaoImpl getInstance() {
        if (instance == null) {
            instance = new BookDaoImpl();
        }
        return instance;
    }

    @Override
    protected Book mapToEntity(ResultSet resultSet) throws SQLException {
        Author authorForResult = new Author(resultSet.getInt("author_id"), resultSet.getString("full_name"));
        Genre genreForResult = new Genre(resultSet.getInt("genre_id"), resultSet.getString("genre_name"));
        Section sectionForResult = new Section(resultSet.getInt("section_id"), resultSet.getString("section_name"));
        return new Book(resultSet.getInt("id"),
                resultSet.getString("name"),
                authorForResult,
                genreForResult,
                sectionForResult,
                resultSet.getInt("quantity"),
                resultSet.getInt("publication_year"));
    }

    @Override
    public boolean update(Book book) {
        return updateObject(book, connectionPool.getConnection(), SQL_UPDATE_BOOK_DATA);
    }

    @Override
    protected PreparedStatement updateMapToTable(PreparedStatement statement, Book book) throws SQLException {
        setParameterStatement(statement, book);
        statement.setInt(7, book.getId());
        return statement;
    }

    @Override
    public int getCount() {
        return getCountRow(connectionPool.getConnection(), SQL_GET_COUNT_BOOK);
    }

    @Override
    public boolean delete(Book book) {
        return deleteObjectById(connectionPool.getConnection(), SQL_DELETE_BOOK, book.getId());
    }

    @Override
    public Optional<Book> findById(int bookId) {
        return findEntityByParameter(connectionPool.getConnection(), SQL_SELECT_BOOK_BY_ID, bookId);
    }

    @Override
    public List<Book> findBookByName(String bookName, int limit, int offset) {
        return findAllByParameter(bookName, limit, offset, connectionPool.getConnection(), SQL_SELECT_BOOK_BY_NAME);
    }

    @Override
    public List<Book> findAll(int limit, int offset) {
        return findAll(connectionPool.getConnection(), SQL_SELECT_ALL_BOOK_WITH_LIMIT_OFFSET, limit, offset);
    }

    @Override
    public List<Book> findBookByAuthorFullName(String authorFullName, int limit, int offset) {
        return findAllByParameter(authorFullName, limit, offset, connectionPool.getConnection(), SQL_SELECT_BOOK_BY_AUTHOR_FULL_NAME);
    }

    @Override
    public List<Book> findBookByGenre(String genreName, int limit, int offset) {
        return findAllByParameter(genreName, limit, offset, connectionPool.getConnection(), SQL_SELECT_BOOK_BY_GENRE);
    }

    @Override
    public List<Book> findBookBySection(String sectionName, int limit, int offset) {
        return findAllByParameter(sectionName, limit, offset, connectionPool.getConnection(), SQL_SELECT_BOOK_BY_SECTION);
    }

    @Override
    public boolean addNew(Book book) {
        return addNewObject(book, connectionPool.getConnection(), SQL_ADD_NEW_BOOK);
    }

    @Override
    public List<Book> getByOrderId(int id, int limit, int offset) {
        return findAllByParameter(id, limit, offset, connectionPool.getConnection(), SQL_SELECT_BOOK_BY_ORDER);
    }

    @Override
    protected PreparedStatement setObjectsForAddMethod(PreparedStatement statement, Book book) throws SQLException {
        setParameterStatement(statement, book);
        return statement;
    }

    private void setParameterStatement(PreparedStatement statement, Book book) throws SQLException {
        statement.setString(1, book.getName());
        statement.setString(2, book.getAuthor().getFullName());
        statement.setString(3, book.getGenre().getName());
        statement.setString(4, book.getSection().getName());
        statement.setInt(5, book.getQuantity());
        statement.setInt(6, book.getPublicationYear());
    }
}
