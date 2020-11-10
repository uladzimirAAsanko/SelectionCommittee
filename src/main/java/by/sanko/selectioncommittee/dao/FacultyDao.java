package by.sanko.selectioncommittee.dao;

import by.sanko.selectioncommittee.entity.Faculty;
import by.sanko.selectioncommittee.exception.DaoException;

import java.util.List;

public interface FacultyDao {
    List<Faculty> getAllSitesOfFaculties() throws DaoException;

    boolean registerAdmin(int userID,String adminCode) throws DaoException;

    Faculty getFacultyById(int facultyID) throws DaoException;

    boolean addAdminCode(int facultyID, String code) throws DaoException;

    boolean deleteAdminCode(String code) throws DaoException;

    int getFacultyIdByUserID(int userID) throws DaoException;
}
