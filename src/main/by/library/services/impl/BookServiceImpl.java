package main.by.library.services.impl;

import main.by.library.dao.BookDao;
import main.by.library.dao.impl.BookDaoImpl;
import main.by.library.entity.Book;
import main.by.library.services.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl() {
        bookDao = BookDaoImpl.getInstance();
    }

    @Override
    public Book findBookById(int bookId) {
        return bookDao.findBookById(bookId);
    }

    @Override
    public List<Book> findBookByName(String bookName) {
        return bookDao.findBookByName(bookName);
    }

    @Override
    public List<Book> findAllBook(int offset) {
        return bookDao.findAllBook(offset);
    }

    @Override
    public List<Book> findBookByAuthorFullName(String authorFullName) {
        return bookDao.findBookByAuthorFullName(authorFullName);
    }

    @Override
    public List<Book> findBookByGenre(String genreName) {
        return bookDao.findBookByGenre(genreName);
    }

    @Override
    public List<Book> findBookBySection(String sectionName) {
        return bookDao.findBookBySection(sectionName);
    }

    @Override
    public boolean addNewBook(Book book) {
        return bookDao.addNewBook(book);
    }

    @Override
    public boolean updateBookData(Book book) {
        return bookDao.updateBookData(book);
    }

    @Override
    public int getCountBook() {
        return bookDao.getCountBook();
    }

    @Override
    public List<Book> getByOrderId(int id) {
        return bookDao.getByOrderId(id);
    }
}
