package by.sanko.selectioncommittee.controller.command.impl;

import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.controller.command.Command;
import by.sanko.selectioncommittee.entity.Exam;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.exception.ServiceImpl.ImpracticableActionException;
import by.sanko.selectioncommittee.exception.ServiceImpl.NotValidDataException;
import by.sanko.selectioncommittee.service.EnrolleeService;
import by.sanko.selectioncommittee.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DeleteExamFromEnrolleeCommand  implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String TYPE_OF_EXAM_PARAM = "typeOfExam";
    private static final String POSITIVE_ANSWER = "Exam removed succesfully";
    private static final String NEGATIVE_ANSWER = "Exams dont removed check valid data";
    private static final String ANSWER_PARAM = "answer";
    private static final String ID_PARAMETER = "userID";
    private static final String EXAM_PARAMETER = "exams";


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int userID = (Integer) req.getAttribute(ID_PARAMETER);
        int typeOfExam = Integer.parseInt(req.getParameter(TYPE_OF_EXAM_PARAM));
        EnrolleeService enrolleeService = ServiceFactory.getInstance().getEnrolleeService();
        String responseFile = MappingJSP.EXAM_MANAGMENT;
        boolean isRemoved = false;
        try{
            isRemoved = enrolleeService.removeExam(userID, Exam.findExamByIndex(typeOfExam));
            if(!isRemoved){
                req.setAttribute(ANSWER_PARAM,NEGATIVE_ANSWER);
            }else{
                req.setAttribute(ANSWER_PARAM,POSITIVE_ANSWER);
            }

        }catch (ImpracticableActionException | NotValidDataException e){
            req.setAttribute(ANSWER_PARAM,e.getMessage());
        }catch (ServiceException e){
            responseFile = MappingJSP.ERROR_PAGE;
        }
        Map<Exam,Integer> map = null;
        try {
            map = enrolleeService.getAllExams(userID);
        } catch (ServiceException e) {
            responseFile = MappingJSP.ERROR_PAGE;
        }
        HashMap<String,Integer> mapOfString = new HashMap<>();
        for(Map.Entry<Exam,Integer> entry : map.entrySet()){
            mapOfString.put(entry.getKey().name(),entry.getValue());
        }
        req.setAttribute(EXAM_PARAMETER,mapOfString);
        try {
            req.getRequestDispatcher(responseFile).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't upload error page");
        }
    }
}
