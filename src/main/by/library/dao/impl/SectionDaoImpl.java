package main.by.library.dao.impl;

import main.by.library.dao.GenericDaoImpl;
import main.by.library.dao.SectionDao;
import main.by.library.entity.Section;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SectionDaoImpl extends GenericDaoImpl<Section> implements SectionDao {
    private static SectionDaoImpl instance;
    public static final String SQL_GET_COUNT_SECTION = "SELECT count(name) AS countRow FROM library.section";
    public static final String SQL_FIND_ALL_SECTION = "SELECT id, name FROM library.section";
    public static final String SQL_LIMIT_OFFSET = " ORDER BY id LIMIT ? OFFSET ?";
    public static final String SQL_FIND_ALL_SECTION_WITH_LIMIT_OFFSET = SQL_FIND_ALL_SECTION + SQL_LIMIT_OFFSET;
    public static final String SQL_FIND_SECTION_BY_NAME = SQL_FIND_ALL_SECTION + " WHERE name = ?";
    public static final String SQL_FIND_SECTION_BY_ID = SQL_FIND_ALL_SECTION + " WHERE id = ?";
    public static final String SQL_ADD_NEW_SECTION = "INSERT INTO library.section (name) VALUES (?)";
    public static final String SQL_UPDATE_SECTION = "UPDATE library.section SET name = ? WHERE id = ?";
    public static final String SQL_DELETE_SECTION = "DELETE FROM library.section WHERE id = ?";

    private SectionDaoImpl() {
    }

    /**
     * Returns instance if the object has already been created
     * @return instance
     */
    public static SectionDaoImpl getInstance() {
        if (instance == null) {
            instance = new SectionDaoImpl();
        }
        return instance;
    }

    @Override
    public int getCount() {
        return getCountRow(connectionPool.getConnection(), SQL_GET_COUNT_SECTION);
    }

    @Override
    public boolean isSectionExist(String sectionName) {
        return findSectionByName(sectionName).isPresent();
    }

    @Override
    public List<Section> findAll(int limit, int offset) {
        return findAll(connectionPool.getConnection(), SQL_FIND_ALL_SECTION_WITH_LIMIT_OFFSET, limit, offset);
    }

    @Override
    protected Section mapToEntity(ResultSet resultSet) throws SQLException {
        return new Section(resultSet.getInt("id"), resultSet.getString("name"));
    }

    @Override
    public boolean addNew(Section section) {
        return addNewObject(section, connectionPool.getConnection(), SQL_ADD_NEW_SECTION);
    }

    @Override
    protected PreparedStatement setObjectsForAddMethod(PreparedStatement statement, Section section) throws SQLException {
        statement.setString(1, section.getName());
        return statement;
    }

    @Override
    public Optional<Section> findSectionByName(String sectionName) {
        return findEntityByParameter(connectionPool.getConnection(), SQL_FIND_SECTION_BY_NAME, sectionName);
    }

    @Override
    public boolean update(Section section) {
        return updateObject(section, connectionPool.getConnection(), SQL_UPDATE_SECTION);
    }

    @Override
    protected PreparedStatement updateMapToTable(PreparedStatement statement, Section section) throws SQLException {
        statement.setString(1, section.getName());
        statement.setInt(2, section.getId());
        return statement;
    }

    @Override
    public boolean delete(Section section) {
        return deleteObjectById(connectionPool.getConnection(), SQL_DELETE_SECTION, section.getId());
    }

    @Override
    public Optional<Section> findById(int id) {
        return findEntityByParameter(connectionPool.getConnection(), SQL_FIND_SECTION_BY_ID, id);
    }
}
