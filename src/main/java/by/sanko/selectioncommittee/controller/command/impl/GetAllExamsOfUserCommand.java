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

public class GetAllExamsOfUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String TYPE_OF_EXAM_PARAM = "typeOfExam";
    private static final String RESULT_PARAM = "result";
    private static final String POSITIVE_ANSWER = "exam added succesfully";
    private static final String NEGATIVE_ANSWER = "Exams dont added check valid data";
    private static final String ANSWER_PARAM = "answer";
    private static final String ID_PARAMETER = "userID";
    private static final String EXAM_PARAMETER = "exams";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String responseFile = MappingJSP.EXAM_MANAGMENT;
        EnrolleeService enrolleeService = ServiceFactory.getInstance().getEnrolleeService();
        int userID = (Integer) req.getAttribute(ID_PARAMETER);
        try {
            Map<Exam,Integer> map =  enrolleeService.getAllExams(userID);
            HashMap<String,Integer> mapOfString = new HashMap<>();
            for(Map.Entry<Exam,Integer> entry : map.entrySet()){
                mapOfString.put(entry.getKey().name(),entry.getValue());
            }
            req.setAttribute(EXAM_PARAMETER,mapOfString);
        }catch (ServiceException e){
            responseFile = MappingJSP.ERROR_PAGE;
        }
        try {
            req.getRequestDispatcher(responseFile).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't upload error page");
        }
    }
}
