package by.sanko.selectioncommittee.service;

import by.sanko.selectioncommittee.entity.Enrollee;
import by.sanko.selectioncommittee.entity.Exam;
import by.sanko.selectioncommittee.entity.Faculty;
import by.sanko.selectioncommittee.exception.DaoException;
import by.sanko.selectioncommittee.exception.ServiceException;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface FacultyService {
    List<Faculty> getAllFaculties() throws ServiceException;

    boolean registerAdmin(int userID,String code) throws ServiceException;

    Faculty getFacultyByAdminID(int userID) throws ServiceException;

    boolean addExamToFaculty(Exam exam, int facultyID, int minimalScore) throws ServiceException;

    Map<Exam, Integer> getAllExams(int facultyID) throws ServiceException;

    boolean deleteExamFromFaculty(Exam exam, int facultyID) throws ServiceException;

    boolean addStatementToFaculty(int facultyID, int adminID,String expiredAt, int numberOfMaxStudents) throws ServiceException;

    boolean updateStatement(int numberOfMaxStudents, String closeAt, int facultyID, int adminID) throws ServiceException;

    boolean deleteStatement(int facultyID, int adminID) throws ServiceException;

    boolean isStatementExist(int facultyID) throws ServiceException;

    Faculty getFacultyByID(int facultyID) throws ServiceException;

    Faculty getFacultyByName(String facultyName) throws ServiceException;

    Map<Enrollee, Integer> getAllEnrolleSignedToFaculty(int facultyID) throws ServiceException;

    int calculateMinimalScoreToEnrollInFaculty(int facultyID) throws ServiceException;

    HashMap<Enrollee, Integer> closeStatement(int facultyID) throws ServiceException;

    boolean finallyCloseStatement(int facultyID, HashMap<Integer,Boolean> finalUsers) throws ServiceException;
}
