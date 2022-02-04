package main.by.library.services;

import main.by.library.entity.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> findAllGenre(int offset);

    boolean addNewGenre(Genre genre);

    List<Genre> findGenreByName(String genreName);

    boolean updateGenre(Genre genre);

    boolean deleteGenre(Genre genre);
}
