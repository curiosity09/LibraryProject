package main.by.library.command.impl;

import main.by.library.command.Command;
import main.by.library.command.CommandResult;
import main.by.library.util.UtilCommand;
import main.by.library.entity.Book;
import main.by.library.util.JSPUtil;
import main.by.library.util.LoggerUtil;
import main.by.library.util.PageUtil;
import main.by.library.validate.Validator;
import main.by.library.validate.impl.BookValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Optional;

public class AddNewBookCommand implements Command, PageUtil, LoggerUtil {

    private static final Logger LOGGER = LogManager.getLogger(AddNewBookCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.log(Level.INFO, ENTER_METHOD_MESSAGE);
        UtilCommand.showAllAuthor(req, authorService.getCountAuthor());
        UtilCommand.showAllGenre(req, genreService.getCountGenre());
        UtilCommand.showAllSection(req, sectionService.getCountSection());
        req.getRequestDispatcher(req.getContextPath() + JSPUtil.getLibrarianJSPPath(ADD_BOOK_PAGE)).include(req, resp);
        Validator<Book> bookValidator = new BookValidator();
        Optional<Book> optionalBook = bookValidator.validate(req);
        LOGGER.log(Level.INFO, PARAM_REQUEST_MESSAGE);
        if (optionalBook.isPresent()) {
            LOGGER.log(Level.INFO, PARAM_RECEIVE_MESSAGE);
            if (bookService.addNewBook(optionalBook.get())) {
                return new CommandResult(req.getContextPath() + JSPUtil.getLibrarianJSPPath(LIBRARIAN_PAGE), CommandResult.REDIRECT);
            }
            LOGGER.log(Level.INFO, ERROR_BLOCK_MESSAGE);
            return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
        }
        return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
    }
}
