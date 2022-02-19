package main.by.library.command.impl;

import main.by.library.command.Command;
import main.by.library.command.CommandResult;
import main.by.library.util.LoggerUtil;
import main.by.library.util.PageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

public class ChangeLanguageCommand implements Command, LoggerUtil, PageUtil {

    public static final String COUNTRY_RUSSIA = "RU";
    public static final String LANGUAGE_RUSSIAN = "ru";

    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lang = req.getParameter(LANGUAGE_PARAMETER);
        if (LANGUAGE_RUSSIAN.equalsIgnoreCase(lang)) {
            req.getSession().setAttribute(LANGUAGE_ATTRIBUTE, new Locale(LANGUAGE_RUSSIAN, COUNTRY_RUSSIA));
        } else {
            req.getSession().setAttribute(LANGUAGE_ATTRIBUTE, new Locale(Locale.US.getLanguage(), Locale.US.getCountry()));
        }
        String from = req.getHeader(REFERER_HEADER);
        return new CommandResult(from, CommandResult.REDIRECT);
    }
}
