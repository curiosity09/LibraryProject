package main.by.library.dao.impl;

import main.by.library.dao.GenericDaoImpl;
import main.by.library.dao.SectionDao;
import main.by.library.entity.Section;
import main.by.library.jdbs.ConnectionPoolImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SectionDaoImplImpl extends GenericDaoImpl<Section> implements SectionDao {
    private static SectionDaoImplImpl instance;
    public static final String SQL_GET_COUNT_SECTION = "SELECT count(name) AS countRow FROM library.section";
    public static final String SQL_FIND_ALL_SECTION = "SELECT id, name FROM library.section";
    public static final String SQL_LIMIT_OFFSET = " ORDER BY id LIMIT ? OFFSET ?";
    public static final String SQL_FIND_ALL_SECTION_WITH_LIMIT_OFFSET = SQL_FIND_ALL_SECTION + SQL_LIMIT_OFFSET;
    public static final String SQL_FIND_SECTION_BY_NAME = SQL_FIND_ALL_SECTION + " WHERE name = ?";
    public static final String SQL_ADD_NEW_SECTION = "INSERT INTO library.section (name) VALUES (?)";
    public static final String SQL_UPDATE_SECTION = "UPDATE library.section SET name = ? WHERE id = ?";
    public static final String SQL_DELETE_SECTION = "DELETE FROM library.section WHERE id = ?";

    private SectionDaoImplImpl() {
    }

    public static SectionDaoImplImpl getInstance() {
        if (instance == null) {
            instance = new SectionDaoImplImpl();
        }
        return instance;
    }

    @Override
    public int getCountSection() {
        return getCountRow(ConnectionPoolImpl.getInstance().getConnection(), SQL_GET_COUNT_SECTION);
    }

    @Override
    public List<Section> findAllSection(int limit, int offset) {
        return findAll(ConnectionPoolImpl.getInstance().getConnection(), SQL_FIND_ALL_SECTION_WITH_LIMIT_OFFSET, limit, offset);
    }

    @Override
    protected Section mapToEntity(ResultSet resultSet) throws SQLException {
        return new Section(resultSet.getInt("id"), resultSet.getString("name"));
    }

    @Override
    public boolean addNewSection(Section section) {
        return addNewObject(section, ConnectionPoolImpl.getInstance().getConnection(), SQL_ADD_NEW_SECTION);
    }

    @Override
    protected PreparedStatement setObjectsForAddMethod(PreparedStatement statement, Section section) throws SQLException {
        statement.setString(1, section.getName());
        return statement;
    }

    @Override
    public Optional<Section> findSectionByName(String sectionName) {
        return findEntityByParameter(ConnectionPoolImpl.getInstance().getConnection(), SQL_FIND_SECTION_BY_NAME, sectionName);
    }

    @Override
    public boolean updateSection(Section section) {
        return updateObject(section, ConnectionPoolImpl.getInstance().getConnection(), SQL_UPDATE_SECTION);
    }

    @Override
    protected PreparedStatement updateMapToTable(PreparedStatement statement, Section section) throws SQLException {
        statement.setString(1, section.getName());
        statement.setInt(2, section.getId());
        return statement;
    }

    @Override
    public boolean deleteSection(Section section) {
        return deleteObjectById(ConnectionPoolImpl.getInstance().getConnection(), SQL_DELETE_SECTION, section.getId());
    }
}
