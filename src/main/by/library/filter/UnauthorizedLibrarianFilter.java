package main.by.library.filter;

import main.by.library.entity.User;
import main.by.library.util.JSPUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static main.by.library.util.PageUtil.*;

@WebFilter(urlPatterns = "/page/librarian/*")
public class UnauthorizedLibrarianFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        User user = (User) httpRequest.getSession().getAttribute(USER_ATTRIBUTE);
        if (Objects.nonNull(user)) {
            switch (user.getRole()) {
                case User.ROLE_ADMIN: {
                    httpResponse.sendRedirect(JSPUtil.getAdminJSPPath(ADMIN_PAGE));
                    break;
                }
                case User.ROLE_USER: {
                    httpResponse.sendRedirect(JSPUtil.getUserJSPPath(USER_PAGE));
                    break;
                }
                case User.ROLE_LIBRARIAN: {
                    filterChain.doFilter(servletRequest,servletResponse);
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
