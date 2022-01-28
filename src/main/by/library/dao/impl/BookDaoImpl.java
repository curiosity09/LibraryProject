package main.by.library.dao.impl;

import main.by.library.dao.BookDao;
import main.by.library.dao.GenericDAO;
import main.by.library.entity.*;
import main.by.library.jdbs.ConnectionPoolImpl;

import java.sql.*;
import java.util.List;

public class BookDaoImpl extends GenericDAO<Book> implements BookDao {

    public static final String SQL_UPDATE_BOOK_DATA = "UPDATE library.book SET author_id = ?, genre_id = ?, section_id = ?, quantity = ?, publication_year = ? WHERE name = ?";
    private static BookDaoImpl instance;
    public static final String SQL_GET_QUANTITY_BOOK = "SELECT quantity FROM library.book WHERE name = (?) OR id = (?)";
    public static final String SQL_SELECT_BOOK_BY_NAME = "SELECT b.name, b.publication_year, a.first_name AS first_name, a.last_name AS last_name, g.name AS genre_name,b.quantity,s.name AS section_name FROM library.book b JOIN library.author a ON b.author_id = a.id JOIN library.genre g ON b.genre_id = g.id JOIN library.section s ON b.section_id = s.id GROUP BY b.name, b.publication_year, a.first_name,a.last_name, g.name,b.quantity,s.name HAVING b.name = (?)";
    public static final String SQL_SELECT_ALL_BOOK = "SELECT b.name, b.publication_year, a.first_name AS first_name, a.last_name AS last_name, g.name AS genre_name,b.quantity,s.name AS section_name FROM library.book b JOIN library.author a ON b.author_id = a.id JOIN library.genre g ON b.genre_id = g.id JOIN library.section s ON b.section_id = s.id GROUP BY b.name, b.publication_year, a.first_name,a.last_name, g.name,b.quantity,s.name";
    public static final String SQL_SELECT_BOOK_BY_AUTHOR_SURNAME = "SELECT b.name, b.publication_year, a.first_name AS first_name, a.last_name AS last_name, g.name AS genre_name,b.quantity,s.name AS section_name FROM library.book b JOIN library.author a ON b.author_id = a.id JOIN library.genre g ON b.genre_id = g.id JOIN library.section s ON b.section_id = s.id GROUP BY b.name, b.publication_year, a.first_name,a.last_name, g.name,b.quantity,s.name HAVING a.last_name = (?)";
    public static final String SQL_SELECT_BOOK_BY_GENRE = "SELECT b.name, b.publication_year, a.first_name AS first_name, a.last_name AS last_name, g.name AS genre_name,b.quantity,s.name AS section_name FROM library.book b JOIN library.author a ON b.author_id = a.id JOIN library.genre g ON b.genre_id = g.id JOIN library.section s ON b.section_id = s.id GROUP BY b.name, b.publication_year, a.first_name,a.last_name, g.name,b.quantity,s.name HAVING g.name = (?)";
    public static final String SQL_SELECT_BOOK_BY_SECTION = "SELECT b.name, b.publication_year, a.first_name AS first_name, a.last_name AS last_name, g.name AS genre_name,b.quantity,s.name AS section_name FROM library.book b JOIN library.author a ON b.author_id = a.id JOIN library.genre g ON b.genre_id = g.id JOIN library.section s ON b.section_id = s.id GROUP BY b.name, b.publication_year, a.first_name,a.last_name, g.name,b.quantity,s.name HAVING s.name = (?)";
    public static final String SQL_ADD_NEW_BOOK = "INSERT INTO library.book (name, author_id, genre_id,section_id,quantity, publication_year) VALUES ((?), (SELECT id FROM library.author WHERE last_name = (?) AND first_name = (?)), (SELECT id FROM library.genre WHERE name = (?)),(SELECT id FROM library.section WHERE name = (?)),(?), (?))";

    private BookDaoImpl() {
    }

    public static BookDaoImpl getInstance() {
        if (instance == null) {
            instance = new BookDaoImpl();
        }
        return instance;
    }

    @Override
    protected Book mapToEntityForGlobalSearch(ResultSet resultSet) throws SQLException {
        Author authorForResult = new Author(resultSet.getString("first_name"), resultSet.getString("last_name"));
        Genre genreForResult = new Genre(resultSet.getString("genre_name"));
        Section sectionForResult = new Section(resultSet.getString("section_name"));
        return new Book(
                resultSet.getString("name"),
                authorForResult,
                genreForResult,
                sectionForResult,
                resultSet.getInt("quantity"),
                resultSet.getInt("publication_year"));
    }

    @Override
    protected void setFieldToStatement(PreparedStatement bookStatement, Book book) throws SQLException {
        bookStatement.setString(1, book.getName());
        bookStatement.setString(2, book.getAuthor().getLastName());
        bookStatement.setString(3, book.getAuthor().getFirstName());
        bookStatement.setString(4, book.getGenre().getName());
        bookStatement.setString(5, book.getSection().getName());
        bookStatement.setInt(6, book.getQuantity());
        bookStatement.setInt(7, book.getPublicationYear());
    }

    @Override
    protected Book mapToEntityForSingleSearch(ResultSet resultSet) throws SQLException {
        return mapToEntityForGlobalSearch(resultSet);
    }

    @Override
    public boolean updateBookData(Book book) {
        return updateObject(book, ConnectionPoolImpl.getInstance().getConnection(), SQL_UPDATE_BOOK_DATA);
    }

    @Override
    protected PreparedStatement updateMapToTable(PreparedStatement statement, Book book) throws SQLException {
        statement.setInt(1, book.getAuthor().getId());
        statement.setInt(2, book.getGenre().getId());
        statement.setInt(3, book.getSection().getId());
        statement.setInt(4, book.getQuantity());
        statement.setInt(5, book.getPublicationYear());
        statement.setString(6, book.getName());
        return statement;
    }

    @Override
    public List<Book> findBookByName(String bookName) {
        return findEntityByYardstick(bookName, ConnectionPoolImpl.getInstance().getConnection(), SQL_SELECT_BOOK_BY_NAME);
    }

    @Override
    public List<Book> findAllBook() {
        return findAll(ConnectionPoolImpl.getInstance().getConnection(), SQL_SELECT_ALL_BOOK);
    }

    @Override
    public List<Book> findBookByAuthorSurname(String authorSurname) {
        return findEntityByYardstick(authorSurname, ConnectionPoolImpl.getInstance().getConnection(), SQL_SELECT_BOOK_BY_AUTHOR_SURNAME);
    }

    @Override
    public List<Book> findBookByGenre(String genreName) {
        return findEntityByYardstick(genreName, ConnectionPoolImpl.getInstance().getConnection(), SQL_SELECT_BOOK_BY_GENRE);
    }

    @Override
    public List<Book> findBookBySection(String sectionName) {
        return findEntityByYardstick(sectionName, ConnectionPoolImpl.getInstance().getConnection(), SQL_SELECT_BOOK_BY_SECTION);
    }

    @Override
    public boolean AddNewBook(Book book) {
        return addNew(book, ConnectionPoolImpl.getInstance().getConnection(), SQL_ADD_NEW_BOOK);
        //TODO quantity book?
        //TODO commit rollback
    }
}
