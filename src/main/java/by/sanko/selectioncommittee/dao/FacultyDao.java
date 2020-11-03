package by.sanko.selectioncommittee.dao;

import by.sanko.selectioncommittee.entity.Faculty;
import by.sanko.selectioncommittee.exception.DaoException;

import java.util.ArrayList;
import java.util.List;

public interface FacultyDao {
    public List<Faculty> getAllSitesOfFaculties() throws DaoException;

}
