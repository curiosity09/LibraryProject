package main.by.library.validate;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface Validator<T> {

    Optional<T> validate(HttpServletRequest req);
}
