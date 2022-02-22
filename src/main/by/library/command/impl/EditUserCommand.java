package main.by.library.command.impl;

import main.by.library.command.Command;
import main.by.library.command.CommandResult;
import main.by.library.entity.User;
import main.by.library.services.UserService;
import main.by.library.services.impl.UserServiceImpl;
import main.by.library.util.JSPUtil;
import main.by.library.validate.Validator;
import main.by.library.validate.impl.UserRegisterValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static main.by.library.util.PageUtil.*;

public class EditUserCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(EditUserCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = UserServiceImpl.getInstance();
        Validator<User> userValidator = new UserRegisterValidator();
        Optional<User> optionalUser = userValidator.validate(req);
        if (optionalUser.isPresent()) {
            LOGGER.info("User is present");
            User user = (User) req.getSession().getAttribute(USER_ATTRIBUTE);
            optionalUser.get().setId(user.getId());
            if (userService.updateUserData(optionalUser.get())) {
                return new CommandResult(req.getContextPath() + JSPUtil.getUserJSPPath(USER_PAGE), CommandResult.REDIRECT);
            } else {
                return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
            }
        } else {
            return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
        }
    }
}
