package main.by.library.dao.impl;

import main.by.library.dao.GenericDao;
import main.by.library.dao.SectionDao;
import main.by.library.entity.Section;
import main.by.library.jdbs.ConnectionPoolImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SectionDaoImpl extends GenericDao<Section> implements SectionDao {
    private static SectionDaoImpl instance;
    public static final String SQL_FIND_ALL_SECTION = "SELECT id, name FROM library.section OFFSET ?";
    public static final String SQL_FIND_SECTION_BY_NAME = SQL_FIND_ALL_SECTION + " WHERE name = ?";
    public static final String SQL_ADD_NEW_SECTION = "INSERT INTO library.section (name) VALUES (?)";
    public static final String SQL_UPDATE_SECTION = "UPDATE library.section SET name = ? WHERE id = ?";
    public static final String SQL_DELETE_SECTION = "DELETE FROM library.section WHERE id = ?";

    private SectionDaoImpl() {
    }

    public static SectionDaoImpl getInstance() {
        if (instance == null) {
            instance = new SectionDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Section> findAllSection(int offset) {
        return findAll(ConnectionPoolImpl.getInstance().getConnection(), SQL_FIND_ALL_SECTION, offset);
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
    public List<Section> findSectionByName(String sectionName) {
        return findAllByParameter(sectionName, ConnectionPoolImpl.getInstance().getConnection(), SQL_FIND_SECTION_BY_NAME);
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
