package main.by.library.command.impl;

import main.by.library.command.Command;
import main.by.library.command.CommandResult;
import main.by.library.util.UtilCommand;
import main.by.library.entity.Book;
import main.by.library.entity.User;
import main.by.library.util.JSPUtil;
import main.by.library.util.LoggerUtil;
import main.by.library.util.PageUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

public class ShowAllBooksCommand implements Command, PageUtil, LoggerUtil {

    private static final Logger LOGGER = LogManager.getLogger(ShowAllBooksCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.log(Level.INFO, ENTER_METHOD_MESSAGE);
        UtilCommand.getListOffset(req, bookService.getCountBook());
        int offset = Integer.parseInt(req.getParameter(OFFSET_PARAMETER));
        List<Book> allBook = bookService.findAllBook(LIMIT_TEN, offset);
        req.setAttribute(BOOKS_ATTRIBUTE, allBook);
        User user = (User) req.getSession().getAttribute(USER_ATTRIBUTE);
        if (Objects.equals(user.getRole(), User.ROLE_USER)) {
            return new CommandResult(req.getContextPath() + JSPUtil.getUserJSPPath(ALL_BOOKS_PAGE),CommandResult.FORWARD);
        } else {
            return new CommandResult(req.getContextPath() + JSPUtil.getLibrarianJSPPath(ALL_BOOKS_PAGE),CommandResult.FORWARD);
        }
    }
}
