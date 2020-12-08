package by.sanko.selectioncommittee.dao;

import by.sanko.selectioncommittee.entity.Enrollee;
import by.sanko.selectioncommittee.entity.Exam;
import by.sanko.selectioncommittee.entity.Faculty;
import by.sanko.selectioncommittee.exception.DaoException;
import by.sanko.selectioncommittee.exception.ServiceException;

import java.util.HashMap;
import java.util.List;

public interface EnrolleeDao {
    Enrollee getEnrolleeByID(int enrolleeID) throws DaoException;

    int getCertificateOfEnrollee(int enrolleeID) throws DaoException;

    boolean registration(int idUser, int certificate,String additionalInfo) throws DaoException;

    boolean addExam(int userID, Exam exam, int result) throws DaoException;

    boolean removeExam(int userID, Exam exam) throws DaoException;

    HashMap<Exam,Integer> getAllExams(int userID) throws DaoException;

    boolean isResultAlreadyAdded(int userID, int indexOfExam) throws DaoException;

    List<Faculty> getAllFaculties() throws DaoException;

    List<Faculty> getFacultiesWithExam(Exam exam) throws DaoException;

    boolean signUpToStatement(int userID, int statementID) throws DaoException;

    boolean removeUserFromStatement(int userID, int statementID) throws DaoException;

    int getStatementOfUserAttached(int userID) throws DaoException;

    boolean isUserRegisterToStatement(int userID) throws DaoException;
}
