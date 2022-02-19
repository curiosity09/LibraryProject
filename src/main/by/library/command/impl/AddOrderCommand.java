package main.by.library.command.impl;

import main.by.library.command.Command;
import main.by.library.command.CommandResult;
import main.by.library.entity.Book;
import main.by.library.entity.Order;
import main.by.library.entity.ShoppingCart;
import main.by.library.entity.User;
import main.by.library.util.JSPUtil;
import main.by.library.util.LoggerUtil;
import main.by.library.util.PageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddOrderCommand implements Command, LoggerUtil, PageUtil {

    private static final Logger LOGGER = LogManager.getLogger(AddOrderCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info(ENTER_METHOD_MESSAGE);
        User user = (User) req.getSession().getAttribute(USER_ATTRIBUTE);
        ShoppingCart selectedBooks = (ShoppingCart) req.getSession().getAttribute(SHOPPING_CART_ATTRIBUTE);
        if (!selectedBooks.getShoppingList().isEmpty() && Objects.nonNull(user)) {
            List<Book> orderedBook = new ArrayList<>(selectedBooks.getShoppingList());
            selectedBooks.getShoppingList().removeAll(orderedBook);
            Order order = new Order(orderedBook, user, LocalDateTime.now(), setRentalPeriod(req));
            if (orderService.addOrder(order)) {
                LOGGER.info("Order added");
                req.getSession().setAttribute(SHOPPING_CART_ATTRIBUTE, selectedBooks);
                for (Book book : orderedBook) {
                    book.setQuantity(book.getQuantity() - ONE_BOOK);
                    bookService.updateBookData(book);
                    LOGGER.info("Update book");
                }
                return new CommandResult(req.getContextPath() + JSPUtil.getUserJSPPath(USER_PAGE), CommandResult.REDIRECT);
            } else {
                return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
            }
        } else {
            return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
        }
    }

    private LocalDateTime setRentalPeriod(HttpServletRequest req) {
        LocalDateTime now = LocalDateTime.now();
        int rentalPeriod = Integer.parseInt(req.getParameter(RENTAL_PERIOD_PARAMETER));
        if (rentalPeriod == 1) {
            long seconds = LocalDateTime.now().compareTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(19, 0))) > 0 ?
                    ChronoUnit.SECONDS.between(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(19, 0)), now)
                    : ChronoUnit.SECONDS.between(LocalTime.now(), LocalTime.of(19, 0));
            return now.plusSeconds(Math.abs(seconds));
        } else {
            return now.plusDays(rentalPeriod);
        }
    }
}
