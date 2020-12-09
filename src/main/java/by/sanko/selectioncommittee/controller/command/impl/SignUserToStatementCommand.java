package by.sanko.selectioncommittee.controller.command.impl;

import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.controller.command.Command;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.exception.ServiceImpl.ImpracticableActionException;
import by.sanko.selectioncommittee.exception.ServiceImpl.NotValidDataException;
import by.sanko.selectioncommittee.service.EnrolleeService;
import by.sanko.selectioncommittee.service.FacultyService;
import by.sanko.selectioncommittee.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUserToStatementCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String NAME_ATTRIBUTE = "nameOfFaculty";
    private static final String ID_PARAMETER = "userID";
    private static final String ERROR_PARAM = "error";
    private static final String STATEMENT_PARAM = "statement";
    private static final String ANSWER_PARAM = "answer";
    private static final String POSITIVE_ANSWER = "User is successfully added to faculty";
    private static final String NEGATIVE_ANSWER = "User isn't added to faculty ";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String facultyName =  req.getParameter(NAME_ATTRIBUTE);
        int userID = (Integer) req.getAttribute(ID_PARAMETER);
        EnrolleeService enrolleeService = ServiceFactory.getInstance().getEnrolleeService();
        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        boolean isSigned = false;
        String answer = "";
        String responseFile = MappingJSP.FACULTY_PAGE_TOOLS;
        try {
            int facultyID = facultyService.getFacultyByName(facultyName).getFacultyID();
            isSigned = enrolleeService.signToFaculty(userID,facultyID);
            if(isSigned){
                req.setAttribute(ANSWER_PARAM,POSITIVE_ANSWER);
            }else{
                req.setAttribute(ANSWER_PARAM,NEGATIVE_ANSWER);
            }
        }catch (NotValidDataException | ImpracticableActionException e){
            answer = e.getMessage();
            req.setAttribute(ANSWER_PARAM,answer);
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
