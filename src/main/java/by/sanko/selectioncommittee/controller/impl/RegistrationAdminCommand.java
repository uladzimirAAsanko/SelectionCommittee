package by.sanko.selectioncommittee.controller.impl;

import by.sanko.selectioncommittee.controller.Command;
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

public class RegistrationAdminCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ADMIN_CODE = "code";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        String code = req.getParameter(ADMIN_CODE);
        boolean result = false;
        int facultyID = -1;
        String responseFile = MappingJSP.ERROR_PAGE;
        int id = NOT.getID(req.getCookies());
        try {
            result = facultyService.registerAdmin(id,code);
        } catch (ServiceException e) {
            logger.error("can't register admin");
        }
        if(result){
            responseFile = MappingJSP.SUCCESS_REGISTRATION;
        }else{
            responseFile = MappingJSP.FAIL_REGISTRATION;
        }
        try {
            req.getRequestDispatcher(responseFile).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't upload error page");
        }
    }
}
