package main.by.library.filter;


import main.by.library.entity.User;
import main.by.library.services.UserService;
import main.by.library.services.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebFilter(urlPatterns = "/admin.html")
public class UnauthorizedAdminFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String username = (String) httpRequest.getSession().getAttribute(ProfileTools.SESSION_LOGGED_IN_ATTRIBUTE_NAME);
        UserService userService = new UserServiceImpl();
        List<User> userByUsername = userService.findUserByUsername(username);
        String role = null;
        for (User user : userByUsername) {
            User userForResult = new User(user.getUsername(), user.getPassword(), user.getRole());
            role = userForResult.getRole();
        }
        if (ProfileTools.isLoggedIn(httpRequest) && Objects.equals(role, User.ROLE_ADMIN)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpResponse.sendRedirect("/user.html");
        }
    }
}
