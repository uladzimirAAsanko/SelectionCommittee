package by.sanko.selectioncommittee.controller.impl;

import by.sanko.selectioncommittee.controller.Command;
import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.EnrolleeService;
import by.sanko.selectioncommittee.service.ServiceFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationEnrolleeCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String CERTIFICATE_PARAM = "certificate";
    private static final String ADDITIONAL_PARAM = "additional";
    private static final String MESSAGE = "message";
    private static final String MESSAGE_OF_ERROR = "Check the certificate";


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        EnrolleeService enrolleeService = ServiceFactory.getInstance().getEnrolleeService();
        boolean isRegister = false;
        String responseFile = MappingJSP.ERROR_PAGE;

        String certificate =  req.getParameter(CERTIFICATE_PARAM);
        String additional = req.getParameter(ADDITIONAL_PARAM);
        int id = NOT.getID(req.getCookies());
        try{
            isRegister = enrolleeService.registerUser(id,certificate,additional);
        } catch (ServiceException e) {
            req.getSession().setAttribute(MESSAGE,MESSAGE_OF_ERROR);
        }
        if(isRegister){
            responseFile = MappingJSP.SUCCESS_REGISTRATION;
        }else{
            responseFile = MappingJSP.FAIL_REGISTRATION;
        }
        try {
            req.getRequestDispatcher(responseFile).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't upload error page");
        }
    }
}
