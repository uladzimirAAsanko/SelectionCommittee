package by.sanko.selectioncommittee.controller.command.impl;

import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.controller.command.Command;
import by.sanko.selectioncommittee.entity.Enrollee;
import by.sanko.selectioncommittee.entity.Faculty;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.EnrolleeService;
import by.sanko.selectioncommittee.service.FacultyService;
import by.sanko.selectioncommittee.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetGoodFacultiesCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE = "faculty";
    private static final String ID_PARAMETER = "userID";
    private static final String USER_RESULT_ATTRIBUTE = "userResult";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        EnrolleeService enrolleeService = ServiceFactory.getInstance().getEnrolleeService();
        Map<Faculty,Integer> facultyList = null;
        try {
            int id = (Integer) req.getAttribute(ID_PARAMETER);
            Integer userResult = enrolleeService.calculateUserScore(id);
            facultyList = enrolleeService.getFacultiesThatGoodToUser(id);
            HashMap<String,String> facs = new HashMap<>();
            for(Map.Entry<Faculty,Integer> entry : facultyList.entrySet()){
                facs.put(entry.getKey().getFacultyName(),entry.getValue().toString());
            }
            req.setAttribute(USER_RESULT_ATTRIBUTE,userResult.toString());
            req.setAttribute(ATTRIBUTE,facs);
            try {
                req.getRequestDispatcher(MappingJSP.FACULTY_PAGE_TOOLS).forward(req, resp);
            } catch ( IOException e) {
                logger.error("can't upload error page");
            }
        } catch (ServiceException exception) {
            resp.sendRedirect(MappingJSP.NOT_FOUND_COMMAND);
        }
    }
}
