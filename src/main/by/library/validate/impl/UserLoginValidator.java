package main.by.library.validate.impl;

import main.by.library.entity.User;
import main.by.library.util.PageUtil;
import main.by.library.validate.Validator;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

import static main.by.library.command.Command.userService;

public class UserLoginValidator implements Validator<User>, PageUtil {

    @Override
    public Optional<User> validate(HttpServletRequest req) {
        String username = req.getParameter(USERNAME_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);
        if (Objects.nonNull(username) && !username.isBlank() && Objects.nonNull(password) && !password.isBlank()) {
            Optional<User> optionalUser = userService.findUserByUsername(username);
            if (optionalUser.isPresent()) {
                byte[] decodePass = Base64.encodeBase64(password.getBytes(StandardCharsets.UTF_8));
                if (Objects.equals(optionalUser.get().getUsername(), username) && Objects.equals(new String(decodePass), optionalUser.get().getPassword())) {
                    return optionalUser;
                }
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
        return Optional.empty();
    }
}
