package main.by.library.command.impl;

import main.by.library.command.Command;
import main.by.library.command.CommandResult;
import main.by.library.entity.Book;
import main.by.library.entity.Order;
import main.by.library.util.JSPUtil;
import main.by.library.util.LoggerUtil;
import main.by.library.util.PageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class FinishOrderCommand implements Command, PageUtil, LoggerUtil {

    private static final Logger LOGGER = LogManager.getLogger(FinishOrderCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info(ENTER_METHOD_MESSAGE);
        String[] orderIds = req.getParameterValues(ORDER_ID_PARAMETER);
        String username = req.getParameter(USERNAME_PARAMETER);
        String offset = req.getParameter(OFFSET_PARAMETER);
        LOGGER.info(PARAM_REQUEST_MESSAGE);
        if (Objects.nonNull(orderIds) && Objects.nonNull(username) && Objects.nonNull(offset)) {
            LOGGER.info(PARAM_RECEIVE_MESSAGE);
            for (String id : orderIds) {
                Optional<Order> orderById = orderService.findOrderById(Integer.parseInt(id));
                if (orderById.isPresent()) {
                    for (Book book : orderById.get().getBook()) {
                        book.setQuantity(book.getQuantity() + ONE_BOOK);
                        bookService.updateBookData(book);
                    }
                    orderService.deleteOrder(orderById.get());
                }
            }
            return new CommandResult(req.getContextPath()
                    + JSPUtil.getControllerCommandPath(FIND_USER_ORDER_PAGE
                    + AMPERSAND + USERNAME_PARAMETER + EQUAL_SIGH + username + AMPERSAND + OFFSET_PARAMETER + EQUAL_SIGH + Integer.parseInt(offset)), CommandResult.REDIRECT);
        } else {
            return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
        }
    }
}
