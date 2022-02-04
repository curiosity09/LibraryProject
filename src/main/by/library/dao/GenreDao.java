package main.by.library.dao;

import main.by.library.entity.Genre;

import java.util.List;

public interface GenreDao {

    List<Genre> findAllGenre(int offset);

    boolean addNewGenre(Genre genre);

    List<Genre> findGenreByName(String genreName);

    boolean updateGenre(Genre genre);

    boolean deleteGenre(Genre genre);
}
