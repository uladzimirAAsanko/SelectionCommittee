package by.sanko.selectioncommittee.controller.command.impl;

import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.controller.command.Command;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.exception.ServiceImpl.ImpracticableActionException;
import by.sanko.selectioncommittee.service.FacultyService;
import by.sanko.selectioncommittee.service.ModeratorService;
import by.sanko.selectioncommittee.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddFacultyCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String FACULTY_STE_PARAM = "facultySite";
    private static final String FACULTY_NAME_PARAMETER = "facultyName";
    private static final String ID_PARAMETER = "userID";
    private static final String MINIMAL_SCORE_PARAMETER = "minimal";
    private static final String POSITIVE_ANSWER = "Faculty is successfully added";
    private static final String NEGATIVE_ANSWER = "This exam is already added";
    private static final String ANSWER_PARAM = "answer";
    private static final String ERROR_PARAM = "error";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String facultyName = req.getParameter(FACULTY_NAME_PARAMETER);
        String facultySite = req.getParameter(FACULTY_STE_PARAM);
        String responseFile = MappingJSP.MODERATOR_TOOLS;
        ModeratorService moderatorService = ServiceFactory.getInstance().getModeratorService();
        String answer = "";
        boolean isAdded = false;
        try{
            isAdded = moderatorService.addFaculty(facultyName,facultySite);
            if(isAdded){
                answer = POSITIVE_ANSWER;
            }else{
                answer = NEGATIVE_ANSWER;
            }
            req.setAttribute(ANSWER_PARAM,answer);
        }catch (ImpracticableActionException e){
            answer = e.getMessage();
            req.setAttribute(ANSWER_PARAM,answer);
        }catch (ServiceException e){
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
