package main.by.library.services.impl;

import main.by.library.dao.GenreDao;
import main.by.library.dao.impl.GenreDaoImpl;
import main.by.library.entity.Genre;
import main.by.library.services.GenreService;

import java.util.List;

public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    public GenreServiceImpl(){
        genreDao = GenreDaoImpl.getInstance();
    }

    @Override
    public List<Genre> findAllGenre(int offset) {
        return genreDao.findAllGenre(offset);
    }

    @Override
    public boolean addNewGenre(Genre genre) {
        return genreDao.addNewGenre(genre);
    }

    @Override
    public List<Genre> findGenreByName(String genreName) {
        return genreDao.findGenreByName(genreName);
    }

    @Override
    public boolean updateGenre(Genre genre) {
        return genreDao.updateGenre(genre);
    }

    @Override
    public boolean deleteGenre(Genre genre) {
        return genreDao.deleteGenre(genre);
    }
}
