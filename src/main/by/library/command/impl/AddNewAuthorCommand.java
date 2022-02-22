package main.by.library.command.impl;

import main.by.library.command.Command;
import main.by.library.command.CommandResult;
import main.by.library.services.AuthorService;
import main.by.library.services.impl.AuthorServiceImpl;
import main.by.library.entity.Author;
import main.by.library.util.JSPUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static main.by.library.util.LoggerUtil.*;
import static main.by.library.util.PageUtil.*;

public class AddNewAuthorCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(AddNewAuthorCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthorService authorService = AuthorServiceImpl.getInstance();
        LOGGER.log(Level.INFO, ENTER_METHOD_MESSAGE);
        String authorName = req.getParameter(AUTHOR_PARAMETER);
        LOGGER.log(Level.INFO, PARAM_REQUEST_MESSAGE);
        if (Objects.nonNull(authorName) && !authorName.isBlank()) {
            LOGGER.log(Level.INFO, PARAM_RECEIVE_MESSAGE);
            if (!authorService.isAuthorExist(authorName)) {
                if (authorService.addNewAuthor(Author.builder().name(authorName).build())) {
                    LOGGER.log(Level.INFO, "Author added");
                    return new CommandResult(req.getContextPath() + JSPUtil.getLibrarianJSPPath(LIBRARIAN_PAGE), CommandResult.REDIRECT);
                } else {
                    return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
                }
            } else {
                LOGGER.log(Level.INFO, ERROR_BLOCK_MESSAGE);
                return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
            }
        }
        LOGGER.log(Level.INFO, ERROR_BLOCK_MESSAGE);
        return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
    }
}
