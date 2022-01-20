package main.by.library.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class ProfileTools {
    public static final String SESSION_LOGGED_IN_ATTRIBUTE_NAME = "user";

    private ProfileTools(){}

    public static boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null &&
                session.getAttribute(SESSION_LOGGED_IN_ATTRIBUTE_NAME) != null;
    }
}

