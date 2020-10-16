package by.sanko.selectioncommittee.controller.impl;

import by.sanko.selectioncommittee.controller.Command;
import by.sanko.selectioncommittee.controller.MappingJSP;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WrongAction implements Command {
    public static final String MESSAGE = "message";
    private static final String COMMAND_DO_NOT_UNDERSTAND = "Command do not understand";

    private static final Logger logger = LogManager.getLogger();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute(MESSAGE, COMMAND_DO_NOT_UNDERSTAND);
        try {
            req.getRequestDispatcher(MappingJSP.ERROR_PAGE).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't upload error page");
        }
    }
}
