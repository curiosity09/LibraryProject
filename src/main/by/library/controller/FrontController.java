package main.by.library.controller;

import main.by.library.entity.User;
import main.by.library.entity.UserData;
import main.by.library.filter.ProfileTools;
import main.by.library.services.UserService;
import main.by.library.services.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(urlPatterns = "/controller")
public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("request receive");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        switch (command) {
            case "loginUser":
                loginUser(req, resp);
                break;
            case "registerUser":
                registerUser(req, resp);
                break;
            case "logoutUser":
                logoutUser(req, resp);
                break;
            default:
                break;
        }

    }

    private void loginUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (Objects.nonNull(username) && !username.isEmpty() && Objects.nonNull(password) && !password.isBlank()) {
            UserService userService = new UserServiceImpl();
            List<User> userByUsername = userService.findUserByUsername(username);
//            String roleForResult = null;
            String passwordForResult = null;
            for (User user : userByUsername) {
                User userForResult = new User(user.getUsername(), user.getPassword(), user.getRole());
//                roleForResult = userForResult.getRole();
                passwordForResult = userForResult.getPassword();
            }
            if (Objects.equals(passwordForResult, password)) {
                HttpSession session = req.getSession();
                session.setAttribute(ProfileTools.SESSION_LOGGED_IN_ATTRIBUTE_NAME,
                        username);
                resp.sendRedirect("user.html");
            } else {
                resp.sendRedirect("error-login.html");
            }
        }
    }

    private void registerUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String firstName = req.getParameter("name");
        String lastName = req.getParameter("surname");
        String phoneNumber = req.getParameter("phone");
        String emailAddress = req.getParameter("email");
        if (Objects.nonNull(username) && !username.isEmpty() && Objects.nonNull(password) && !password.isBlank()) {
            UserService userService = new UserServiceImpl();
            User userForResult = new User(username, password, User.ROLE_USER, new UserData(firstName, lastName, phoneNumber, emailAddress));
            boolean b = userService.AddNewUser(userForResult);
            if (b) {
                HttpSession session = req.getSession();
                session.setAttribute(ProfileTools.SESSION_LOGGED_IN_ATTRIBUTE_NAME,
                        username);
                resp.sendRedirect("/user.html");
            } else {
                resp.sendRedirect("/register.html");
            }
        }
    }

    private void logoutUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("/login.html");
    }
}
