package by.sanko.selectioncommittee.service;

import by.sanko.selectioncommittee.exception.ServiceException;

import java.util.HashMap;
import java.util.List;

public interface EnrolleeService {
    HashMap<String,Boolean> addExam(int userID, int indexOfExam, String result) throws ServiceException;

    boolean registerUser(int idUser, String certificate,String additionalInfo) throws ServiceException;

    List<String> getAllExams(int userID) throws ServiceException;
}
