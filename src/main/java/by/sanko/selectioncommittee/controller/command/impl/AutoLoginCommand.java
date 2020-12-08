package by.sanko.selectioncommittee.controller.command.impl;

import by.sanko.selectioncommittee.controller.command.Command;
import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.controller.filler.LoginPageFiller;
import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.ServiceFactory;
import by.sanko.selectioncommittee.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutoLoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private static final String ID_PARAMETER = "userID";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        String responseFile = MappingJSP.ERROR_PAGE;
        User user = null;
        try {
            Integer id = (Integer) req.getAttribute(ID_PARAMETER);
            if(id != null) {
                user = userService.getUserByID(id);
            }
            if(user == null){
                responseFile = MappingJSP.AUTHORIZATION;
            }else{
                LoginPageFiller pageFiller = LoginPageFiller.getInstance();
                responseFile = pageFiller.fillHomePage(req,user);
            }
        } catch (ServiceException e) {
            logger.error("can't upload error page");
        }
        try {
            req.getRequestDispatcher(responseFile).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't upload error page");
        }
    }
}
