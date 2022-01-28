package main.by.library.filter;

import main.by.library.controller.FrontController;
import main.by.library.entity.User;
import main.by.library.services.UserService;
import main.by.library.services.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebFilter(urlPatterns = "/page/user/user.jsp")
public class UnauthorizedUserFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String username = (String) httpRequest.getSession().getAttribute(FrontController.SESSION_LOGGED_IN_ATTRIBUTE_NAME);
        UserService userService = new UserServiceImpl();
        User checkedUser = userService.checkAuthentication(username);
        if (Objects.nonNull(checkedUser)) {
            switch (checkedUser.getRole()) {
                case User.ROLE_ADMIN: {
                    httpResponse.sendRedirect("/page/admin/admin.jsp");
                    break;
                }
                case User.ROLE_USER: {
                    filterChain.doFilter(servletRequest, servletResponse);
                    break;
                }
                default:
                    //TODO librarian
                    break;
            }
        } else {
            httpResponse.sendRedirect("/page/errorPage.jsp");
        }
    }
}

