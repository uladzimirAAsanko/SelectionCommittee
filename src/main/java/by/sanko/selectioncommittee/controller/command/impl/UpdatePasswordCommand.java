package by.sanko.selectioncommittee.controller.command.impl;

import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.controller.command.Command;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.ServiceFactory;
import by.sanko.selectioncommittee.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdatePasswordCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String LOGIN_PARAMETER = "userLogin";
    private static final String OLD_PASSWORD_PARAM = "oldPass";
    private static final String NEW_PASSWORD_PARAM = "newPass";
    private static final String MESSAGE = "message";
    private static final String ANSWER_PARAM = "answer";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        String responseFile = MappingJSP.ERROR_PAGE;
        String oldPass = req.getParameter(OLD_PASSWORD_PARAM);
        String newPass = req.getParameter(NEW_PASSWORD_PARAM);
        String login = (String) req.getAttribute(LOGIN_PARAMETER);
        try {
            String answer = userService.updateUserPassword(oldPass,newPass,login);
            responseFile = MappingJSP.UPDATE_USER_INFO;
            req.setAttribute(ANSWER_PARAM,answer);
        } catch (ServiceException e) {
            req.setAttribute(MESSAGE,e.getMessage());
        }
        try {
            req.getRequestDispatcher(responseFile).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't upload error page");
        }
    }
}
