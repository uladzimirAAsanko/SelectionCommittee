package by.sanko.selectioncommittee.service.impl;

import by.sanko.selectioncommittee.dao.DaoFactory;
import by.sanko.selectioncommittee.dao.FacultyDao;
import by.sanko.selectioncommittee.entity.Faculty;
import by.sanko.selectioncommittee.exception.DaoException;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.FacultyService;

import java.util.List;

public class FacultyServiceImpl implements FacultyService {
    private static final String TR = "<tr>";
    private static final String TR_CLOSE = "</tr>";
    private static final String TD_CLOSE = "</td>";
    private static final String TD = "<td>";

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

    @Override
    public String transformListToString(List<Faculty> faculties){
        StringBuilder builder = new StringBuilder();
        for(Faculty faculty: faculties){
            builder.append(TR).append(TD);
            builder.append(faculty.getFacultyName());
            builder.append(TD_CLOSE).append(TD);
            builder.append(faculty.getFacultySite());
            builder.append(TD_CLOSE).append(TR_CLOSE);
        }
        return builder.toString();
    }

    @Override
    public boolean registerAdmin(int userID,String code) throws ServiceException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        Faculty faculty = null;
        boolean isRegister = false;
        try {
            isRegister =  facultyDao.registerAdmin(userID,code);
        } catch (DaoException e) {
            throw new ServiceException("Exception while checking admins code",e);
        }
        return isRegister;
    }

    @Override
    public Faculty getFacultyByAdminID(int userID) throws ServiceException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        Faculty faculty = null;
        int facultyID = -1;
        try {
            facultyID = facultyDao.getFacultyIdByUserID(userID);
            faculty = facultyDao.getFacultyById(facultyID);
        } catch (DaoException e) {
            throw new ServiceException("Exception while getting faculty by admins code", e);
        }
        return faculty;
    }
}
