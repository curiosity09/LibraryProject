package main.by.library.services;

import main.by.library.entity.Author;

import java.util.List;

public interface AuthorService {

    List<Author> findAllAuthor(int offset);

    boolean addNewAuthor(Author author);

    List<Author> findAuthorByFullName(String authorSurname);

    boolean updateAuthor(Author author);

    boolean deleteAuthor(Author author);
}
