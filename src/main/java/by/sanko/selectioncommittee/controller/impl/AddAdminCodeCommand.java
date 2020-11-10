package by.sanko.selectioncommittee.controller.impl;

import by.sanko.selectioncommittee.controller.Command;
import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.ModeratorService;
import by.sanko.selectioncommittee.service.ServiceFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddAdminCodeCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String adminCodeParam = "adminCode";
    private static final String facultyIDParam = "facultyID";
    private static final String POSITIVE_ANSWER = "admin code added succesfully";
    private static final String NEGATIVE_ANSWER = "admin code already banned";
    private static final String ANSWER_PARAM = "answer";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ModeratorService moderatorService = ServiceFactory.getInstance().getModeratorService();
        String adminCode = req.getParameter(adminCodeParam);
        int facultyID = Integer.parseInt(req.getParameter(facultyIDParam));
        boolean result = false;
        try {
            result = moderatorService.addAdminCode(adminCode,facultyID);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while adding admin code command", e);
        }
        String answer = null;
        if(result){
            answer = POSITIVE_ANSWER;
        }else{
            answer = NEGATIVE_ANSWER;
        }
        req.getSession().setAttribute(ANSWER_PARAM,answer);
        resp.sendRedirect(MappingJSP.MODERATOR_WELCOME_PAGE);
    }
}
