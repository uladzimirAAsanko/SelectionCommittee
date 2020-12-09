package by.sanko.selectioncommittee.service.impl;

import by.sanko.selectioncommittee.dao.DaoFactory;
import by.sanko.selectioncommittee.dao.EnrolleeDao;
import by.sanko.selectioncommittee.dao.FacultyDao;
import by.sanko.selectioncommittee.entity.Enrollee;
import by.sanko.selectioncommittee.entity.Exam;
import by.sanko.selectioncommittee.entity.Faculty;
import by.sanko.selectioncommittee.entity.UsersRole;
import by.sanko.selectioncommittee.exception.DaoException;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.exception.ServiceImpl.ImpracticableActionException;
import by.sanko.selectioncommittee.exception.ServiceImpl.NotValidDataException;
import by.sanko.selectioncommittee.service.EnrolleeService;
import by.sanko.selectioncommittee.service.FacultyService;
import by.sanko.selectioncommittee.service.ServiceFactory;
import by.sanko.selectioncommittee.util.mail.MessageCreator;
import by.sanko.selectioncommittee.util.parser.ParseDate;
import by.sanko.selectioncommittee.util.validator.StatementValidator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class FacultyServiceImpl implements FacultyService {
    private static final int minimalNededExam = 3;

    @Override
    public List<Faculty> getAllFaculties() throws ServiceException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        List<Faculty> list = null;
        try{
            list = facultyDao.getAllSitesOfFaculties();
        } catch (DaoException e) {
            throw new ServiceException("Exception while getting a list of faculties",e);
        }
        return list;
    }

    @Override
    public boolean registerAdmin(int userID,String code) throws ServiceException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        Faculty faculty = null;
        boolean isRegister = false;
        try {
            int facultyID =  facultyDao.getIDbyCode(code);
            if(facultyID != -1){
                isRegister = facultyDao.registerUser(facultyID,userID);
            }
            if(isRegister){
                isRegister = DaoFactory.getInstance().getUserDao().changeUserRole(UsersRole.ADMINISTRATOR,userID);
            }
            if(isRegister){
                isRegister = facultyDao.deleteAdminCode(code);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception while checking admins code",e);
        }
        return isRegister;
    }

    @Override
    public Faculty getFacultyByAdminID(int userID) throws ServiceException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        Faculty faculty = null;
        int facultyID = -1;
        try {
            facultyID = facultyDao.getFacultyIdByUserID(userID);
            faculty = facultyDao.getFacultyById(facultyID);
        } catch (DaoException e) {
            throw new ServiceException("Exception while getting faculty by admins code", e);
        }
        return faculty;
    }

    @Override
    public boolean addExamToFaculty(Exam exam, int facultyID, int minimalScore) throws ServiceException {
        boolean isAdded = false;
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try{
            if(facultyDao.isFacultyHasStatement(facultyID)){
                throw new ImpracticableActionException("Faculty has statement");
            }
            if(!facultyDao.isFacultyHasExam(exam,facultyID)){
                int numberOfExams = facultyDao.getNumberOfExamsOfFaculty(facultyID);
                if(facultyDao.isFacultyHasExam(Exam.BELARUSIAN,facultyID)){
                    numberOfExams--;
                }
                if(facultyDao.isFacultyHasExam(Exam.RUSSIAN,facultyID)){
                    numberOfExams--;
                }
                if(numberOfExams < minimalNededExam){
                    isAdded = facultyDao.addExamToFaculty(exam,facultyID, minimalScore);
                }else{
                    throw new NotValidDataException("You already added all exams");
                }
            }
        }catch (DaoException e){
            throw new ServiceException("Exception while adding exam to faculty", e);
        }
        return isAdded;
    }

    @Override
    public HashMap<Exam, Integer> getAllExams(int facultyID) throws ServiceException {
        HashMap<Exam, Integer> answer =null;
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try{
            answer = facultyDao.getAllExams(facultyID);
        } catch (DaoException e) {
            throw new ServiceException("Exception while getting all exams from faculty", e);
        }
        return answer;
    }

    @Override
    public boolean deleteExamFromFaculty(Exam exam, int facultyID) throws ServiceException {
        boolean isDeleted = false;
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try{
            if(facultyDao.isFacultyHasStatement(facultyID)){
               throw new ImpracticableActionException("Faculty has statement");
            }
            if(facultyDao.isFacultyHasExam(exam,facultyID)) {
                isDeleted = facultyDao.deleteExam(exam, facultyID);
            }
        }catch (DaoException e){
            throw new ServiceException("Exception while deleting exam from faculty", e);
        }
        return isDeleted;
    }

    @Override
    public boolean addStatementToFaculty(int facultyID, int adminID, String expiredAt, int numberOfMaxStudents) throws ServiceException {
        boolean isAdded = false;
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        if(!StatementValidator.validateStatement(expiredAt,numberOfMaxStudents)){
            throw new NotValidDataException("Expired date or numberOfMaxStudents are not valid");
        }
        try{
            if(!facultyDao.isFacultyHasStatement(facultyID) && facultyDao.getNumberOfExamsOfFaculty(facultyID) > 2){
                Date dateClose = ParseDate.parse(expiredAt);
                Date dateStart = Date.valueOf(LocalDate.now());
                isAdded = facultyDao.addStatementOfFaculty(facultyID,adminID,dateStart,dateClose,numberOfMaxStudents);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception while adding statement to faculty", e);
        }
        return isAdded;
    }

    @Override
    public boolean updateStatement(int numberOfMaxStudents, String closeAt, int facultyID, int adminID) throws ServiceException {
        boolean isUpdated = false;
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        if(StatementValidator.validateStatement(closeAt,numberOfMaxStudents)){
            throw new NotValidDataException("Expired date or numberOfMaxStudents are not valid");
        }
        try{
            if(facultyDao.isFacultyHasStatement(facultyID)){
                Date dateClose = ParseDate.parse(closeAt);
                isUpdated = facultyDao.updateStatement(numberOfMaxStudents,dateClose,facultyID,adminID);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception while updating statement of faculty", e);
        }
        return isUpdated;
    }

    @Override
    public boolean deleteStatement(int facultyID, int adminID) throws ServiceException {
        boolean isDeleted = false;
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try{
            if(facultyDao.isFacultyHasStatement(facultyID)){
                HashMap<Enrollee,Integer> enrollees = getAllEnrolleSignedToFaculty(facultyID);
                Faculty faculty = facultyDao.getFacultyById(facultyID);
                for(Map.Entry<Enrollee,Integer> entry : enrollees.entrySet()){
                    Enrollee enrollee = entry.getKey();
                    MessageCreator.writeMessageWithDeletingStatement(enrollee.getEmail(), enrollee.getFirstName(), enrollee.getLastName(),enrollee.getLastName(),faculty.getFacultyName());
                }
                isDeleted = facultyDao.deleteStatement(facultyID,adminID);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception while updating deleting of faculty", e);
        }
        return isDeleted;
    }

    @Override
    public boolean isStatementExist(int facultyID) throws ServiceException {
        boolean isExist = false;
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try{
            isExist = facultyDao.isFacultyHasStatement(facultyID);
        }catch (DaoException e){
            throw new ServiceException("Exception while checking statement by facultyID", e);
        }
        return isExist;
    }

    @Override
    public Faculty getFacultyByID(int facultyID) throws ServiceException {
        Faculty faculty = null;
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try{
            faculty = facultyDao.getFacultyById(facultyID);
        } catch (DaoException e) {
            throw new ServiceException("Exception while getting of faculty", e);
        }
        return faculty;
    }

    @Override
    public Faculty getFacultyByName(String facultyName) throws ServiceException {
        Faculty faculty = null;
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try{
            faculty = facultyDao.getFacultyByName(facultyName);
        } catch (DaoException e) {
            throw new ServiceException("Exception while getting of faculty", e);
        }
        return faculty;
    }

    @Override
    public HashMap<Enrollee, Integer> getAllEnrolleSignedToFaculty(int facultyID) throws ServiceException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        int statementID = 0;
        HashMap<Enrollee, Integer> answer = null;
        try {
            statementID = facultyDao.getStatementIDByFacultyID(facultyID);
            List<Integer> enrolleeID = facultyDao.getAllUsersIDFromStatement(statementID);
            if(enrolleeID != null && !enrolleeID.isEmpty()) {
                answer = new HashMap<>();
                EnrolleeService enrolleeService = ServiceFactory.getInstance().getEnrolleeService();
                for (Integer id : enrolleeID) {
                    answer.put(enrolleeService.getEnrolleeByID(id), enrolleeService.calculateUserScore(id));
                }
                answer = answer.entrySet()
                        .stream()
                        .sorted(Map.Entry.<Enrollee, Integer>comparingByValue().reversed())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception while all abiturients of faculty", e);
        }
        return answer;
    }

    @Override
    public int calculateMinimalScoreToEnrollInFaculty(int facultyID) throws ServiceException {
        HashMap<Enrollee,Integer> enrolleeSignedToFaculty = getAllEnrolleSignedToFaculty(facultyID);
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        int minimalScore = 0;
        try {
            int maxStudents = facultyDao.getNumberOfMaxStudentsInStatement(facultyID);
            if(enrolleeSignedToFaculty != null && maxStudents <= enrolleeSignedToFaculty.size()){
                SortedSet<Integer> values = new TreeSet<>(enrolleeSignedToFaculty.values());
                for(Integer userScore: values){
                    maxStudents--;
                    if(maxStudents == 0){
                        minimalScore = userScore;
                    }
                }
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception while getting maximal number of students",e);
        }
        return minimalScore;
    }

    @Override
    public HashMap<Enrollee, Integer> closeStatement(int facultyID) throws ServiceException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        HashMap<Enrollee, Integer> remainder = null;
        try {
            if(!facultyDao.isFacultyHasStatement(facultyID)){
                throw new ImpracticableActionException("Faculty hasn't statement");
            }
            if(facultyDao.getExpiredDateOfStatement(facultyID).after(Date.valueOf(LocalDate.now()))){
                throw new ImpracticableActionException("Statement isn't expired yet");
            }
            HashMap<Enrollee,Integer> allEnrollee = getAllEnrolleSignedToFaculty(facultyID);
            int numbersOfMaxStudent = facultyDao.getNumberOfMaxStudentsInStatement(facultyID);
            Faculty faculty = facultyDao.getFacultyById(facultyID);
            int statementID = facultyDao.getStatementIDByFacultyID(facultyID);
            if(allEnrollee.size() <= numbersOfMaxStudent){
                for(Map.Entry<Enrollee,Integer> entry : allEnrollee.entrySet()){
                    Enrollee enrollee = entry.getKey();
                    MessageCreator.writeSuccessfullEnrollment(enrollee.getEmail(), enrollee.getFirstName(), enrollee.getLastName(),enrollee.getLastName(),faculty.getFacultyName());
                }
            }else{
               int index = 0;
                for(Map.Entry<Enrollee,Integer> entry : allEnrollee.entrySet()){
                    Enrollee enrollee = entry.getKey();
                    if(index < numbersOfMaxStudent){
                        MessageCreator.writeSuccessfullEnrollment(enrollee.getEmail(), enrollee.getFirstName(), enrollee.getLastName(),enrollee.getLastName(),faculty.getFacultyName());
                    }else{
                        facultyDao.deleteUserFromStatement(enrollee.getUserID(),statementID);
                        MessageCreator.writeUnSuccessfullyEnrollment(enrollee.getEmail(), enrollee.getFirstName(), enrollee.getLastName(),enrollee.getLastName(),faculty.getFacultyName());
                    }
                    index++;
                }
            }
            remainder = getAllEnrolleSignedToFaculty(facultyID);
        } catch (DaoException e) {
            throw new ServiceException("Exception while closing statement",e);
        }
        return remainder;
    }

    @Override
    public boolean finallyCloseStatement(int facultyID, HashMap<Integer, Boolean> finalUsers) throws ServiceException{
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        EnrolleeDao enrolleeDao = DaoFactory.getInstance().getEnrolleeDao();
        try{
            if(!facultyDao.isFacultyHasStatement(facultyID)){
                throw new ImpracticableActionException("Faculty hasn't statement");
            }
            if(facultyDao.getExpiredDateOfStatement(facultyID).after(Date.valueOf(LocalDate.now()))){
                throw new ImpracticableActionException("Statement isn't expired yet");
            }
            int statementID = facultyDao.getStatementIDByFacultyID(facultyID);
            int numberOfStudents = facultyDao.getAllUsersIDFromStatement(statementID).size();
            int numberOfFalseAnswer = numberOfStudents - facultyDao.getNumberOfMaxStudentsInStatement(facultyID);
            int counterOfFalseStatememnt = 0;
            for(Map.Entry<Integer, Boolean> entry : finalUsers.entrySet()){
                if(!entry.getValue()){
                    counterOfFalseStatememnt++;
                }
            }
            if(counterOfFalseStatememnt < numberOfFalseAnswer){
                throw new NotValidDataException("We cannot apply too much students for faculty");
            }
            Faculty faculty = facultyDao.getFacultyById(facultyID);
            for(Map.Entry<Integer, Boolean> entry : finalUsers.entrySet()){
                int enrolleeID = entry.getKey();
                Enrollee enrollee = enrolleeDao.getEnrolleeByID(enrolleeID);
                if(entry.getValue()){
                    MessageCreator.writeSuccessfullEnrollment(enrollee.getEmail(), enrollee.getFirstName(), enrollee.getLastName(),enrollee.getLastName(),faculty.getFacultyName());
                }else{
                    facultyDao.deleteUserFromStatement(enrolleeID,statementID);
                    MessageCreator.writeUnSuccessfullyEnrollment(enrollee.getEmail(), enrollee.getFirstName(), enrollee.getLastName(),enrollee.getLastName(),faculty.getFacultyName());
                }
            }
        }catch (DaoException e){
            throw new ServiceException("Exception while finally closing statement",e);
        }
        return true;
    }


}
