package main.by.library.command.impl;

import main.by.library.command.Command;
import main.by.library.command.CommandResult;
import main.by.library.entity.Genre;
import main.by.library.services.GenreService;
import main.by.library.services.impl.GenreServiceImpl;
import main.by.library.util.JSPUtil;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static main.by.library.util.PageUtil.*;

public class AddNewGenreCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GenreService genreService = GenreServiceImpl.getInstance();
        String genreName = req.getParameter(GENRE_PARAMETER);
        if (Objects.nonNull(genreName) && !genreName.isBlank()) {
            if (!genreService.isGenreExist(genreName)) {
                if (genreService.addNewGenre(Genre.builder().name(genreName).build())) {
                    return new CommandResult(req.getContextPath() + JSPUtil.getLibrarianJSPPath(LIBRARIAN_PAGE), CommandResult.REDIRECT);
                }
                return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
            }
            return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
        }
        return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
    }
}
