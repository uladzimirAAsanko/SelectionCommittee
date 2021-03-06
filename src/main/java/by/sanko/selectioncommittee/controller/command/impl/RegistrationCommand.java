package by.sanko.selectioncommittee.controller.command.impl;

import by.sanko.selectioncommittee.controller.command.Command;
import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.entity.AuthorizationData;
import by.sanko.selectioncommittee.entity.RegistrationData;
import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.entity.UsersRole;
import by.sanko.selectioncommittee.exception.ServiceImpl.NotValidDataException;
import by.sanko.selectioncommittee.exception.ServiceImpl.RegistrationException;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.ServiceFactory;
import by.sanko.selectioncommittee.service.UserService;
import by.sanko.selectioncommittee.util.security.JWTUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.ServletException;
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
    private static final String ANSWER_PARAM = "answer";
    private static final String SPACE = " ";
    private static final String MESSAGE = "message";
    private static final String COOK_ATTRIBUTE = "user_id";


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String enc = req.getCharacterEncoding();
        req.setCharacterEncoding("UTF-8");
        UserService userService = ServiceFactory.getInstance().getUserService();
        boolean isRegister = false;
        String responseFile = MappingJSP.ERROR_PAGE;
        String firstName =  req.getParameter(FIRST_NAME);
        String lastName = req.getParameter(LAST_NAME);
        String fathersName = req.getParameter(FATHERS_NAME);
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        String email = req.getParameter(EMAIL);
        int role = Integer.parseInt(req.getParameter(ROLE));
        try{
            RegistrationData data = new RegistrationData(firstName, lastName, fathersName, login, password, email, UsersRole.GUEST.ordinal());
            isRegister = userService.registerUser(data);
            if(isRegister){
                User user = userService.authorizeUser(new AuthorizationData(login,password));
                JWTUtil.getInstance().writeCook(req,resp,user);
                if(role == UsersRole.ENROLLEE.ordinal()){
                    responseFile = MappingJSP.ENROLLE_REGISTRATION;
                }else{
                    if(role == UsersRole.ADMINISTRATOR.ordinal()){
                        responseFile = MappingJSP.ADMIN_REGISTRATION;
                    }else{
                        responseFile = MappingJSP.SUCCESS_REGISTRATION;
                    }
                }
            }else{
                responseFile = MappingJSP.FAIL_REGISTRATION;
            }
        }catch (NotValidDataException e){
            responseFile = MappingJSP.REGISTRATION;
            req.setAttribute(ANSWER_PARAM,e.getMessage());
            req.setAttribute(FIRST_NAME,firstName);
            req.setAttribute(LAST_NAME,lastName);
            req.setAttribute(FATHERS_NAME,fathersName);
            req.setAttribute(EMAIL,email);
            req.setAttribute(LOGIN,login);
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
