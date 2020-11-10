package by.sanko.selectioncommittee.service;

import by.sanko.selectioncommittee.entity.Faculty;
import by.sanko.selectioncommittee.exception.ServiceException;

import java.util.List;

public interface FacultyService {
    List getAllFaculties() throws ServiceException;

    String transformListToString(List<Faculty> faculties);

    boolean registerAdmin(int userID,String code) throws ServiceException;

    Faculty getFacultyByAdminID(int userID) throws ServiceException;
}
