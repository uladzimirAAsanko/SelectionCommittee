package by.sanko.selectioncommittee.service.impl;

import by.sanko.selectioncommittee.dao.DaoFactory;
import by.sanko.selectioncommittee.dao.EnrolleeDao;
import by.sanko.selectioncommittee.dao.FacultyDao;
import by.sanko.selectioncommittee.entity.Enrollee;
import by.sanko.selectioncommittee.entity.Exam;
import by.sanko.selectioncommittee.entity.Faculty;
import by.sanko.selectioncommittee.exception.DaoException;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.exception.ServiceImpl.ImpracticableActionException;
import by.sanko.selectioncommittee.exception.ServiceImpl.NotValidDataException;
import by.sanko.selectioncommittee.service.EnrolleeService;
import by.sanko.selectioncommittee.service.FacultyService;
import by.sanko.selectioncommittee.service.ServiceFactory;
import by.sanko.selectioncommittee.util.validator.ExamValidator;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EnrolleeServiceImpl implements EnrolleeService {
    private static final int MAXIMUM_OF_PROFILE_EXAMS = 2;

    @Override
    public Enrollee getEnrolleeByID(int enrolleeID) throws ServiceException {
        Enrollee enrollee = null;
        EnrolleeDao enrolleeDao = DaoFactory.getInstance().getEnrolleeDao();
        try {
            enrollee = enrolleeDao.getEnrolleeByID(enrolleeID);
        }catch (DaoException e){
            throw  new ServiceException("Exception while getting enrollee by id",e);
        }
        return enrollee;
    }

    @Override
    public boolean addExam(int userID, Exam exam, String resultOfExam) throws ServiceException {
       boolean isAdded = false;
        EnrolleeDao enrolleeDao = DaoFactory.getInstance().getEnrolleeDao();
        try{
            if(!ExamValidator.getInstance().validateExam(resultOfExam)){
                throw new NotValidDataException("Not valid data");
            }
            int result = Integer.parseInt(resultOfExam);
            if(enrolleeDao.isResultAlreadyAdded(userID,exam.getIndex())){
                throw new NotValidDataException("Exam is already added");
            }
            if(enrolleeDao.isUserRegisterToStatement(userID)){
                throw new ImpracticableActionException("User already signed up to faculty");
            }
            if(exam == Exam.BELARUSIAN || exam == Exam.RUSSIAN){
                isAdded = enrolleeDao.addExam(userID,exam,result);
            }else{
                if(getNumberOfProfileExams(userID) == MAXIMUM_OF_PROFILE_EXAMS){
                    throw new ImpracticableActionException("User already take all profile exams");
                }
                isAdded = enrolleeDao.addExam(userID,exam,result);
            }
        }catch (DaoException e){
            throw new ServiceException("Exception while removing exam from user",e);
        }
        return isAdded;
    }

    @Override
    public boolean removeExam(int userID, Exam exam) throws ServiceException {
        boolean isRemoved = false;
        EnrolleeDao enrolleeDao = DaoFactory.getInstance().getEnrolleeDao();
        try{
            if(!enrolleeDao.isResultAlreadyAdded(userID,exam.getIndex())){
                throw new NotValidDataException("Exam is already removed");
            }
            if(enrolleeDao.isUserRegisterToStatement(userID)){
                throw new ImpracticableActionException("User already signed up to faculty");
            }
            isRemoved = enrolleeDao.removeExam(userID,exam);
        }catch (DaoException e){
            throw new ServiceException("Exception while removing exam from user",e);
        }
        return isRemoved;
    }

    @Override
    public boolean registerUser(int idUser, String certificate,String additionalInfo) throws ServiceException {
        ExamValidator validator = ExamValidator.getInstance();
        int resultOfCertificate = 0;
        boolean answer = false;
        if(!validator.validateExam(certificate)){
            throw new NotValidDataException("Not valid certificate score");
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
    public HashMap<Exam,Integer> getAllExams(int userID) throws  ServiceException {
        EnrolleeDao enrolleeDao = DaoFactory.getInstance().getEnrolleeDao();
        HashMap<Exam,Integer> result = null;
        try{
            result = enrolleeDao.getAllExams(userID);
        }catch (DaoException e){
            throw new ServiceException("Exception while getting all exams",e);
        }
        return result;
    }

    @Override
    public int getNumberOfProfileExams(int userID) throws ServiceException {
        HashMap<Exam,Integer> result = getAllExams(userID);
       int profileExams =  result.entrySet()
                .stream()
                .filter(map->map.getKey() != Exam.BELARUSIAN && map.getKey() != Exam.RUSSIAN)
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue())).size();
        return profileExams;
    }

    //TODO: Too Complicated method maybe should add column into statement, in which write minimal score to pass in this faculty
    @Override
    public Map<Faculty, Integer> getAllFaculties() throws ServiceException {
        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        EnrolleeDao enrolleeDao = DaoFactory.getInstance().getEnrolleeDao();
        HashMap<Faculty, Integer> answer = new HashMap<>();
        try {
            List<Faculty> allFaculties = enrolleeDao.getAllFaculties();
            for(Faculty faculty : allFaculties){
                answer.put(faculty,facultyService.calculateMinimalScoreToEnrollInFaculty(faculty.getFacultyID()));
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception while getting all faculties",e);
        }
        return answer;
    }

    @Override
    public HashMap<Faculty, Integer> getFacultiesAvailableForUser(int userID) throws ServiceException {
        EnrolleeDao enrolleeDao = DaoFactory.getInstance().getEnrolleeDao();
        FacultyService facultyService = ServiceFactory.getInstance().getFacultyService();
        HashMap<Faculty, Integer> faculties = new HashMap<>();
        try {
            HashMap<Exam, Integer> userExam = enrolleeDao.getAllExams(userID);
            if(userExam.get(Exam.BELARUSIAN) == null && userExam.get(Exam.RUSSIAN) == null){
                throw new ImpracticableActionException("User hasn't no languages can't sign to any faculty");
            }
            List<Faculty> availableFaculties = null;
            availableFaculties = enrolleeDao.getFacultiesWithExam(Exam.RUSSIAN);
            userExam.remove(Exam.RUSSIAN);
            if (userExam.get(Exam.BELARUSIAN) != null){
                availableFaculties.addAll(enrolleeDao.getFacultiesWithExam(Exam.BELARUSIAN));
                userExam.remove(Exam.BELARUSIAN);
            }
            for(Map.Entry<Exam,Integer> entry : userExam.entrySet()){
                List<Faculty> facWithExam = enrolleeDao.getFacultiesWithExam(entry.getKey());
                availableFaculties.retainAll(facWithExam);
            }
            for(Faculty faculty : availableFaculties){
                faculties.put(faculty,facultyService.calculateMinimalScoreToEnrollInFaculty(faculty.getFacultyID()));
            }
        } catch (Exception e) {
            throw new ServiceException("Exception while getting all exams",e);
        }
        return faculties;
    }

    @Override
    public boolean signToFaculty(int userID, int facultyID) throws ServiceException {
        EnrolleeDao enrolleeDao = DaoFactory.getInstance().getEnrolleeDao();
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        boolean isSigned = false;
        try {
            if(enrolleeDao.isUserRegisterToStatement(userID)){
                throw new ImpracticableActionException("User is already sign to faculty");
            }
            if(facultyDao.getExpiredDateOfStatement(facultyID).before(java.sql.Date.valueOf(LocalDate.now()))){
                throw new ImpracticableActionException("Statement is already closed");
            }
            if(!facultyDao.isFacultyHasStatement(facultyID)){
                throw new ImpracticableActionException("Faculty hasn't statement");
            }
            HashMap<Exam,Integer> userExam = enrolleeDao.getAllExams(userID);
            boolean isLanguageIsSigned = true;
            for(Map.Entry<Exam,Integer> entry : userExam.entrySet()){
                Exam exam = entry.getKey();
                int minimalScore = facultyDao.getMinimalScoreOfThisExamByFaculty(exam,facultyID);
                if(minimalScore == -1){
                    if(isLanguageIsSigned && (exam.equals(Exam.BELARUSIAN) || exam.equals(Exam.RUSSIAN))){
                        isLanguageIsSigned = false;
                    }else{
                        throw new ImpracticableActionException("Faculty hasn't exam");
                    }
                }
                if(minimalScore > entry.getValue()){
                    if(isLanguageIsSigned && (exam.equals(Exam.BELARUSIAN) || exam.equals(Exam.RUSSIAN))){
                        isLanguageIsSigned = false;
                    }else{
                        throw new ImpracticableActionException("Users exam doesn't have enough score to sign");
                    }
                }
            }
            int statementID = facultyDao.getStatementIDByFacultyID(facultyID);
            isSigned = enrolleeDao.signUpToStatement(userID,statementID);
        }catch (DaoException e){
            throw new ServiceException("Exception while signing to faculty",e);
        }
        return isSigned;
    }

    @Override
    public boolean unSignFromFaculty(int userID, int facultyID) throws ServiceException {
        boolean isUnSigned = false;
        EnrolleeDao enrolleeDao = DaoFactory.getInstance().getEnrolleeDao();
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try {
            if(!facultyDao.isFacultyHasStatement(facultyID)){
                throw new ImpracticableActionException("Faculty hasn't statement");
            }
            int statementID = facultyDao.getStatementIDByFacultyID(facultyID);
            if(enrolleeDao.getStatementOfUserAttached(userID) != statementID){
                throw new ImpracticableActionException("User didn't signed to this faculty");
            }
            if(facultyDao.getExpiredDateOfStatement(facultyID).before(java.sql.Date.valueOf(LocalDate.now()))){
                throw new ImpracticableActionException("Statement is already closed");
            }
            isUnSigned = enrolleeDao.removeUserFromStatement(userID,statementID);
        }catch (DaoException e){
            throw new ServiceException("Exception while removing user from faculty's statement",e);
        }
        return isUnSigned;
    }

    @Override
    public int calculateUserScore(int userID) throws ServiceException {
        int userScore = 0;
        EnrolleeDao enrolleeDao = DaoFactory.getInstance().getEnrolleeDao();
        try {
            userScore = enrolleeDao.getCertificateOfEnrollee(userID);
        } catch (DaoException e) {
            throw new ServiceException("Exception while getting user certificate",e);
        }
        if(userScore == -1){
            throw new ImpracticableActionException("User is not enrollee");
        }
        HashMap<Exam,Integer> result = getAllExams(userID);
        int resultOfLanguage = 0;
        for(Map.Entry<Exam,Integer> entry : result.entrySet()){
            int value = entry.getValue();
            if((entry.getKey() == Exam.BELARUSIAN || entry.getKey() == Exam.RUSSIAN)){
                if( value > resultOfLanguage) {
                    userScore -= resultOfLanguage;
                    resultOfLanguage = value;
                    userScore += resultOfLanguage;
                }
            }else {
                userScore += value;
            }
        }
        return userScore;
    }

    @Override
    public Map<Faculty, Integer> getFacultiesThatGoodToUser(int userID) throws ServiceException {
        HashMap<Faculty, Integer> availableFaculties = getFacultiesAvailableForUser(userID);
        int userResult = calculateUserScore(userID);
        Map<Faculty, Integer> goodForUser = availableFaculties.entrySet()
                .stream()
                .filter(map -> map.getValue() <= userResult)
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
        return goodForUser;
    }
}
