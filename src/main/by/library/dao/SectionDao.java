package main.by.library.dao;

import main.by.library.entity.Section;

import java.util.List;

public interface SectionDao {

    List<Section> findAllSection(int offset);

    boolean addNewSection(Section section);

    List<Section> findSectionByName(String sectionName);

    boolean updateSection(Section section);

    boolean deleteSection(Section section);
}
