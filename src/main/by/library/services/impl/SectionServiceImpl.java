package main.by.library.services.impl;

import main.by.library.dao.SectionDao;
import main.by.library.dao.impl.SectionDaoImpl;
import main.by.library.entity.Section;
import main.by.library.services.SectionService;

import java.util.List;

public class SectionServiceImpl implements SectionService {

    private final SectionDao sectionDao;

    public SectionServiceImpl() {
        sectionDao = SectionDaoImpl.getInstance();
    }

    @Override
    public List<Section> findAllSection(int offset) {
        return sectionDao.findAllSection(offset);
    }

    @Override
    public boolean addNewSection(Section section) {
        return sectionDao.addNewSection(section);
    }

    @Override
    public List<Section> findSectionByName(String sectionName) {
        return sectionDao.findSectionByName(sectionName);
    }

    @Override
    public boolean updateSection(Section section) {
        return sectionDao.updateSection(section);
    }

    @Override
    public boolean deleteSection(Section section) {
        return sectionDao.deleteSection(section);
    }
}
