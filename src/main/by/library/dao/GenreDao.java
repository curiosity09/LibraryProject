package main.by.library.dao;

import main.by.library.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao extends GenericDao<Genre>{

    Optional<Genre> findGenreByName(String genreName);

    boolean isGenreExist(String genreName);
}
