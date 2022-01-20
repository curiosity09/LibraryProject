package main.by.library.dao;

import main.by.library.entity.Author;
import main.by.library.entity.Book;
import main.by.library.entity.Genre;
import main.by.library.entity.User;
import main.by.library.jdbs.ConnectionPool;
import main.by.library.jdbs.ConnectionPoolImpl;
import main.by.library.jdbs.PropertiesManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BookDaoImpl extends GenericDAO<Book> implements BookDao {

    public static final String DB_URL_KEY = "db.url";
    public static final String DB_USERNAME_KEY = "db.username";
    public static final String DB_PASS_KEY = "db.pass";
    public static final String SQL_SELECT_BOOK_BY_NAME = "SELECT b.name, b.publication_year, a.first_name AS first_name, a.last_name AS last_name, g.name AS genre_name FROM library.book b JOIN library.author a ON b.author_id = a.id JOIN library.genre g ON b.genre_id = g.id GROUP BY b.name, b.publication_year, a.first_name,a.last_name, g.name HAVING b.name = (?)";
    public static final String SQL_SELECT_ALL_BOOK = "SELECT b.name, b.publication_year, a.first_name AS first_name, a.last_name AS last_name, g.name AS genre_name FROM library.book b JOIN library.author a ON b.author_id = a.id JOIN library.genre g ON b.genre_id = g.id GROUP BY b.name, b.publication_year, a.first_name,a.last_name, g.name";
    public static final String SQL_SELECT_BOOK_BY_AUTHOR_SURNAME = "SELECT b.name, b.publication_year, a.first_name AS first_name, a.last_name AS last_name, g.name AS genre_name FROM library.book b JOIN library.author a ON b.author_id = a.id JOIN library.genre g ON b.genre_id = g.id GROUP BY b.name, b.publication_year, a.first_name,a.last_name, g.name HAVING a.last_name = (?)";
    public static final String SQL_SELECT_BOOK_BY_GENRE = "SELECT b.name, b.publication_year, a.first_name AS first_name, a.last_name AS last_name, g.name AS genre_name FROM library.book b JOIN library.author a ON b.author_id = a.id JOIN library.genre g ON b.genre_id = g.id GROUP BY b.name, b.publication_year, a.first_name,a.last_name, g.name HAVING g.name = (?)";
    public static final String SQL_ADD_NEW_BOOK = "INSERT INTO library.book (name, author_id, genre_id, publication_year) VALUES ((?), (SELECT id FROM library.author WHERE last_name = (?) AND first_name = (?)), (SELECT id FROM library.genre WHERE name = (?)), (?))";
    private ConnectionPool connectionPool;
    private Connection connection;

    public BookDaoImpl() {
        try {
            connectionPool = ConnectionPoolImpl.create(
                    PropertiesManager.getPropertyByKey(DB_URL_KEY),
                    PropertiesManager.getPropertyByKey(DB_USERNAME_KEY),
                    PropertiesManager.getPropertyByKey(DB_PASS_KEY));
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Book mapToEntityForGlobalSearch(ResultSet resultSet) throws SQLException {
        Author authorForResult = new Author(resultSet.getString("first_name"), resultSet.getString("last_name"));
        Genre genreForResult = new Genre(resultSet.getString("genre_name"));
        return new Book(resultSet.getString("name"), authorForResult, genreForResult, resultSet.getInt("publication_year"));
    }

    @Override
    protected void setFieldToStatement(PreparedStatement bookStatement, Book book) throws SQLException {
        bookStatement.setString(1, book.getName());
        bookStatement.setString(2, book.getAuthor().getLastName());
        bookStatement.setString(3, book.getAuthor().getFirstName());
        bookStatement.setString(4, book.getGenre().getName());
        bookStatement.setInt(5, book.getPublicationYear());
    }

    @Override
    protected Book mapToEntityForSingleSearch(ResultSet resultSet) throws SQLException {
        Author authorForResult = new Author(resultSet.getString("first_name"), resultSet.getString("last_name"));
        Genre genreForResult = new Genre(resultSet.getString("genre_name"));
        return new Book(resultSet.getString("name"), authorForResult, genreForResult, resultSet.getInt("publication_year"));
    }

    @Override
    public List<Book> findBookByName(String bookName) {
        return findEntityByYardstick(bookName, connection, SQL_SELECT_BOOK_BY_NAME);
    }

    @Override
    public List<Book> findAllBook() {
        return findAll(connection, SQL_SELECT_ALL_BOOK);
    }

    @Override
    public List<Book> findBookByAuthorSurname(String authorSurname) {
        return findEntityByYardstick(authorSurname, connection, SQL_SELECT_BOOK_BY_AUTHOR_SURNAME);
    }

    @Override
    public List<Book> findBookByGenre(String genreName) {
        return findEntityByYardstick(genreName, connection, SQL_SELECT_BOOK_BY_GENRE);
    }

    @Override
    public boolean AddNewBook(Book book) {
        return addNew(book, connection, SQL_ADD_NEW_BOOK);
    }
}