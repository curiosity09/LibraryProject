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

@WebFilter(urlPatterns = {"/","/index.jsp","/register.jsp","/page/errorPage.jsp"})
public class AuthorizedUserFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        Object username = httpRequest.getSession().getAttribute(FrontController.SESSION_LOGGED_IN_ATTRIBUTE_NAME);
        if(!Objects.nonNull(username)){
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpResponse.sendRedirect("/page/user/user.jsp");
        }
    }
}
