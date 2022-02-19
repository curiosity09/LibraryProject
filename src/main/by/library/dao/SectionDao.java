package main.by.library.dao;

import main.by.library.entity.Section;

import java.util.List;
import java.util.Optional;

public interface SectionDao extends GenericDao<Section> {

    Optional<Section> findSectionByName(String sectionName);

    boolean isSectionExist(String sectionName);
}
