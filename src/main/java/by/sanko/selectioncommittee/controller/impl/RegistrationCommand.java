package by.sanko.selectioncommittee.controller.impl;

import by.sanko.selectioncommittee.controller.Command;
import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.entity.AuthorizationData;
import by.sanko.selectioncommittee.entity.RegistrationData;
import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.entity.UsersRole;
import by.sanko.selectioncommittee.exception.RegistrationException;
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

public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String FATHERS_NAME = "fathersName";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String ROLE = "role";
    private static final String SPACE = " ";
    private static final String MESSAGE = "message";
    private static final String COOK_ATTRIBUTE = "user_id";

    //FIXME problem with new in dto

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        UserService userService = ServiceFactory.getInstance().getUserService();
        boolean isRegister = false;
        String responseFile = MappingJSP.ERROR_PAGE;
        try{
            String firstName =  req.getParameter(FIRST_NAME);
            String lastName = req.getParameter(LAST_NAME);
            String fathersName = req.getParameter(FATHERS_NAME);
            String login = req.getParameter(LOGIN);
            String password = req.getParameter(PASSWORD);
            String email = req.getParameter(EMAIL);
            int role = Integer.parseInt(req.getParameter(ROLE));
            RegistrationData data = new RegistrationData(firstName,lastName,fathersName,login,password,email,role);
            isRegister = userService.registerUser(data);
            if(isRegister){
                //FIXME DO NORMAL SESSIONS
                try {
                    User user = userService.authorizeUser(new AuthorizationData(login,password));
                    Cookie sessionId = new Cookie(COOK_ATTRIBUTE, user.getUserID() + "");
                    resp.addCookie(sessionId);
                } catch (ServiceException e) {
                    logger.log(Level.ERROR, "Error while authorization command", e);
                }
                if(role == UsersRole.ENROLLEE.ordinal()){
                    responseFile = MappingJSP.ENROLLE_REGISTRATION;
                }else{
                    responseFile = MappingJSP.SUCCESS_REGISTRATION;
                }
            }else{
                responseFile = MappingJSP.FAIL_REGISTRATION;
            }
        }catch (RegistrationException e){
            req.getSession().setAttribute(MESSAGE,e.getMessage());
        }catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while registration command", e);
        }
        try {
            req.getRequestDispatcher(responseFile).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't upload error page");
        }
    }
}
