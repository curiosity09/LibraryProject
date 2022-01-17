package main.by.library.dao;

import main.by.library.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    List<Book> findBookByName(String bookName);

    List<Book> findAllBook();

    List<Book> findBookByAuthorSurname(String authorSurname);

    List<Book> findBookByGenre(String genreName);

    boolean AddNewBook(Book book);
}
