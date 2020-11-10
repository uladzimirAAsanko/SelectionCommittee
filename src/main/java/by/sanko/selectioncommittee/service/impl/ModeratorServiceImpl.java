package by.sanko.selectioncommittee.service.impl;

import by.sanko.selectioncommittee.dao.DaoFactory;
import by.sanko.selectioncommittee.dao.FacultyDao;
import by.sanko.selectioncommittee.exception.DaoException;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.ModeratorService;


public class ModeratorServiceImpl implements ModeratorService {

    @Override
    public boolean addAdminCode(String code, int facultyID) throws ServiceException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        boolean isAdded = false;
        try {
            isAdded = facultyDao.addAdminCode(facultyID,code);
        } catch (DaoException e) {
            throw new ServiceException("Exception while adding admin code",e);
        }
        return isAdded;
    }
}
