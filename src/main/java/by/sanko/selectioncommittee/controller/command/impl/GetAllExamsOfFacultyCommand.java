package by.sanko.selectioncommittee.controller.command.impl;

import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.controller.command.Command;
import by.sanko.selectioncommittee.entity.Exam;
import by.sanko.selectioncommittee.exception.ServiceException;
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

public class GetAllExamsOfFacultyCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String FACULTY_ID_PARAMETER = "facultyID";
    private static final String ERROR_PARAM = "error";
    private static final String EXAM_PARAMETER = "exams";


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int facultyID = (Integer) req.getAttribute(FACULTY_ID_PARAMETER);
        String responseFile = MappingJSP.FACULTY_PAGE_TOOLS;
        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        String answer = "";
        Map<Exam,Integer> map = null;
        try {
            map = facultyService.getAllExams(facultyID);
        } catch (ServiceException e) {
            responseFile = MappingJSP.ERROR_PAGE;
            req.setAttribute(ERROR_PARAM,e.getMessage());
        }
        HashMap<String,Integer> mapOfString = new HashMap<>();
        for(Map.Entry<Exam,Integer> entry : map.entrySet()){
            mapOfString.put(entry.getKey().name(),entry.getValue());
        }
        req.setAttribute(EXAM_PARAMETER,mapOfString);
        try {
            req.getRequestDispatcher(responseFile).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't upload error page");
        }
    }
}
