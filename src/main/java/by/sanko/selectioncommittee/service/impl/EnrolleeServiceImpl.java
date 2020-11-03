package by.sanko.selectioncommittee.service.impl;

import by.sanko.selectioncommittee.dao.DaoFactory;
import by.sanko.selectioncommittee.dao.EnrolleeDao;
import by.sanko.selectioncommittee.dao.UserDao;
import by.sanko.selectioncommittee.entity.Exam;
import by.sanko.selectioncommittee.entity.RegistrationData;
import by.sanko.selectioncommittee.exception.DaoException;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.EnrolleeService;
import by.sanko.selectioncommittee.util.validator.ExamValidator;

import java.util.HashMap;
import java.util.List;

public class EnrolleeServiceImpl implements EnrolleeService {
    private static final String PARAM_OF_MAP_CHECK = "Is result correct";
    private static final String PARAM_OF_MAP_IS_ADDED = "Is exam uniq";

    @Override
    public HashMap<String, Boolean> addExam(int userID, int indexOfExam, String result) throws ServiceException {
        HashMap<String, Boolean> map = new HashMap<>();
        ExamValidator validator = ExamValidator.getInstance();
        int answer = 0;
        if(!validator.validateExam(result)){
            map.put(PARAM_OF_MAP_CHECK,Boolean.FALSE);
            return map;
        }
        answer = Integer.parseInt(result);
        EnrolleeDao enrolleeDao = DaoFactory.getInstance().getEnrolleeDao();
        try {
            Exam exam = Exam.findExamByIndex(indexOfExam);
            map.put(PARAM_OF_MAP_IS_ADDED,enrolleeDao.addExam(userID,exam,answer));
        } catch (DaoException e) {
            throw new ServiceException("Exception while adding exam to user",e);
        }
        return map;
    }

    @Override
    public boolean registerUser(int idUser, String certificate,String additionalInfo) throws ServiceException {
        ExamValidator validator = ExamValidator.getInstance();
        int resultOfCertificate = 0;
        HashMap<String, Boolean> map = new HashMap<>();
        boolean answer = false;
        if(!validator.validateExam(certificate)){
            return false;
        }
        resultOfCertificate = Integer.parseInt(certificate);
        EnrolleeDao enrolleeDao = DaoFactory.getInstance().getEnrolleeDao();
        try {
            answer = enrolleeDao.registration(idUser,resultOfCertificate,additionalInfo);
        } catch (DaoException e) {
            throw new ServiceException("Exception while adding exam to user",e);
        }
        return answer;
    }

    @Override
    public List<String> getAllExams(int userID) throws  ServiceException {
        EnrolleeDao enrolleeDao = DaoFactory.getInstance().getEnrolleeDao();
        List<String> result = null;
        try{
            result = enrolleeDao.getAllExams(userID);
        }catch (DaoException e){
            throw new ServiceException("Exception while hetting all exams",e);
        }
        return result;
    }
}
