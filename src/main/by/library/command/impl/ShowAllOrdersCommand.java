package main.by.library.command.impl;

import main.by.library.command.Command;
import main.by.library.command.CommandResult;
import main.by.library.services.OrderService;
import main.by.library.services.impl.OrderServiceImpl;
import main.by.library.util.UtilCommand;
import main.by.library.entity.Order;
import main.by.library.util.JSPUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

import static main.by.library.util.PageUtil.*;

public class ShowAllOrdersCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        OrderService orderService = OrderServiceImpl.getInstance();
        UtilCommand.getListOffset(req, orderService.getCountOrder());
        int offset = Integer.parseInt(req.getParameter(OFFSET_PARAMETER));
        List<Order> orders = orderService.findAllOrder(LIMIT_TEN, offset);
        req.setAttribute(ALL_ORDERS_ATTRIBUTE, orders);
        req.setAttribute(DATE_TIME_NOW_ATTRIBUTE, LocalDateTime.now());
        return new CommandResult(req.getContextPath() + JSPUtil.getLibrarianJSPPath(ALL_ORDERS_PAGE),CommandResult.FORWARD);
    }
}
