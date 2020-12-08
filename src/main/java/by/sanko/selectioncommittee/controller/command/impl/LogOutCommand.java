package by.sanko.selectioncommittee.controller.command.impl;

import by.sanko.selectioncommittee.controller.command.Command;
import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.util.security.JWTUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogOutCommand implements Command {
    private static final String ID_PARAMETER = "userID";
    private static final String ROLE_PARAMETER = "userRole";
    private static final String NAME_PARAMETER = "userName";
    private static final String LASTNAME_PARAMETER = "userLastName";
    private static final String FATHERS_NAME_PARAMETER = "userFathersName";


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.removeAttribute(ID_PARAMETER);
        req.removeAttribute(NAME_PARAMETER);
        req.removeAttribute(ROLE_PARAMETER);
        req.removeAttribute(LASTNAME_PARAMETER);
        req.removeAttribute(FATHERS_NAME_PARAMETER);
        JWTUtil.getInstance().deleteCook(req,resp);
        resp.sendRedirect(req.getContextPath() + MappingJSP.WELCOME_PAGE);
    }
}
