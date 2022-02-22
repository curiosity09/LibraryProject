package main.by.library.command.impl;

import main.by.library.command.Command;
import main.by.library.command.CommandResult;
import main.by.library.entity.Book;
import main.by.library.entity.ShoppingCart;
import main.by.library.entity.User;
import main.by.library.util.JSPUtil;
import main.by.library.validate.Validator;
import main.by.library.validate.impl.UserLoginValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static main.by.library.util.PageUtil.*;

public class LoginCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Validator<User> userValidator = new UserLoginValidator();
        Optional<User> userOptional = userValidator.validate(req);
        if (userOptional.isPresent()) {
            req.getSession().setAttribute(USER_ATTRIBUTE, userOptional.get());
            List<Book> bookList = new ArrayList<>();
            ShoppingCart shoppingCart = new ShoppingCart(bookList);
            req.getSession().setAttribute(SHOPPING_CART_ATTRIBUTE, shoppingCart);
            return new CommandResult(req.getContextPath() + JSPUtil.getUserJSPPath(USER_PAGE), CommandResult.REDIRECT);
        } else {
            return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
        }
    }
}