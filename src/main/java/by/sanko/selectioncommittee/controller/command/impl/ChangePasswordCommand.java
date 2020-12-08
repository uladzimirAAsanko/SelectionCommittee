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

public class ChangePasswordCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String DATA_PARAM = "data";
    private static final String ERROR_PARAM = "error";
    private static final String ANSWER_PARAM = "answer";
    private static final String POSITIVE_ANSWER = "Message was successfully sent to your email ";
    private static final String NEGATIVE_ANSWER = "Message was not sent to your email, please check input data";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        String responseFile = MappingJSP.ERROR_PAGE;
        try {
            String data = req.getParameter(DATA_PARAM);
            String answer = "";
            if(userService.changeUserPassword(data)){
                answer = POSITIVE_ANSWER;
            }else {
                answer = NEGATIVE_ANSWER;
            }
            req.setAttribute(ANSWER_PARAM,answer);
            responseFile = MappingJSP.CHANGE_PASSWORD;
        }catch (ServiceException e) {
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
