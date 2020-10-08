package by.sanko.selectioncommittee.dao;

import by.sanko.selectioncommittee.dao.impl.FacultyDaoImpl;
import by.sanko.selectioncommittee.dao.impl.UserDaoImpl;

public class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();

    private final UserDao userDao = new UserDaoImpl();
    private final FacultyDao facultyDao = new FacultyDaoImpl();

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public FacultyDao getFacultyDao() {
        return facultyDao;
    }
}