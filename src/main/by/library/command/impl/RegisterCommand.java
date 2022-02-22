package main.by.library.command.impl;

import main.by.library.command.Command;
import main.by.library.command.CommandResult;
import main.by.library.entity.User;
import main.by.library.services.UserService;
import main.by.library.services.impl.UserServiceImpl;
import main.by.library.util.JSPUtil;
import main.by.library.validate.Validator;
import main.by.library.validate.impl.UserRegisterValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

import static main.by.library.util.PageUtil.*;

public class RegisterCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        UserService userService = UserServiceImpl.getInstance();
        Validator<User> userValidator = new UserRegisterValidator();
        Optional<User> userOptional = userValidator.validate(req);
        if (userOptional.isPresent()) {
            if (!userService.isUserExist(userOptional.get().getUsername())) {
                if (userService.addNewUser(userOptional.get())) {
                    Object attribute = req.getSession().getAttribute(USER_ATTRIBUTE);
                    if (!Objects.nonNull(attribute)) {
                        req.getSession().setAttribute(USER_ATTRIBUTE, userOptional.get());
                    }
                    return new CommandResult(req.getContextPath() + JSPUtil.getUserJSPPath(USER_PAGE), CommandResult.REDIRECT);
                } else {
                    return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
                }
            } else {
                return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
            }
        } else {
            return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
        }
    }
}
