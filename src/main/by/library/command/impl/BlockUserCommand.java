package main.by.library.command.impl;

import main.by.library.command.Command;
import main.by.library.command.CommandResult;
import main.by.library.entity.User;
import main.by.library.services.UserService;
import main.by.library.services.impl.UserServiceImpl;
import main.by.library.util.JSPUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static main.by.library.util.PageUtil.*;

public class BlockUserCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = UserServiceImpl.getInstance();
        int countUser = userService.getCountUser();
        List<User> allDebtors = userService.findAllDebtors(countUser, OFFSET_ZERO);
        req.setAttribute(DEBTORS_ATTRIBUTE, allDebtors);
        req.getRequestDispatcher(req.getContextPath() + JSPUtil.getAdminJSPPath(BLOCK_USER_PAGE)).forward(req, resp);
        String username = req.getParameter(USERNAME_PARAMETER);
        if (Objects.nonNull(username) && !username.isBlank()) {
            if (userService.blockUser(User.builder().username(username).build())) {
                return new CommandResult(req.getContextPath() + JSPUtil.getAdminJSPPath(ADMIN_PAGE), CommandResult.REDIRECT);
            } else {
                return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
            }
        } else {
            return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
        }
    }
}
