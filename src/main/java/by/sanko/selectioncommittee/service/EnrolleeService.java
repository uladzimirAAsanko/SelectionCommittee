package by.sanko.selectioncommittee.service;

import by.sanko.selectioncommittee.entity.Enrollee;
import by.sanko.selectioncommittee.entity.Exam;
import by.sanko.selectioncommittee.entity.Faculty;
import by.sanko.selectioncommittee.exception.DaoException;
import by.sanko.selectioncommittee.exception.ServiceException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface EnrolleeService {
    Enrollee getEnrolleeByID(int enrolleeID) throws ServiceException;

    boolean addExam(int userID,Exam exam, String resultOfExam) throws ServiceException;

    boolean removeExam(int userID, Exam exam) throws ServiceException;

    boolean registerUser(int idUser, String certificate,String additionalInfo) throws ServiceException;

    Map<Exam,Integer> getAllExams(int userID) throws ServiceException;

    int getNumberOfProfileExams(int userID) throws ServiceException;

    Map<Faculty,Integer> getAllFaculties() throws ServiceException;

    Map<Faculty,Integer> getFacultiesAvailableForUser(int userID) throws ServiceException;

    boolean signToFaculty(int userID, int facultyID) throws ServiceException;

    boolean unSignFromFaculty(int userID, int facultyID) throws ServiceException;

    int calculateUserScore(int userID) throws ServiceException;

    Map<Faculty,Integer> getFacultiesThatGoodToUser(int userID) throws ServiceException;
}
