package main.by.library.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {

    CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
