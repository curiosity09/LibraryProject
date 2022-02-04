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

@WebFilter(urlPatterns = "/page/user/*")
public class UnauthorizedUserFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        User user = (User) httpRequest.getSession().getAttribute(FrontController.USER_ATTRIBUTE);
        if (Objects.nonNull(user)) {
            switch (user.getRole()) {
                case User.ROLE_ADMIN: {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + JSPUtil.getAdminJSPPath("admin"));
                    break;
                }
                case User.ROLE_USER: {
                    filterChain.doFilter(servletRequest, servletResponse);
                    break;
                }
                case User.ROLE_LIBRARIAN:{
                    httpResponse.sendRedirect(httpRequest.getContextPath() + JSPUtil.getLibrarianJSPPath("librarian"));
                    break;
                }
                default:
                    break;
            }
        } else {
            httpResponse.sendRedirect(JSPUtil.ERROR_PAGE);
        }
    }
}

