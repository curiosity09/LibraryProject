package main.by.library.services.impl;

import main.by.library.dao.GenreDao;
import main.by.library.dao.impl.GenreDaoImpl;
import main.by.library.entity.Genre;
import main.by.library.services.GenreService;

import java.util.List;
import java.util.Optional;

public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;
    private static GenreServiceImpl instance;

    private GenreServiceImpl(){
        genreDao = GenreDaoImpl.getInstance();
    }

    /**
     * Returns instance if the object has already been created
     *
     * @return instance
     */
    public static GenreServiceImpl getInstance() {
        if (instance == null) {
            instance = new GenreServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Genre> findAllGenre(int limit, int offset) {
        return genreDao.findAll(limit, offset);
    }

    @Override
    public boolean addNewGenre(Genre genre) {
        return genreDao.addNew(genre);
    }

    @Override
    public Optional<Genre> findGenreByName(String genreName) {
        return genreDao.findGenreByName(genreName);
    }

    @Override
    public boolean updateGenre(Genre genre) {
        return genreDao.update(genre);
    }

    @Override
    public boolean deleteGenre(Genre genre) {
        return genreDao.delete(genre);
    }

    @Override
    public int getCountGenre() {
        return genreDao.getCount();
    }

    @Override
    public boolean isGenreExist(String genreName) {
        return genreDao.isGenreExist(genreName);
    }

    @Override
    public Optional<Genre> findById(int id) {
        return genreDao.findById(id);
    }
}
