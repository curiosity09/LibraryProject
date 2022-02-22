package main.by.library.command;

import main.by.library.services.*;
import main.by.library.services.impl.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {

    BookService bookService = new BookServiceImpl();
    UserService userService = new UserServiceImpl();
    AuthorService authorService = new AuthorServiceImpl();
    GenreService genreService = new GenreServiceImpl();
    SectionService sectionService = new SectionServiceImpl();
    OrderService orderService = new OrderServiceImpl();

    CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
