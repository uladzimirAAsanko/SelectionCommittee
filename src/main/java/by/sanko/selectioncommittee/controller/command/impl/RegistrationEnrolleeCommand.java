package by.sanko.selectioncommittee.controller.command.impl;

import by.sanko.selectioncommittee.controller.command.Command;
import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.exception.ServiceImpl.NotValidDataException;
import by.sanko.selectioncommittee.service.EnrolleeService;
import by.sanko.selectioncommittee.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

public class RegistrationEnrolleeCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String CERTIFICATE_PARAM = "certificate";
    private static final String ADDITIONAL_PARAM = "additional";
    private static final String MESSAGE = "message";
    private static final String ANSWER_PARAM = "answer";
    private static final String MESSAGE_OF_ERROR = "Check the certificate";
    private static final String ID_PARAMETER = "userID";


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        EnrolleeService enrolleeService = ServiceFactory.getInstance().getEnrolleeService();
        boolean isRegister = false;
        String responseFile = MappingJSP.ERROR_PAGE;
        String certificate =  req.getParameter(CERTIFICATE_PARAM);
        String additional = req.getParameter(ADDITIONAL_PARAM);
        int id = (Integer) req.getAttribute(ID_PARAMETER);
        try{
            isRegister = enrolleeService.registerUser(id,certificate,additional);
            if(isRegister){
                responseFile = MappingJSP.SUCCESS_LOGIN;
            }else{
                responseFile = MappingJSP.SUCCESS_LOGIN;
            }
        }catch (NotValidDataException e){
            responseFile = MappingJSP.ENROLLE_REGISTRATION;
            req.setAttribute(ANSWER_PARAM,e.getMessage());
        }
        catch (ServiceException e) {
            req.setAttribute(MESSAGE,MESSAGE_OF_ERROR);
        }
        try {
            req.getRequestDispatcher(responseFile).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't upload error page");
        }
    }
}
