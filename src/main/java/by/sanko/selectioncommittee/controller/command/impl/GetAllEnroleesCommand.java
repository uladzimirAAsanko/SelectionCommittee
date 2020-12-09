package by.sanko.selectioncommittee.controller.command.impl;

import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.controller.command.Command;
import by.sanko.selectioncommittee.entity.Enrollee;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.exception.ServiceImpl.ImpracticableActionException;
import by.sanko.selectioncommittee.exception.ServiceImpl.NotValidDataException;
import by.sanko.selectioncommittee.service.FacultyService;
import by.sanko.selectioncommittee.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GetAllEnroleesCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ID_PARAMETER = "userID";
    private static final String POSITIVE_ANSWER = "Statement succesfully close";
    private static final String NEGATIVE_ANSWER = "Statement dont close";
    private static final String ANSWER_PARAM = "answer";
    private static final String EXAM_PARAMETER = "exams";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int userID = (Integer) req.getAttribute(ID_PARAMETER);
        String responseFile = MappingJSP.FACULTY_PAGE_TOOLS;
        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        Map<Enrollee,Integer> enrolleeInStatement = null;
        try{
            int facultyID = facultyService.getFacultyByAdminID(userID).getFacultyID();
            enrolleeInStatement =  facultyService.getAllEnrolleSignedToFaculty(facultyID);
            if(enrolleeInStatement != null){
                HashMap<String,Integer> mapOfString = new HashMap<>();
                for(Map.Entry<Enrollee,Integer> entry : enrolleeInStatement.entrySet()){
                    mapOfString.put(entry.getKey().getLogin(),entry.getValue());
                }
                req.setAttribute(EXAM_PARAMETER,mapOfString);
                req.setAttribute(ANSWER_PARAM,POSITIVE_ANSWER);
            }else{
                req.setAttribute(ANSWER_PARAM,NEGATIVE_ANSWER);
            }

        }catch (ImpracticableActionException | NotValidDataException e){
            req.setAttribute(ANSWER_PARAM,e.getMessage());
        }catch (ServiceException e){
            responseFile = MappingJSP.ERROR_PAGE;
        }
        try {
            req.getRequestDispatcher(responseFile).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't upload error page");
        }
    }
}
