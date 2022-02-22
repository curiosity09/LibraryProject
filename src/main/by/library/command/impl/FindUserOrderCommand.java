package main.by.library.command.impl;

import main.by.library.command.Command;
import main.by.library.command.CommandResult;
import main.by.library.services.OrderService;
import main.by.library.services.UserService;
import main.by.library.services.impl.OrderServiceImpl;
import main.by.library.services.impl.UserServiceImpl;
import main.by.library.util.UtilCommand;
import main.by.library.entity.Order;
import main.by.library.entity.User;
import main.by.library.util.JSPUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static main.by.library.util.LoggerUtil.*;
import static main.by.library.util.PageUtil.*;

public class FindUserOrderCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(FindUserOrderCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderService orderService = OrderServiceImpl.getInstance();
        UserService userService = UserServiceImpl.getInstance();
        LOGGER.log(Level.INFO, ENTER_METHOD_MESSAGE);
        String username = req.getParameter(USERNAME_PARAMETER);
        LOGGER.log(Level.INFO, PARAM_REQUEST_MESSAGE);
        if (!Objects.nonNull(username)) {
            List<User> users = userService.findAllUsers(userService.getCountUser(), OFFSET_ZERO);
            req.setAttribute(ALL_USERS_ATTRIBUTE, users);
            req.getRequestDispatcher(req.getContextPath() + JSPUtil.getLibrarianJSPPath(FIND_USER_ORDER_PAGE)).forward(req, resp);
        }
        if (Objects.nonNull(username)&&!username.isBlank()) {
            LOGGER.log(Level.INFO, PARAM_RECEIVE_MESSAGE);
            List<Order> ordersByUsername = orderService.findOrderByUsername(username, orderService.getCountOrder(), OFFSET_ZERO);
            UtilCommand.getListOffset(req, ordersByUsername.size());
            int offset = Integer.parseInt(req.getParameter(OFFSET_PARAMETER));
            List<Order> usersOrder = orderService.findOrderByUsername(username, LIMIT_TEN, offset);
            req.setAttribute(USERS_ORDER_ATTRIBUTE, usersOrder);
            LOGGER.log(Level.INFO, SET_ATTRIBUTE_MESSAGE);
            return new CommandResult(req.getContextPath() + JSPUtil.getLibrarianJSPPath(USER_ORDER_PAGE), CommandResult.FORWARD);
        } else {
            LOGGER.log(Level.INFO, ERROR_BLOCK_MESSAGE);
            return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.FORWARD);
        }
    }
}

