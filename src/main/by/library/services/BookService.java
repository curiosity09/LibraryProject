package main.by.library.services;

import main.by.library.entity.Book;

import java.sql.Connection;
import java.util.List;

public interface BookService {

    Book findBookById(int bookId);

    List<Book> findBookByName(String bookName);

    List<Book> findAllBook(int offset);

    List<Book> findBookByAuthorFullName(String authorSurname);

    List<Book> findBookByGenre(String genreName);

    List<Book> findBookBySection(String sectionName);

    boolean addNewBook(Book book);

    boolean updateBookData(Book book);

    int getCountBook();

    List<Book> getByOrderId(int id);
}
