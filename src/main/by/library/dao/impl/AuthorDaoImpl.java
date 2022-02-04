package main.by.library.dao.impl;

import main.by.library.dao.AuthorDao;
import main.by.library.dao.GenericDao;
import main.by.library.entity.Author;
import main.by.library.jdbs.ConnectionPoolImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AuthorDaoImpl extends GenericDao<Author> implements AuthorDao {

    private static AuthorDaoImpl instance;
    public static final String SQL_FIND_ALL_AUTHOR = "SELECT id, full_name FROM library.author OFFSET ?";
    public static final String SQL_FIND_AUTHOR_BY_FULL_NAME = SQL_FIND_ALL_AUTHOR + " WHERE full_name = ?";
    public static final String SQL_ADD_NEW_AUTHOR = "INSERT INTO library.author (full_name) VALUES (?)";
    public static final String SQL_UPDATE_AUTHOR = "UPDATE library.author SET full_name = ? WHERE id = ?";
    public static final String SQL_DELETE_AUTHOR = "DELETE FROM library.author WHERE id = ?";

    private AuthorDaoImpl() {
    }

    public static AuthorDaoImpl getInstance() {
        if (instance == null) {
            instance = new AuthorDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Author> findAllAuthor(int offset) {
        return findAll(ConnectionPoolImpl.getInstance().getConnection(), SQL_FIND_ALL_AUTHOR, offset);
    }

    @Override
    protected Author mapToEntity(ResultSet resultSet) throws SQLException {
        return new Author(resultSet.getInt("id"),
                resultSet.getString("full_name"));
    }

    @Override
    public boolean addNewAuthor(Author author) {
        return addNewObject(author, ConnectionPoolImpl.getInstance().getConnection(), SQL_ADD_NEW_AUTHOR);
    }

    @Override
    protected PreparedStatement setObjectsForAddMethod(PreparedStatement statement, Author author) throws SQLException {
        statement.setString(1, author.getFullName());
        return statement;
    }

    @Override
    public List<Author> findAuthorByFullName(String authorFullName) {
        return findAllByParameter(authorFullName, ConnectionPoolImpl.getInstance().getConnection(), SQL_FIND_AUTHOR_BY_FULL_NAME);
    }

    @Override
    public boolean updateAuthor(Author author) {
        return updateObject(author, ConnectionPoolImpl.getInstance().getConnection(), SQL_UPDATE_AUTHOR);
    }

    @Override
    protected PreparedStatement updateMapToTable(PreparedStatement statement, Author author) throws SQLException {
        statement.setString(1, author.getFullName());
        statement.setInt(2, author.getId());
        return statement;
    }

    @Override
    public boolean deleteAuthor(Author author) {
        return deleteObjectById(ConnectionPoolImpl.getInstance().getConnection(), SQL_DELETE_AUTHOR, author.getId());
    }
}
