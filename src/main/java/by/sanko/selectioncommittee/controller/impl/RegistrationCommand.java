package by.sanko.selectioncommittee.controller.impl;

import by.sanko.selectioncommittee.controller.Command;
import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.ServiceFactory;
import by.sanko.selectioncommittee.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationCommand implements Command {
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String FATHERS_NAME = "fathersName";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String ROLE = "role";
    private static final String SPACE = " ";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        boolean isRegister = false;
        try{
            StringBuilder data = new StringBuilder();
            data.append(req.getParameter(FIRST_NAME)).append(SPACE);
            data.append(req.getParameter(LAST_NAME)).append(SPACE);
            data.append(req.getParameter(FATHERS_NAME)).append(SPACE);
            data.append(req.getParameter(LOGIN)).append(SPACE);
            data.append(req.getParameter(PASSWORD)).append(SPACE);
            data.append(req.getParameter(EMAIL)).append(SPACE);
            data.append(req.getParameter(ROLE));
            isRegister = userService.registerUser(data.toString());
        }catch (ServiceException e) {
            resp.sendRedirect(MappingJSP.NOT_FOUND_COMMAND);
        }
        if(isRegister){
            resp.sendRedirect(MappingJSP.SUCCESS_REGISTRATION);
        }else{
            resp.sendRedirect(MappingJSP.FAIL_REGISTRATION);
        }
    }
}
