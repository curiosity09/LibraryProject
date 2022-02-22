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
import java.util.Optional;

import static main.by.library.util.PageUtil.*;

public class ShowUserInfoCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = UserServiceImpl.getInstance();
        User user = (User) req.getSession().getAttribute(USER_ATTRIBUTE);
        Optional<User> userById = userService.findUserById(user.getId());
        userById.ifPresent(value -> req.getSession().setAttribute(USER_ATTRIBUTE, value));
        return new CommandResult(req.getContextPath() + JSPUtil.getUserJSPPath(USER_INFO_PAGE), CommandResult.FORWARD);
    }
}
