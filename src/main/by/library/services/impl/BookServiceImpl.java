package main.by.library.services.impl;

import main.by.library.dao.BookDao;
import main.by.library.dao.impl.BookDaoImpl;
import main.by.library.entity.Book;
import main.by.library.services.BookService;

import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private static BookServiceImpl instance;

    private BookServiceImpl() {
        bookDao = BookDaoImpl.getInstance();
    }

    /**
     * Returns instance if the object has already been created
     *
     * @return instance
     */
    public static BookServiceImpl getInstance() {
        if (instance == null) {
            instance = new BookServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<Book> findBookById(int bookId) {
        return bookDao.findById(bookId);
    }

    @Override
    public List<Book> findBookByName(String bookName, int limit, int offset) {
        return bookDao.findBookByName(bookName, limit, offset);
    }

    @Override
    public List<Book> findAllBook(int limit, int offset) {
        return bookDao.findAll(limit, offset);
    }

    @Override
    public List<Book> findBookByAuthorFullName(String authorFullName, int limit, int offset) {
        return bookDao.findBookByAuthorFullName(authorFullName, limit, offset);
    }

    @Override
    public List<Book> findBookByGenre(String genreName, int limit, int offset) {
        return bookDao.findBookByGenre(genreName, limit, offset);
    }

    @Override
    public List<Book> findBookBySection(String sectionName, int limit, int offset) {
        return bookDao.findBookBySection(sectionName, limit, offset);
    }

    @Override
    public boolean addNewBook(Book book) {
        return bookDao.addNew(book);
    }

    @Override
    public boolean updateBookData(Book book) {
        return bookDao.update(book);
    }

    @Override
    public int getCountBook() {
        return bookDao.getCount();
    }

    @Override
    public List<Book> getByOrderId(int id, int limit, int offset) {
        return bookDao.getByOrderId(id, limit, offset);
    }

    @Override
    public boolean deleteBook(Book book) {
        return bookDao.delete(book);
    }
}
