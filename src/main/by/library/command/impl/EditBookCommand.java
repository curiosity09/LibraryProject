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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class EditBookCommand implements Command, PageUtil, LoggerUtil {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UtilCommand.showAllAuthor(req, authorService.getCountAuthor());
        UtilCommand.showAllSection(req, sectionService.getCountSection());
        UtilCommand.showAllGenre(req, genreService.getCountGenre());
        req.getRequestDispatcher(req.getContextPath() + JSPUtil.getLibrarianJSPPath(EDIT_BOOK_PAGE)).include(req, resp);
        String bookId = req.getParameter(BOOK_ID_PARAMETER);
        int offset = Integer.parseInt(req.getParameter(OFFSET_PARAMETER));
        Validator<Book> bookValidator = new BookValidator();
        Optional<Book> optionalBook = bookValidator.validate(req);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setId(Integer.parseInt(bookId));
            if (bookService.updateBookData(book)) {
                return new CommandResult(req.getContextPath()
                        + JSPUtil.getControllerCommandPath(SHOW_ALL_BOOKS_COMMAND
                        + AMPERSAND + OFFSET_PARAMETER + EQUAL_SIGH + offset), CommandResult.REDIRECT);
            } else {
                return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
            }
        } else {
            return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
        }
    }
}
