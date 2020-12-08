package by.sanko.selectioncommittee.controller.filler;

import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.entity.Exam;
import by.sanko.selectioncommittee.entity.Faculty;
import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.entity.UsersRole;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.EnrolleeService;
import by.sanko.selectioncommittee.service.FacultyService;
import by.sanko.selectioncommittee.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginPageFiller {
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String FATHERS_NAME = "fathersName";
    private static final String EMAIL = "email";
    private static final String LOGIN = "login";
    private static final String FACULTY_NAME = "facultyName";
    private static final String FACULTY_SITE = "facultySite";
    private static final String AVATAR = "avatar";
    private static final String FACULTY_ID_PARAMETER = "facultyID";
    private static final String ANSWER_PARAM = "answer";

    private static final LoginPageFiller instance = new LoginPageFiller();

    private LoginPageFiller() {

    }

    public static LoginPageFiller getInstance() {
        return instance;
    }


    public String fillHomePage(HttpServletRequest req, User user) throws ServiceException {
        String answer = null;
        req.setAttribute(FIRST_NAME,user.getFirstName());
        req.setAttribute(LAST_NAME,user.getLastName());
        req.setAttribute(FATHERS_NAME,user.getFathersName());
        req.setAttribute(LOGIN,user.getLogin());
        req.setAttribute(EMAIL,user.getEmail());
        req.setAttribute(AVATAR,user.getAvatarDir());
        String answerToParam = req.getParameter(ANSWER_PARAM);
        if(answerToParam != null && !answerToParam.isEmpty()){
            req.setAttribute(ANSWER_PARAM,answerToParam);
        }
        answer = MappingJSP.SUCCESS_LOGIN;
        if(user.getRole() == UsersRole.ENROLLEE){
            EnrolleeService enrolleeService = ServiceFactory.getInstance().getEnrolleeService();
            Map<Exam,Integer> exams = enrolleeService.getAllExams(user.getUserID());
            List<String> string = new ArrayList<>();
            for (Exam key: exams.keySet()) {
                string.add(key.name());
            }
            req.setAttribute("exams ", exams);
            req.setAttribute("list ", string);
        }
        if(user.getRole() == UsersRole.MODERATOR){
            answer = MappingJSP.SUCCESS_LOGIN;
        }
        if(user.getRole() == UsersRole.ADMINISTRATOR){
            FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
            Faculty faculty = facultyService.getFacultyByAdminID(user.getUserID());
            req.setAttribute(FACULTY_NAME,faculty.getFacultyName());
            req.setAttribute(FACULTY_SITE,faculty.getFacultySite());
            answer = MappingJSP.SUCCESS_LOGIN;
        }
        return answer;
    }

    public String fillAdminPage(HttpServletRequest req, int userID) throws ServiceException {
        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        Faculty faculty = facultyService.getFacultyByAdminID(userID);
        int facultyID = (Integer) req.getAttribute(FACULTY_ID_PARAMETER);
        Map<Exam,Integer> allExams = facultyService.getAllExams(facultyID);
        HashMap<String, String> map = new HashMap<>();
        allExams.forEach((k,v)->{
            map.put(k.name(),v+"");
        });
        req.setAttribute("allExams",map);
        req.setAttribute(FACULTY_NAME,faculty.getFacultyName());
        req.setAttribute(FACULTY_SITE,faculty.getFacultySite());
        return MappingJSP.ADMINISTRATOR_WELCOME_PAGE;
    }
}
