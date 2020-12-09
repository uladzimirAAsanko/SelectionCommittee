package by.sanko.selectioncommittee.controller.command.impl;

import by.sanko.selectioncommittee.controller.command.Command;
import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.entity.Faculty;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.FacultyService;
import by.sanko.selectioncommittee.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetAllFacultiesCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE = "faculty";
    private static final String CURRENT_SITE_ATTRIBUTE = "currentSite";
    private static final String STAY_PART = "/jsp/";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
            List<Faculty> facultyList = null;
        try {
            facultyList = facultyService.getAllFaculties();
            HashMap<String,String> facs = new HashMap<>();
            for(Faculty faculty: facultyList){
                facs.put(faculty.getFacultyName(),faculty.getFacultySite());
            }
            req.setAttribute(ATTRIBUTE,facs);
            String currSiteAttr = req.getParameter(CURRENT_SITE_ATTRIBUTE);
            String currSite = currSiteAttr.substring(currSiteAttr.indexOf(STAY_PART));
            try {
                req.getRequestDispatcher(currSite).forward(req, resp);
            } catch (ServletException | IOException e) {
                logger.error("can't upload error page");
            }
        } catch (ServiceException exception) {
            resp.sendRedirect(MappingJSP.NOT_FOUND_COMMAND);
        }
    }
}
