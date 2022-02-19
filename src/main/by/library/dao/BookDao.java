package main.by.library.dao;

import main.by.library.entity.Book;

import java.util.List;

public interface BookDao extends GenericDao<Book>{

    List<Book> findBookByName(String bookName, int limit, int offset);

    List<Book> findBookByAuthorFullName(String authorSurname, int limit, int offset);

    List<Book> findBookByGenre(String genreName, int limit, int offset);

    List<Book> findBookBySection(String sectionName, int limit, int offset);

    List<Book> getByOrderId(int id, int limit, int offset);
}
