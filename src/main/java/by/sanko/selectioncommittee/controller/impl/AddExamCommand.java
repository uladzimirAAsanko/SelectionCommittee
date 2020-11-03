package by.sanko.selectioncommittee.controller.impl;

import by.sanko.selectioncommittee.controller.Command;
import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.EnrolleeService;
import by.sanko.selectioncommittee.service.FacultyService;
import by.sanko.selectioncommittee.service.ServiceFactory;
import by.sanko.selectioncommittee.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddExamCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String TYPE_OF_EXAM_PARAM = "typeOfExam";
    private static final String RESULT_PARAM = "result";
    private static final String MESSAGE = "message";
    private static final String MESSAGE_TO_USER = "error while adding exam";
    private static final String POSITIVE_ANSWER = "exam added succesfully";
    private static final String NEGATIVE_ANSWER = " are problems";
    private static final String ANSWER_PARAM = "answer";

    String responseFile = MappingJSP.ERROR_PAGE;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        EnrolleeService enrolleeService = ServiceFactory.getInstance().getEnrolleeService();
        int userID = NOT.getID(req.getCookies());
        //String result = req.getParameter(RESULT_PARAM);
        //int typeOfExam = Integer.parseInt(req.getParameter(TYPE_OF_EXAM_PARAM));
        String result = "40";
        int typeOfExam = 8;
        HashMap<String,Boolean> map = null;
        try{
            map = enrolleeService.addExam(userID,typeOfExam,result);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while authorization command", e);
        }
        if(map == null){
            req.getSession().setAttribute(MESSAGE,MESSAGE_TO_USER);
        }else{
            StringBuffer answer = new StringBuffer();
            for(Map.Entry entry: map.entrySet()){
                Boolean value = (Boolean)entry.getValue();
                String key = (String)entry.getKey();
                if(!value){
                    answer.append(key);
                }
            }
            if(answer.length() == 0){
                answer.append(POSITIVE_ANSWER);
            }else{
                answer.append(NEGATIVE_ANSWER);
            }
            req.getSession().setAttribute(ANSWER_PARAM,answer);
            //TODO It should redirect to page where it comes
            resp.sendRedirect(MappingJSP.SUCCESS_LOGIN);
        }
    }
}
