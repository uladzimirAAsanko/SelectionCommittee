package by.sanko.selectioncommittee.service;

import by.sanko.selectioncommittee.entity.AuthorizationData;
import by.sanko.selectioncommittee.entity.RegistrationData;
import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.exception.ServiceException;

public interface UserService {

    boolean registerUser(RegistrationData data) throws ServiceException;

    User authorizeUser(AuthorizationData data) throws ServiceException;

    User getUserByID(int id) throws ServiceException;

}
