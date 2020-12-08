package by.sanko.selectioncommittee.controller.command.impl;

import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.controller.command.Command;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.AuthenticationService;
import by.sanko.selectioncommittee.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangingPasswordCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String TOKEN_PARAM = "token";
    private static final String PASSWORD_PARAM = "password";
    private static final String ERROR_PARAM = "error";
    private static final String ID_PARAM = "id";
    private static final String ANSWER_PARAM = "answer";
    private static final String NEGATIVE_ANSWER = "Something went wrong";


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String token = req.getParameter(TOKEN_PARAM);
        AuthenticationService service = ServiceFactory.getInstance().getAuthenticationService();
        String responseFile = MappingJSP.ERROR_PAGE;
        try {
            int userID = service.takeUserIDFromToken(token);
            if(userID > 0){
                req.getSession().setAttribute(ID_PARAM,userID);
                responseFile = MappingJSP.SET_NEW_PASS;
            }
        } catch (ServiceException e) {
            responseFile = MappingJSP.ERROR_PAGE;
            req.setAttribute(ERROR_PARAM,e.getMessage());
        }
        try {
            req.getRequestDispatcher(responseFile).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't upload error page");
        }

    }
}
