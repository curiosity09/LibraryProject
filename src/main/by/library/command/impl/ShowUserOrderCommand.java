package main.by.library.command.impl;

import main.by.library.command.Command;
import main.by.library.command.CommandResult;
import main.by.library.util.UtilCommand;
import main.by.library.entity.Order;
import main.by.library.entity.User;
import main.by.library.util.JSPUtil;
import main.by.library.util.LoggerUtil;
import main.by.library.util.PageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowUserOrderCommand implements Command, PageUtil, LoggerUtil {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER_ATTRIBUTE);
        List<Order> ordersByUsername = orderService.findOrderByUsername(user.getUsername(), orderService.getCountOrder(), OFFSET_ZERO);
        UtilCommand.getListOffset(req, ordersByUsername.size());
        int offset = Integer.parseInt(req.getParameter(OFFSET_PARAMETER));
        List<Order> usersOrder = orderService.findOrderByUsername(user.getUsername(), LIMIT_TEN, offset);
        req.setAttribute(USERS_ORDER_ATTRIBUTE, usersOrder);
        return new CommandResult(req.getContextPath() + JSPUtil.getUserJSPPath(USER_ORDER_PAGE),CommandResult.FORWARD);
    }
}
