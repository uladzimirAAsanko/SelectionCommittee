package by.sanko.selectioncommittee.controller.impl;

import by.sanko.selectioncommittee.controller.Command;
import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.controller.filler.LoginPageFiller;
import by.sanko.selectioncommittee.entity.AuthorizationData;
import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.entity.UsersRole;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.ServiceFactory;
import by.sanko.selectioncommittee.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationCommand implements Command {
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String MESSAGE = "message";
    private static final String MESSAGE_TO_USER = "Invalid login or password";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String FATHERS_NAME = "fathersName";
    private static final String EMAIL = "email";
    private static final String COOK_ATTRIBUTE = "user_id";

    private static final Logger logger = LogManager.getLogger();
    //TODO Make Mapping parameters class
    //FIXME Replace Authorization data in 2 strings
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp){
        UserService userService = ServiceFactory.getInstance().getUserService();
        String responseFile = MappingJSP.ERROR_PAGE;
        User user = null;
        AuthorizationData data = new AuthorizationData(req.getParameter(LOGIN), req.getParameter(PASSWORD));
        try {
            user = userService.authorizeUser(data);
            Cookie sessionId = new Cookie(COOK_ATTRIBUTE, user.getUserID() + "");
            resp.addCookie(sessionId);
            if(user == null){
                req.getSession().setAttribute(MESSAGE,MESSAGE_TO_USER);
            }else{
                LoginPageFiller pageFiller = LoginPageFiller.getInstance();
                responseFile = pageFiller.fillHomePage(req,user);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while authorization command", e);
        }
        try {
            req.getRequestDispatcher(responseFile).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't upload error page");
        }
    }
}
