package main.by.library.command.impl;

import main.by.library.command.Command;
import main.by.library.command.CommandResult;
import main.by.library.entity.Book;
import main.by.library.entity.ShoppingCart;
import main.by.library.util.JSPUtil;
import main.by.library.util.LoggerUtil;
import main.by.library.util.PageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class DelBookFromOrderCommand implements Command, LoggerUtil, PageUtil {

    private static final Logger LOGGER = LogManager.getLogger(DelBookFromOrderCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info(ENTER_METHOD_MESSAGE);
        ShoppingCart selectedBooks = (ShoppingCart) req.getSession().getAttribute(SHOPPING_CART_ATTRIBUTE);
        int bookId = Integer.parseInt(req.getParameter(BOOK_ID_PARAMETER));
        if (!selectedBooks.getShoppingList().isEmpty()) {
            Optional<Book> bookById = bookService.findBookById(bookId);
            bookById.ifPresent(book -> selectedBooks.getShoppingList().remove(book));
            req.getSession().setAttribute(SHOPPING_CART_ATTRIBUTE, selectedBooks);
            LOGGER.info("Set attribute");
            return new CommandResult(req.getContextPath() + JSPUtil.getUserJSPPath(SHOPPING_CART_PAGE),CommandResult.REDIRECT);
        }
        return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
    }
}
