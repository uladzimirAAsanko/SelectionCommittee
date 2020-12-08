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

public class UpdateProfileCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String FATHERS_NAME = "fathersName";
    private static final String ID_PARAMETER = "userID";
    private static final String ANSWER_PARAM = "answer";
    private static final String MESSAGE = "message";
    private static final String SUCCESSFUL_ANSWER = "info successfully updated";
    private static final String UNSUCCESSFUL_ANSWER = "Something went wrong please try again";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        String responseFile = MappingJSP.ERROR_PAGE;
        UserService userService = ServiceFactory.getInstance().getUserService();
        String firstName = req.getParameter(FIRST_NAME);
        String lastName = req.getParameter(LAST_NAME);
        String fathersName = req.getParameter(FATHERS_NAME);
        int userID = (Integer) req.getAttribute(ID_PARAMETER);
        try {
            responseFile = MappingJSP.UPDATE_USER_INFO;
            String answer = "";
            if(userService.updateUserInfo(firstName,lastName,fathersName,userID)){
                answer = SUCCESSFUL_ANSWER;
            }else{
                answer = UNSUCCESSFUL_ANSWER;
            }
            req.getSession().setAttribute(ANSWER_PARAM,answer);
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
