package main.by.library.dao.impl;

import main.by.library.dao.AuthorDao;
import main.by.library.dao.GenericDaoImpl;
import main.by.library.entity.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AuthorDaoImpl extends GenericDaoImpl<Author> implements AuthorDao {

    private static AuthorDaoImpl instance;
    public static final String SQL_GET_COUNT_AUTHOR = "SELECT count(full_name) AS countRow FROM library.author";
    public static final String SQL_FIND_ALL_AUTHOR = "SELECT id, full_name FROM library.author";
    public static final String SQL_LIMIT_OFFSET = " ORDER BY id LIMIT ? OFFSET ?";
    public static final String SQL_FIND_ALL_AUTHOR_WITH_LIMIT_OFFSET = SQL_FIND_ALL_AUTHOR + SQL_LIMIT_OFFSET;
    public static final String SQL_FIND_AUTHOR_BY_FULL_NAME = SQL_FIND_ALL_AUTHOR + " WHERE full_name = ?";
    public static final String SQL_FIND_AUTHOR_BY_ID = SQL_FIND_ALL_AUTHOR + " WHERE id = ?";
    public static final String SQL_ADD_NEW_AUTHOR = "INSERT INTO library.author (full_name) VALUES (?)";
    public static final String SQL_UPDATE_AUTHOR = "UPDATE library.author SET full_name = ? WHERE id = ?";
    public static final String SQL_DELETE_AUTHOR = "DELETE FROM library.author WHERE id = ?";

    private AuthorDaoImpl() {
    }

    /**
     * Returns instance if the object has already been created
     * @return instance
     */
    public static AuthorDaoImpl getInstance() {
        if (instance == null) {
            instance = new AuthorDaoImpl();
        }
        return instance;
    }

    @Override
    public int getCount() {
        return getCountRow(connectionPool.getConnection(), SQL_GET_COUNT_AUTHOR);
    }

    @Override
    public List<Author> findAll(int limit, int offset) {
        return findAll(connectionPool.getConnection(), SQL_FIND_ALL_AUTHOR_WITH_LIMIT_OFFSET, limit, offset);
    }

    @Override
    protected Author mapToEntity(ResultSet resultSet) throws SQLException {
        return new Author(resultSet.getInt("id"),
                resultSet.getString("full_name"));
    }

    @Override
    public boolean addNew(Author author) {
        return addNewObject(author, connectionPool.getConnection(), SQL_ADD_NEW_AUTHOR);
    }

    @Override
    protected PreparedStatement setObjectsForAddMethod(PreparedStatement statement, Author author) throws SQLException {
        statement.setString(1, author.getFullName());
        return statement;
    }

    @Override
    public Optional<Author> findAuthorByFullName(String authorFullName) {
        return findEntityByParameter(connectionPool.getConnection(), SQL_FIND_AUTHOR_BY_FULL_NAME, authorFullName);
    }

    @Override
    public boolean isAuthorExist(String authorName) {
        return findAuthorByFullName(authorName).isPresent();
    }

    @Override
    public boolean update(Author author) {
        return updateObject(author, connectionPool.getConnection(), SQL_UPDATE_AUTHOR);
    }

    @Override
    protected PreparedStatement updateMapToTable(PreparedStatement statement, Author author) throws SQLException {
        statement.setString(1, author.getFullName());
        statement.setInt(2, author.getId());
        return statement;
    }

    @Override
    public boolean delete(Author author) {
        return deleteObjectById(connectionPool.getConnection(), SQL_DELETE_AUTHOR, author.getId());
    }

    @Override
    public Optional<Author> findById(int id) {
        return findEntityByParameter(connectionPool.getConnection(), SQL_FIND_AUTHOR_BY_ID, id);
    }
}
