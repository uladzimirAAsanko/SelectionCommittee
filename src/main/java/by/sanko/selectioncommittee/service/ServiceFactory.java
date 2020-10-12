package by.sanko.selectioncommittee.service;


import by.sanko.selectioncommittee.service.impl.FacultyServiceImpl;
import by.sanko.selectioncommittee.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final UserService userService = new UserServiceImpl();
    private final FacultyService facultyService = new FacultyServiceImpl();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public FacultyService getFacultyService() {
        return facultyService;
    }
}