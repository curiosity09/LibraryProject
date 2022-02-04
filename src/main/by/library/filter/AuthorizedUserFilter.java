package main.by.library.filter;

import main.by.library.controller.FrontController;
import main.by.library.entity.User;
import main.by.library.util.JSPUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebFilter(urlPatterns = {"/","/index.jsp","/register.jsp"})
public class AuthorizedUserFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        User user = (User) httpRequest.getSession().getAttribute(FrontController.USER_ATTRIBUTE);
        if(!Objects.nonNull(user)){
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpResponse.sendRedirect(JSPUtil.getUserJSPPath("user"));
        }
    }
}
