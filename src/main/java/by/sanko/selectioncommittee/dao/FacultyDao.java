package by.sanko.selectioncommittee.dao;

import by.sanko.selectioncommittee.entity.Enrollee;
import by.sanko.selectioncommittee.entity.Exam;
import by.sanko.selectioncommittee.entity.Faculty;
import by.sanko.selectioncommittee.exception.DaoException;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

public interface FacultyDao {
    List<Faculty> getAllSitesOfFaculties() throws DaoException;

    boolean registerAdmin(int userID,String adminCode) throws DaoException;

    Faculty getFacultyById(int facultyID) throws DaoException;

    boolean addAdminCode(int facultyID, String code) throws DaoException;

    boolean deleteAdminCode(String code) throws DaoException;

    int getFacultyIdByUserID(int userID) throws DaoException;

    boolean addExamToFaculty(Exam exam, int facultyID, int minimalScore) throws DaoException;

    int getNumberOfExamsOfFaculty(int facultyID) throws DaoException;

    boolean isFacultyHasExam(Exam exam, int facultyID) throws DaoException;

    HashMap<Exam, Integer> getAllExams(int facultyID) throws DaoException;

    boolean deleteExam(Exam exam, int facultyID) throws DaoException;

    boolean addStatementOfFaculty(int facultyID,int adminID, Date createdAt,Date expiredAt, int numberOfMaxStudents) throws DaoException;

    boolean isFacultyHasStatement(int facultyID) throws DaoException;

    boolean updateStatement(int numberOfMaxStudents, Date closeAt, int facultyID, int adminID) throws DaoException;

    boolean deleteStatement(int facultyID, int adminID) throws DaoException;

    int getNumberOfMaxStudentsInStatement(int facultyID) throws DaoException;

    Date getExpiredDateOfStatement(int facultyID) throws  DaoException;

    int getStatementIDByFacultyID(int facultyID) throws DaoException;

    boolean deleteUserFromStatement(int userId, int statementID) throws DaoException;

    List<Integer> getAllUsersIDFromStatement(int statementID) throws DaoException;

    int getMinimalScoreOfThisExamByFaculty(Exam exam, int facultyID) throws DaoException;

    boolean addFaculty(String facultyName, String facultySite) throws DaoException;

    Faculty getFacultyByName(String facultyName) throws DaoException;
}
