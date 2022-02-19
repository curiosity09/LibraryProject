package main.by.library.command.impl;

import main.by.library.command.Command;
import main.by.library.command.CommandResult;
import main.by.library.entity.Book;
import main.by.library.entity.ShoppingCart;
import main.by.library.util.LoggerUtil;
import main.by.library.util.PageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AddToCartCommand implements Command, PageUtil, LoggerUtil {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ShoppingCart shoppingCart = (ShoppingCart) req.getSession().getAttribute(SHOPPING_CART_ATTRIBUTE);
        String[] books = req.getParameterValues(BOOK_ID_PARAMETER);
        for (String book : books) {
            Optional<Book> bookById = bookService.findBookById(Integer.parseInt(book));
            if (bookById.isPresent() && bookById.get().getQuantity() != 0) {
                shoppingCart.getShoppingList().add(bookById.get());
            }
        }
        req.getSession().setAttribute(SHOPPING_CART_ATTRIBUTE, shoppingCart);
        return new CommandResult(req.getHeader(REFERER_HEADER), CommandResult.REDIRECT);
    }
}
