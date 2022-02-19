package main.by.library.services;

import main.by.library.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    List<Genre> findAllGenre(int limit, int offset);

    boolean addNewGenre(Genre genre);

    Optional<Genre> findGenreByName(String genreName);

    boolean updateGenre(Genre genre);

    boolean deleteGenre(Genre genre);

    int getCountGenre();

    boolean isGenreExist(String genreName);

    Optional<Genre> findById(int id);
}
