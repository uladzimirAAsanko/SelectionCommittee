package by.sanko.selectioncommittee.controller.impl;

import by.sanko.selectioncommittee.controller.Command;
import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.entity.User;
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

public class AutoLoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String MESSAGE = "message";
    private static final String MESSAGE_TO_USER = "Invalid login or password";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String FATHERS_NAME = "fathersName";
    private static final String EMAIL = "email";
    private static final String COOK_ATTRIBUTE = "user_id";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        String responseFile = MappingJSP.ERROR_PAGE;
        User user = null;

        try {
            user = userService.getUserByID(NOT.getID(req.getCookies()));
        } catch (ServiceException e) {
            logger.error("can't upload error page");
        }
        if(user == null){
            responseFile = MappingJSP.AUTHORIZATION;
        }else{
            responseFile = MappingJSP.SUCCESS_LOGIN;
            req.getSession().setAttribute(FIRST_NAME,user.getFirstName());
            req.getSession().setAttribute(LAST_NAME,user.getLastName());
            req.getSession().setAttribute(FATHERS_NAME,user.getFathersName());
            req.getSession().setAttribute(LOGIN,user.getLogin());
            req.getSession().setAttribute(EMAIL,user.getEmail());
        }
        try {
            req.getRequestDispatcher(responseFile).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't upload error page");
        }
    }
}
