package by.sanko.selectioncommittee.controller.filler;

import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.entity.Faculty;
import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.entity.UsersRole;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.EnrolleeService;
import by.sanko.selectioncommittee.service.FacultyService;
import by.sanko.selectioncommittee.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class LoginPageFiller {
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String FATHERS_NAME = "fathersName";
    private static final String EMAIL = "email";
    private static final String LOGIN = "login";
    private static final String FACULTY_NAME = "facultyName";
    private static final String FACULTY_SITE = "facultySite";

    private static final LoginPageFiller instance = new LoginPageFiller();

    private LoginPageFiller() {

    }

    public static LoginPageFiller getInstance() {
        return instance;
    }


    public String fillHomePage(HttpServletRequest req, User user) throws ServiceException {
        String answer = null;
        req.getSession().setAttribute(FIRST_NAME,user.getFirstName());
        req.getSession().setAttribute(LAST_NAME,user.getLastName());
        req.getSession().setAttribute(FATHERS_NAME,user.getFathersName());
        req.getSession().setAttribute(LOGIN,user.getLogin());
        req.getSession().setAttribute(EMAIL,user.getEmail());
        answer = MappingJSP.SUCCESS_LOGIN;
        if(user.getRole() == UsersRole.ENROLLEE){
            EnrolleeService enrolleeService = ServiceFactory.getInstance().getEnrolleeService();
            List<String> list = enrolleeService.getAllExams(user.getUserID());
            req.setAttribute("list", list);
        }
        if(user.getRole() == UsersRole.MODERATOR){
            answer = MappingJSP.MODERATOR_WELCOME_PAGE;
        }
        if(user.getRole() == UsersRole.ADMINISTRATOR){
            FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
            Faculty faculty = facultyService.getFacultyByAdminID(user.getUserID());
            req.getSession().setAttribute(FACULTY_NAME,faculty.getFacultyName());
            req.getSession().setAttribute(FACULTY_SITE,faculty.getFacultySite());
            answer = MappingJSP.ADMINISTRATOR_WELCOME_PAGE;
        }
        return answer;
    }
}
