package main.by.library.command.impl;

import main.by.library.command.Command;
import main.by.library.command.CommandResult;
import main.by.library.util.UtilCommand;
import main.by.library.entity.User;
import main.by.library.util.JSPUtil;
import main.by.library.util.LoggerUtil;
import main.by.library.util.PageUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAllUsersCommand implements Command, PageUtil, LoggerUtil {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        int countUser = userService.getCountUser();
        UtilCommand.getListOffset(req, countUser);
        String getLimit = req.getParameter(LIMIT_PARAMETER);
        String getOffset = req.getParameter(OFFSET_PARAMETER);
        List<User> users = userService.findAllUsers(Integer.parseInt(getLimit), Integer.parseInt(getOffset));
        req.setAttribute(ALL_USERS_ATTRIBUTE, users);
        return new CommandResult(req.getContextPath() + JSPUtil.getAdminJSPPath(ALL_USERS_PAGE), CommandResult.FORWARD);
    }
}
