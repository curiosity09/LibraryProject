package main.by.library.services;

import main.by.library.entity.Section;

import java.util.List;
import java.util.Optional;

public interface SectionService {

    List<Section> findAllSection(int limit, int offset);

    boolean addNewSection(Section section);

    Optional<Section> findSectionByName(String sectionName);

    boolean updateSection(Section section);

    boolean deleteSection(Section section);

    int getCountSection();

    boolean isSectionExist(String sectionName);

    Optional<Section> findById(int id);
}
