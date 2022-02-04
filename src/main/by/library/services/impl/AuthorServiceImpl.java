package main.by.library.services.impl;

import main.by.library.dao.AuthorDao;
import main.by.library.dao.impl.AuthorDaoImpl;
import main.by.library.entity.Author;
import main.by.library.services.AuthorService;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    public AuthorServiceImpl(){
        authorDao = AuthorDaoImpl.getInstance();
    }

    @Override
    public List<Author> findAllAuthor(int offset) {
        return authorDao.findAllAuthor(offset);
    }

    @Override
    public boolean addNewAuthor(Author author) {
        return authorDao.addNewAuthor(author);
    }

    @Override
    public List<Author> findAuthorByFullName(String authorFullName) {
        return authorDao.findAuthorByFullName(authorFullName);
    }

    @Override
    public boolean updateAuthor(Author author) {
        return authorDao.updateAuthor(author);
    }

    @Override
    public boolean deleteAuthor(Author author) {
        return authorDao.deleteAuthor(author);
    }
}
