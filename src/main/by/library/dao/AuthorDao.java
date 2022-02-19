package main.by.library.dao;

import main.by.library.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao extends GenericDao<Author> {

    Optional<Author> findAuthorByFullName(String authorFullName);

    boolean isAuthorExist(String authorName);

}
