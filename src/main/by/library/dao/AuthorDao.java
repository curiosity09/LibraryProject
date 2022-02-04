package main.by.library.dao;

import main.by.library.entity.Author;

import java.util.List;

public interface AuthorDao {

    List<Author> findAllAuthor(int offset);

    boolean addNewAuthor(Author author);

    List<Author> findAuthorByFullName(String authorSurname);

    boolean updateAuthor(Author author);

    boolean deleteAuthor(Author author);
}
