package main.by.library.dao.impl;

import main.by.library.dao.GenericDaoImpl;
import main.by.library.dao.GenreDao;
import main.by.library.entity.Genre;
import main.by.library.jdbs.ConnectionPoolImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class GenreDaoImplImpl extends GenericDaoImpl<Genre> implements GenreDao {

    public static final String SQL_GET_COUNT_GENRE = "SELECT count(name) AS countRow FROM library.genre";
    public static final String SQL_FIND_ALL_GENRE = "SELECT id, name FROM library.genre";
    public static final String SQL_LIMIT_OFFSET = " ORDER BY id LIMIT ? OFFSET ?";
    public static final String SQL_FIND_ALL_GENRE_WITH_LIMIT_OFFSET = SQL_FIND_ALL_GENRE + SQL_LIMIT_OFFSET;
    public static final String SQL_FIND_GENRE_BY_NAME = SQL_FIND_ALL_GENRE + " WHERE name = ?";
    public static final String SQL_ADD_NEW_GENRE = "INSERT INTO library.genre (name) VALUES (?)";
    public static final String SQL_UPDATE_GENRE = "UPDATE library.genre SET name = ? WHERE id = ?";
    public static final String SQL_DELETE_GENRE = "DELETE FROM library.genre WHERE id = ?";
    private static GenreDaoImplImpl instance;

    private GenreDaoImplImpl() {
    }

    public static GenreDaoImplImpl getInstance() {
        if (instance == null) {
            instance = new GenreDaoImplImpl();
        }
        return instance;
    }

    @Override
    public int getCountGenre() {
        return getCountRow(ConnectionPoolImpl.getInstance().getConnection(), SQL_GET_COUNT_GENRE);
    }

    @Override
    public List<Genre> findAllGenre(int limit, int offset) {
        return findAll(ConnectionPoolImpl.getInstance().getConnection(), SQL_FIND_ALL_GENRE_WITH_LIMIT_OFFSET, limit, offset);
    }

    @Override
    protected Genre mapToEntity(ResultSet resultSet) throws SQLException {
        return new Genre(resultSet.getInt("id"), resultSet.getString("name"));
    }

    @Override
    public boolean addNewGenre(Genre genre) {
        return addNewObject(genre, ConnectionPoolImpl.getInstance().getConnection(), SQL_ADD_NEW_GENRE);
    }

    @Override
    protected PreparedStatement setObjectsForAddMethod(PreparedStatement statement, Genre genre) throws SQLException {
        statement.setString(1, genre.getName());
        return statement;
    }

    @Override
    public Optional<Genre> findGenreByName(String genreName) {
        return findEntityByParameter(ConnectionPoolImpl.getInstance().getConnection(), SQL_FIND_GENRE_BY_NAME, genreName);
    }

    @Override
    public boolean updateGenre(Genre genre) {
        return updateObject(genre, ConnectionPoolImpl.getInstance().getConnection(), SQL_UPDATE_GENRE);
    }

    @Override
    protected PreparedStatement updateMapToTable(PreparedStatement statement, Genre genre) throws SQLException {
        statement.setString(1, genre.getName());
        statement.setInt(2, genre.getId());
        return statement;
    }

    @Override
    public boolean deleteGenre(Genre genre) {
        return deleteObjectById(ConnectionPoolImpl.getInstance().getConnection(), SQL_DELETE_GENRE, genre.getId());
    }
}
