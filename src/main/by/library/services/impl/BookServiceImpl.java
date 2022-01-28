package main.by.library.services.impl;

import main.by.library.dao.BookDao;
import main.by.library.dao.impl.BookDaoImpl;
import main.by.library.entity.Book;
import main.by.library.services.BookService;

import java.sql.Connection;

public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public BookServiceImpl() {
        bookDao = BookDaoImpl.getInstance();
    }

}
