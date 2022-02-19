package main.by.library.services.impl;

import main.by.library.dao.AuthorDao;
import main.by.library.dao.impl.AuthorDaoImpl;
import main.by.library.entity.Author;
import main.by.library.services.AuthorService;

import java.util.List;
import java.util.Optional;

public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    public AuthorServiceImpl(){
        authorDao = AuthorDaoImpl.getInstance();
    }

    @Override
    public List<Author> findAllAuthor(int limit, int offset) {
        return authorDao.findAll(limit, offset);
    }

    @Override
    public boolean addNewAuthor(Author author) {
        return authorDao.addNew(author);
    }

    @Override
    public Optional<Author> findAuthorByFullName(String authorFullName) {
        return authorDao.findAuthorByFullName(authorFullName);
    }

    @Override
    public boolean updateAuthor(Author author) {
        return authorDao.update(author);
    }

    @Override
    public boolean deleteAuthor(Author author) {
        return authorDao.delete(author);
    }

    @Override
    public int getCountAuthor() {
        return authorDao.getCount();
    }

    @Override
    public boolean isAuthorExist(String authorName) {
        return authorDao.isAuthorExist(authorName);
    }

    @Override
    public Optional<Author> findById(int id) {
        return authorDao.findById(id);
    }
}
