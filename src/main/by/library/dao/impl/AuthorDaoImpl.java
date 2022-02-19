package main.by.library.dao.impl;

import main.by.library.dao.AuthorDao;
import main.by.library.dao.GenericDaoImpl;
import main.by.library.entity.Author;
import main.by.library.jdbs.ConnectionPoolImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AuthorDaoImplImpl extends GenericDaoImpl<Author> implements AuthorDao {

    private static AuthorDaoImplImpl instance;
    public static final String SQL_GET_COUNT_AUTHOR = "SELECT count(full_name) AS countRow FROM library.author";
    public static final String SQL_FIND_ALL_AUTHOR = "SELECT id, full_name FROM library.author";
    public static final String SQL_LIMIT_OFFSET = " ORDER BY id LIMIT ? OFFSET ?";
    public static final String SQL_FIND_ALL_AUTHOR_WITH_LIMIT_OFFSET = SQL_FIND_ALL_AUTHOR + SQL_LIMIT_OFFSET;
    public static final String SQL_FIND_AUTHOR_BY_FULL_NAME = SQL_FIND_ALL_AUTHOR + " WHERE full_name = ?";
    public static final String SQL_ADD_NEW_AUTHOR = "INSERT INTO library.author (full_name) VALUES (?)";
    public static final String SQL_UPDATE_AUTHOR = "UPDATE library.author SET full_name = ? WHERE id = ?";
    public static final String SQL_DELETE_AUTHOR = "DELETE FROM library.author WHERE id = ?";

    private AuthorDaoImplImpl() {
    }

    public static AuthorDaoImplImpl getInstance() {
        if (instance == null) {
            instance = new AuthorDaoImplImpl();
        }
        return instance;
    }

    @Override
    public int getCountAuthor() {
        return getCountRow(ConnectionPoolImpl.getInstance().getConnection(), SQL_GET_COUNT_AUTHOR);
    }

    @Override
    public List<Author> findAllAuthor(int limit, int offset) {
        return findAll(ConnectionPoolImpl.getInstance().getConnection(), SQL_FIND_ALL_AUTHOR_WITH_LIMIT_OFFSET, limit, offset);
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
    public Optional<Author> findAuthorByFullName(String authorFullName) {
        return findEntityByParameter(ConnectionPoolImpl.getInstance().getConnection(), SQL_FIND_AUTHOR_BY_FULL_NAME, authorFullName);
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
