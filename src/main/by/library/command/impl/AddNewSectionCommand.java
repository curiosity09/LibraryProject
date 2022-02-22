package main.by.library.command.impl;

import main.by.library.command.Command;
import main.by.library.command.CommandResult;
import main.by.library.entity.Section;
import main.by.library.services.SectionService;
import main.by.library.services.impl.SectionServiceImpl;
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

public class AddNewSectionCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(AddNewSectionCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SectionService sectionService = SectionServiceImpl.getInstance();
        LOGGER.log(Level.INFO, ENTER_METHOD_MESSAGE);
        String sectionName = req.getParameter(SECTION_PARAMETER);
        if (Objects.nonNull(sectionName) && !sectionName.isBlank()) {
            if (!sectionService.isSectionExist(sectionName)) {
                if (sectionService.addNewSection(Section.builder().name(sectionName).build())) {
                    return new CommandResult(req.getContextPath() + JSPUtil.getLibrarianJSPPath(LIBRARIAN_PAGE), CommandResult.REDIRECT);
                }
                return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
            }
            return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
        }
        return new CommandResult(req.getContextPath() + JSPUtil.ERROR_PAGE, CommandResult.REDIRECT);
    }
}