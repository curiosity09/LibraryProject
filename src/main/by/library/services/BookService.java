package main.by.library.services;

import main.by.library.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> findBookById(int bookId);

    List<Book> findBookByName(String bookName, int limit, int offset);

    List<Book> findAllBook(int limit, int offset);

    List<Book> findBookByAuthorFullName(String authorSurname, int limit, int offset);

    List<Book> findBookByGenre(String genreName, int limit, int offset);

    List<Book> findBookBySection(String sectionName, int limit, int offset);

    boolean addNewBook(Book book);

    boolean updateBookData(Book book);

    int getCountBook();

    List<Book> getByOrderId(int id, int limit, int offset);

    boolean deleteBook(Book book);
}
