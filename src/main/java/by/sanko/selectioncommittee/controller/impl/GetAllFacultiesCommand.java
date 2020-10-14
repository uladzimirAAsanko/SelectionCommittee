package by.sanko.selectioncommittee.controller.impl;

import by.sanko.selectioncommittee.controller.Command;
import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.entity.Faculty;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.FacultyService;
import by.sanko.selectioncommittee.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAllFacultiesCommand implements Command {
    private static final String ATTRIBUTE = "faculty";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        List<Faculty> facultyList = null;
        try {
            facultyList = facultyService.getAllFaculties();
            String answer = facultyService.transformListToString(facultyList);
            req.getSession().setAttribute(ATTRIBUTE,answer);
            resp.sendRedirect(MappingJSP.WELCOME_PAGE);
        } catch (ServiceException exception) {
            resp.sendRedirect(MappingJSP.NOT_FOUND_COMMAND);
        }
    }
}
