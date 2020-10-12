package by.sanko.selectioncommittee.service.impl;

import by.sanko.selectioncommittee.dao.DaoFactory;
import by.sanko.selectioncommittee.dao.FacultyDao;
import by.sanko.selectioncommittee.entity.Faculty;
import by.sanko.selectioncommittee.exception.DaoException;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.FacultyService;

import java.util.List;

public class FacultyServiceImpl implements FacultyService {

    @Override
    public List getAllFaculties() throws ServiceException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        List<Faculty> list = null;
        try{
            list = facultyDao.getAllSitesOfFaculties();
        } catch (DaoException e) {
            throw new ServiceException("Exception while getting a list of faculties",e);
        }
        return list;
    }
}
