package main.by.library.services;

import main.by.library.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<Author> findAllAuthor(int limit, int offset);

    boolean addNewAuthor(Author author);

    Optional<Author> findAuthorByFullName(String authorFullName);

    boolean updateAuthor(Author author);

    boolean deleteAuthor(Author author);

    int getCountAuthor();

    boolean isAuthorExist(String authorName);

    Optional<Author> findById(int id);
}
