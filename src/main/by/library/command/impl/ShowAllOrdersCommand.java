package main.by.library.command.impl;

import main.by.library.command.Command;
import main.by.library.command.CommandResult;
import main.by.library.util.UtilCommand;
import main.by.library.entity.Order;
import main.by.library.util.JSPUtil;
import main.by.library.util.LoggerUtil;
import main.by.library.util.PageUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

public class ShowAllOrdersCommand implements Command, PageUtil, LoggerUtil {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        UtilCommand.getListOffset(req, orderService.getCountOrder());
        int offset = Integer.parseInt(req.getParameter(OFFSET_PARAMETER));
        List<Order> orders = orderService.findAllOrder(LIMIT_TEN, offset);
        req.setAttribute(ALL_ORDERS_ATTRIBUTE, orders);
        req.setAttribute(DATE_TIME_NOW_ATTRIBUTE, LocalDateTime.now());
        return new CommandResult(req.getContextPath() + JSPUtil.getLibrarianJSPPath(ALL_ORDERS_PAGE),CommandResult.FORWARD);
    }
}
