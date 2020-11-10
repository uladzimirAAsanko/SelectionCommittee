package by.sanko.selectioncommittee.service;

import by.sanko.selectioncommittee.exception.ServiceException;

public interface ModeratorService {
    boolean addAdminCode(String code, int facultyID) throws ServiceException;
}
