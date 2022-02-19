package main.by.library.validate.impl;

import main.by.library.entity.User;
import main.by.library.entity.UserData;
import main.by.library.util.PageUtil;
import main.by.library.validate.Validator;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class UserRegisterValidator implements Validator<User>, PageUtil {

    @Override
    public Optional<User> validate(HttpServletRequest req) {
        String username = req.getParameter(USERNAME_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);
        String role = req.getParameter(ROLE_PARAMETER);
        String firstName = req.getParameter(NAME_PARAMETER);
        String lastName = req.getParameter(SURNAME_PARAMETER);
        String phoneNumber = req.getParameter(PHONE_PARAMETER);
        String emailAddress = req.getParameter(EMAIL_PARAMETER);
        if (isCorrectUsername(username) && isCorrectPassword(password)
                && Objects.nonNull(role) && !role.isBlank()) {
            byte[] encodePass = Base64.encodeBase64(password.getBytes(StandardCharsets.UTF_8));
            User userForResult = User.builder().username(username).password(new String(encodePass)).role(role).build();
            UserData userData = UserData.builder().build();
            if (isCorrectName(firstName)) {
                userData.setName(firstName);
            }
            if (isCorrectName(lastName)) {
                userData.setSurname(lastName);
            }
            if (isCorrectPhoneNumber(phoneNumber)) {
                userData.setPhoneNumber(phoneNumber);
            }
            if (isCorrectEmailAddress(emailAddress)) {
                userData.setEmailAddress(emailAddress);
            }
            userForResult.setUserData(userData);
            return Optional.of(userForResult);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Returns true if the string fulfills the condition
     * @param username String
     * @return true or false
     */
    public static boolean isCorrectUsername(String username) {
        return Objects.nonNull(username) && Pattern.matches("^[A-za-z][A-Za-z0-9\\._]{3,10}$", username);
    }

    /**
     * Returns true if the string fulfills the condition
     * @param password String
     * @return true or false
     */
    public static boolean isCorrectPassword(String password) {
        return Objects.nonNull(password) && Pattern.matches("^[A-za-z0-9\\._]{4,16}$", password);
    }

    /**
     * Returns true if the string fulfills the condition
     * @param name String
     * @return true or false
     */
    public static boolean isCorrectName(String name) {
        return Objects.nonNull(name) && Pattern.matches("^([A-Z]{1}[a-z]+|[А-Я]{1}[а-я]+)$", name);
    }

    /**
     * Returns true if the string fulfills the condition
     * @param emailAddress String
     * @return true or false
     */
    public static boolean isCorrectEmailAddress(String emailAddress) {
        return Objects.nonNull(emailAddress)
                && Pattern.matches("[A-za-z0-9\\._]+@[a-z]+\\.[a-z]{2,4}", emailAddress);
    }

    /**
     * Returns true if the string fulfills the condition
     * @param phoneNumber String
     * @return true or false
     */
    public static boolean isCorrectPhoneNumber(String phoneNumber) {
        return Objects.nonNull(phoneNumber)
                && Pattern.matches("^(\\+?375|80)(29|25|44|33)(\\d{3})(\\d{2})(\\d{2})$", phoneNumber);
    }
}
