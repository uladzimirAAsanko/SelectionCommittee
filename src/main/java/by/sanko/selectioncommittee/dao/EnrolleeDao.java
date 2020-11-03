package by.sanko.selectioncommittee.dao;

import by.sanko.selectioncommittee.entity.Exam;
import by.sanko.selectioncommittee.exception.DaoException;

import java.util.List;

public interface EnrolleeDao {
    boolean registration(int idUser, int certificate,String additionalInfo) throws DaoException;

    boolean addExam(int userID, Exam exam, int result) throws DaoException;

    List<String> getAllExams(int userID) throws DaoException;
}
