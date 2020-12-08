package by.sanko.selectioncommittee.controller.command.impl;

import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.controller.command.Command;
import by.sanko.selectioncommittee.controller.filler.LoginPageFiller;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.exception.ServiceImpl.NotValidDataException;
import by.sanko.selectioncommittee.service.FacultyService;
import by.sanko.selectioncommittee.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddStatementCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String FACULTY_ID_PARAMETER = "facultyID";
    private static final String ID_PARAMETER = "userID";
    private static final String MAX_STUDENT_PARAMETER = "maxStudents";
    private static final String EXPIRED_DATE_PARAMETER = "expiredDate";
    private static final String POSITIVE_ANSWER = "Statement is successfully added";
    private static final String NEGATIVE_ANSWER = "Statement is already added";
    private static final String ANSWER_PARAM = "answer";
    private static final String ERROR_PARAM = "error";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int userID = (Integer) req.getAttribute(ID_PARAMETER);
        int facultyID = (Integer) req.getAttribute(FACULTY_ID_PARAMETER);
        int maxStudent = Integer.parseInt(req.getParameter(MAX_STUDENT_PARAMETER));
        String expiredDate = req.getParameter(EXPIRED_DATE_PARAMETER);
        String responseFile = MappingJSP.FACULTY_PAGE_TOOLS;
        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        String answer = "";
        try {
            if(facultyService.addStatementToFaculty(facultyID,userID,expiredDate,maxStudent)){
                answer = POSITIVE_ANSWER;
            }else{
                answer = NEGATIVE_ANSWER;
            }
            req.setAttribute(ANSWER_PARAM,answer);
        }catch (NotValidDataException e){
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
