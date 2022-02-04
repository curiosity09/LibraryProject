package main.by.library.services;

import main.by.library.entity.Section;

import java.util.List;

public interface SectionService {

    List<Section> findAllSection(int offset);

    boolean addNewSection(Section section);

    List<Section> findSectionByName(String sectionName);

    boolean updateSection(Section section);

    boolean deleteSection(Section section);
}
