package main.by.library.command;

import main.by.library.util.CommandFactory;
import main.by.library.util.LoggerUtil;
import main.by.library.util.PageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet(urlPatterns = "/controller")
public class FrontController extends HttpServlet implements LoggerUtil, PageUtil {

    private static final Logger LOGGER = LogManager.getLogger(FrontController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            LOGGER.info(DO_GET_METHOD_PROCESSING_MESSAGE);
            processCommand(req, resp);
        } catch (ServletException | IOException e) {
            LOGGER.error(DO_GET_METHOD_EXCEPTION_MESSAGE, e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            LOGGER.info(DO_POST_METHOD_PROCESSING_MESSAGE);
            processCommand(req, resp);
        } catch (ServletException | IOException e) {
            LOGGER.error(DO_POST_METHOD_EXCEPTION_MESSAGE, e);
        }

    }

    private void processCommand(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = CommandFactory.defineCommand(req.getParameter(COMMAND_PARAMETER));
        if (Objects.nonNull(command)) {
            CommandResult result = command.execute(req, resp);
            String navigationType = result.getNavigationType();
            String pageName = result.getPageName();
            switch (navigationType) {
                case CommandResult.FORWARD:
                    req.getServletContext().getRequestDispatcher(pageName).forward(req, resp);
                    break;
                case CommandResult.INCLUDE:
                    req.getServletContext().getRequestDispatcher(pageName).include(req, resp);
                    break;
                case CommandResult.REDIRECT:
                    resp.sendRedirect(pageName);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + navigationType);
            }
        }
    }
}
