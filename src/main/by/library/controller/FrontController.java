package main.by.library.controller;

import main.by.library.entity.*;
import main.by.library.services.*;
import main.by.library.services.impl.*;
import main.by.library.util.JSPUtil;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(urlPatterns = "/controller")
public class FrontController extends HttpServlet {

    public static final String USER_ATTRIBUTE = "user";
    public static final String COMMAND_KEY = "command";
    public static final String SHOPPING_CART_ATTRIBUTE = "shoppingCart";
    public static final int ZERO_OFFSET = 0;
    public static final int ONE_BOOK = 1;
    private final BookService bookService = new BookServiceImpl();
    private final UserService userService = new UserServiceImpl();
    private final AuthorService authorService = new AuthorServiceImpl();
    private final GenreService genreService = new GenreServiceImpl();
    private final SectionService sectionService = new SectionServiceImpl();
    private final OrderService orderService = new OrderServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter(COMMAND_KEY);
        processCommand(req, resp, command);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter(COMMAND_KEY);
        processCommand(req, resp, command);
    }

    private void processCommand(HttpServletRequest req, HttpServletResponse resp, String command) throws IOException, ServletException {
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
            case "showAllBooks":
                showAllBooks(req, resp);
                break;
            case "showAllUsers":
                showAllUsers(req, resp);
                break;
            case "showAllOrders":
                showAllOrders(req, resp);
                break;
            case "addNewBook":
                addNewBook(req, resp);
                break;
            case "addNewGenre":
                addNewGenre(req, resp);
                break;
            case "addNewAuthor":
                addNewAuthor(req, resp);
                break;
            case "addNewSection":
                addNewSection(req, resp);
                break;
            case "blockUser":
                blockUser(req, resp);
                break;
            case "addToCart":
                addToCart(req, resp);
                break;
            case "addOrder":
                addOrder(req, resp);
                break;
            default:
                break;
        }
    }

    private void addToCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ShoppingCart shoppingCart = (ShoppingCart) req.getSession().getAttribute(SHOPPING_CART_ATTRIBUTE);
        String[] books = req.getParameterValues("bookId");
        for (String book : books) {
            Book bookByName = bookService.findBookById(Integer.parseInt(book));
            if (bookByName.getQuantity() != 0) {
                shoppingCart.getShoppingList().add(bookByName);
                //TODO you can update the book storage here
            } else {
                System.out.println("There is no book");
                //TODO change
            }
        }
        req.getSession().setAttribute(SHOPPING_CART_ATTRIBUTE, shoppingCart);
        resp.sendRedirect(req.getContextPath() + "/controller?command=showAllBooks&offset=0");
    }


    private void addOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute(USER_ATTRIBUTE);
        ShoppingCart selectedBooks = (ShoppingCart) req.getSession().getAttribute(SHOPPING_CART_ATTRIBUTE);
        if (!selectedBooks.getShoppingList().isEmpty() && Objects.nonNull(user)) {
            List<Book> orderedBook = new ArrayList<>(selectedBooks.getShoppingList());
            selectedBooks.getShoppingList().removeAll(orderedBook);
            LocalDateTime now = LocalDateTime.now();
            int rentalPeriod = Integer.parseInt(req.getParameter("rentalPeriod"));
            LocalDateTime period;
            if (rentalPeriod == 1) {
                Duration between = Duration.between(LocalTime.now(), LocalTime.of(19, 0));
                long seconds = between.getSeconds();
                period = now.plusSeconds(seconds);
            } else {
                period = now.plusDays(rentalPeriod);
            }
            Order order = new Order(orderedBook, user, now, period);
            if (orderService.addOrder(order)) {
                req.getSession().setAttribute(SHOPPING_CART_ATTRIBUTE, selectedBooks);
                for (Book book : orderedBook) {
                    book.setQuantity(book.getQuantity() - ONE_BOOK);
                    bookService.updateBookData(book);
                }
            }
            req.getRequestDispatcher(req.getContextPath() + JSPUtil.getUserJSPPath("user")).forward(req, resp);
        }
    }

    private void blockUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        showAllUsers(req, resp);
        req.getRequestDispatcher(req.getContextPath() + JSPUtil.getAdminJSPPath("blockUser")).include(req, resp);
        String username = req.getParameter("username");
        if (Objects.nonNull(username)) {
            userService.blockUser(new User(username));
            req.getRequestDispatcher(req.getContextPath() + JSPUtil.getAdminJSPPath("admin")).forward(req, resp);
        }
    }

    private void addNewSection(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher(req.getContextPath() + JSPUtil.getLibrarianJSPPath("addSection")).include(req, resp);
        String sectionName = req.getParameter("sectionName");
        if (Objects.nonNull(sectionName)) {
            sectionService.addNewSection(new Section(sectionName));
            req.getRequestDispatcher(req.getContextPath() + JSPUtil.getLibrarianJSPPath("librarian")).forward(req, resp);
        }
    }

    private void addNewAuthor(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher(req.getContextPath() + JSPUtil.getLibrarianJSPPath("addAuthor")).include(req, resp);
        String authorName = req.getParameter("authorName");
        if (Objects.nonNull(authorName)) {
            authorService.addNewAuthor(new Author(authorName));
            req.getRequestDispatcher(req.getContextPath() + JSPUtil.getLibrarianJSPPath("librarian")).forward(req, resp);
        }
    }

    private void addNewGenre(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher(req.getContextPath() + JSPUtil.getLibrarianJSPPath("addGenre")).include(req, resp);
        String genreName = req.getParameter("genreName");
        if (Objects.nonNull(genreName)) {
            genreService.addNewGenre(new Genre(genreName));
            req.getRequestDispatcher(req.getContextPath() + JSPUtil.getLibrarianJSPPath("librarian")).forward(req, resp);
        }
    }

    private void showAllAuthor(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<Author> allAuthor = authorService.findAllAuthor(ZERO_OFFSET);
        req.setAttribute("authors", allAuthor);
    }

    private void showAllGenre(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<Genre> allGenre = genreService.findAllGenre(ZERO_OFFSET);
        req.setAttribute("genres", allGenre);
    }

    private void showAllSection(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<Section> allSection = sectionService.findAllSection(ZERO_OFFSET);
        req.setAttribute("sections", allSection);
    }

    private void addNewBook(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        showAllAuthor(req, resp);
        showAllGenre(req, resp);
        showAllSection(req, resp);
        req.getRequestDispatcher(req.getContextPath() + JSPUtil.getLibrarianJSPPath("addBook")).include(req, resp);
        String bookName = req.getParameter("bookName");
        String authorFullName = req.getParameter("authorFullName");
        String genre = req.getParameter("genre");
        String section = req.getParameter("section");
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        int year = Integer.parseInt(req.getParameter("year"));
        if (Objects.nonNull(bookName) && Objects.nonNull(authorFullName)
                && Objects.nonNull(genre) && Objects.nonNull(section)
                && quantity != 0 && year != 0) {
            Book bookForResult = new Book(bookName, new Author(authorFullName), new Genre(genre), new Section(section), quantity, year);
            bookService.addNewBook(bookForResult);
            req.getRequestDispatcher(req.getContextPath() + JSPUtil.getLibrarianJSPPath("librarian")).forward(req, resp);
        }
    }

    private void showAllOrders(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        getListOffset(req, orderService.getCountOrder());
        int offset = Integer.parseInt(req.getParameter("offset"));
        List<Order> orders = orderService.findAllOrder(offset);
        req.setAttribute("orders", orders);
        req.getRequestDispatcher(req.getContextPath() + JSPUtil.getLibrarianJSPPath("allOrders")).forward(req, resp);
    }

    private void showAllUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        getListOffset(req, userService.getCountUser());
        int offset = Integer.parseInt(req.getParameter("offset"));
        List<User> users = userService.findAllUsers(offset);
        req.setAttribute("users", users);
        req.getRequestDispatcher(req.getContextPath() + JSPUtil.getAdminJSPPath("allUsers")).forward(req, resp);
    }

    private void showAllBooks(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        getListOffset(req, bookService.getCountBook());
        int offset = Integer.parseInt(req.getParameter("offset"));
        List<Book> allBook = bookService.findAllBook(offset);
        req.setAttribute("books", allBook);
        req.getRequestDispatcher(req.getContextPath() + JSPUtil.getUserJSPPath("allBooks")).forward(req, resp);
    }

    private void getListOffset(HttpServletRequest req, int countBook) {
        int countPage;
        if (countBook % 10 != 0) {
            countPage = countBook / 10 + 1;
        } else {
            countPage = countBook / 10;
        }
        List<Integer> offsetList = new ArrayList<>(countPage);
        int num = 0;
        for (int i = 0; i < countPage; i++) {
            offsetList.add(num);
            num += 10;
        }
        req.setAttribute("offsetList", offsetList);
    }

    private void loginUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (Objects.nonNull(username) && !username.isEmpty() && Objects.nonNull(password) && !password.isEmpty()) {
            User userForResult = userService.checkAuthentication(username);
            byte[] decodePass = Base64.encodeBase64(password.getBytes(StandardCharsets.UTF_8));
            if (Objects.equals(userForResult.getUsername(), username) && Objects.equals(new String(decodePass), userForResult.getPassword())) {
                req.getSession().setAttribute(USER_ATTRIBUTE, userForResult);
                List<Book> bookList = new ArrayList<>();
                ShoppingCart shoppingCart = new ShoppingCart(bookList);
                req.getSession().setAttribute(SHOPPING_CART_ATTRIBUTE, shoppingCart);
                /*resp.sendRedirect(JSPUtil.getUserJSPPath("user"));*/
                req.getRequestDispatcher(req.getContextPath() + JSPUtil.getUserJSPPath("user")).forward(req, resp);
            } else {
                resp.sendRedirect(JSPUtil.ERROR_PAGE);
            }
        } else {
            resp.sendRedirect(JSPUtil.ERROR_PAGE);
        }
    }

    private void registerUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String role = req.getParameter("role");
        String firstName = req.getParameter("name");
        String lastName = req.getParameter("surname");
        String phoneNumber = req.getParameter("phone");
        String emailAddress = req.getParameter("email");
        if (Objects.nonNull(username) && !username.isBlank()
                && Objects.nonNull(password) && !password.isBlank()
                && Objects.nonNull(role) && !role.isBlank()
                && Objects.nonNull(firstName) && !firstName.isBlank()
                && Objects.nonNull(lastName) && !lastName.isBlank()
                && Objects.nonNull(phoneNumber) && !phoneNumber.isBlank()
                && Objects.nonNull(emailAddress) && !emailAddress.isBlank()) {
            byte[] encodePass = Base64.encodeBase64(password.getBytes(StandardCharsets.UTF_8));
            User userForResult = new User(username, new String(encodePass), role, new UserData(firstName, lastName, phoneNumber, emailAddress));
            if (!userService.isUserExist(userForResult.getUsername())) {
                if (userService.addNewUser(userForResult)) {
                    Object attribute = req.getSession().getAttribute(USER_ATTRIBUTE);
                    if (!Objects.nonNull(attribute)) {
                        req.getSession().setAttribute(USER_ATTRIBUTE, userForResult);
                    }
                    resp.sendRedirect(JSPUtil.getUserJSPPath("user"));
                } else {
                    resp.sendRedirect(JSPUtil.ERROR_PAGE);
                }
            } else {
                resp.sendRedirect(JSPUtil.ERROR_PAGE);
            }
        } else {
            resp.sendRedirect(JSPUtil.ERROR_PAGE);
        }
    }

    private void logoutUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("/");
    }
}
