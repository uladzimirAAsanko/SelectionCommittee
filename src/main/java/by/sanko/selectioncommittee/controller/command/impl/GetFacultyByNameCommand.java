package by.sanko.selectioncommittee.controller.command.impl;

import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.controller.command.Command;
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

public class GetFacultyByNameCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String FACULTY_ATTRIBUTE = "faculty";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String SITE_ATTRIBUTE = "site";
    private static final String AVATAR_ATTRIBUTE = "avatar";
    private static final String ERROR_PARAM = "error";
    private static final String STATEMENT_PARAM = "statement";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        String facultyName =  req.getParameter(FACULTY_ATTRIBUTE);
        String responseFile = MappingJSP.FACULTY_PROFILE;
        try {
            Faculty faculty = facultyService.getFacultyByName(facultyName);
            Boolean isStatementExist = facultyService.isStatementExist(faculty.getFacultyID());
            req.setAttribute(NAME_ATTRIBUTE, faculty.getFacultyName());
            req.setAttribute(SITE_ATTRIBUTE, faculty.getFacultySite());
            req.setAttribute(AVATAR_ATTRIBUTE, faculty.getFacultyAvatar());
            req.setAttribute(STATEMENT_PARAM,isStatementExist.toString());
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
