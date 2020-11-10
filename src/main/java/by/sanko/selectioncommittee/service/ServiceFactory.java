package by.sanko.selectioncommittee.service;


import by.sanko.selectioncommittee.service.impl.EnrolleeServiceImpl;
import by.sanko.selectioncommittee.service.impl.FacultyServiceImpl;
import by.sanko.selectioncommittee.service.impl.ModeratorServiceImpl;
import by.sanko.selectioncommittee.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final UserService userService = new UserServiceImpl();
    private final FacultyService facultyService = new FacultyServiceImpl();
    private final EnrolleeService enrolleeService = new EnrolleeServiceImpl();
    private final ModeratorService moderatorService = new ModeratorServiceImpl();

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

    public EnrolleeService getEnrolleeService() {
        return enrolleeService;
    }

    public ModeratorService getModeratorService(){return moderatorService;}
}
