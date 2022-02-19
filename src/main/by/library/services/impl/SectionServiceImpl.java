package main.by.library.services.impl;

import main.by.library.dao.SectionDao;
import main.by.library.dao.impl.SectionDaoImpl;
import main.by.library.entity.Section;
import main.by.library.services.SectionService;

import java.util.List;
import java.util.Optional;

public class SectionServiceImpl implements SectionService {

    private final SectionDao sectionDao;

    public SectionServiceImpl() {
        sectionDao = SectionDaoImpl.getInstance();
    }

    @Override
    public List<Section> findAllSection(int limit, int offset) {
        return sectionDao.findAll(limit, offset);
    }

    @Override
    public boolean addNewSection(Section section) {
        return sectionDao.addNew(section);
    }

    @Override
    public Optional<Section> findSectionByName(String sectionName) {
        return sectionDao.findSectionByName(sectionName);
    }

    @Override
    public boolean updateSection(Section section) {
        return sectionDao.update(section);
    }

    @Override
    public boolean deleteSection(Section section) {
        return sectionDao.delete(section);
    }

    @Override
    public int getCountSection() {
        return sectionDao.getCount();
    }

    @Override
    public boolean isSectionExist(String sectionName) {
        return sectionDao.isSectionExist(sectionName);
    }

    @Override
    public Optional<Section> findById(int id) {
        return sectionDao.findById(id);
    }
}
