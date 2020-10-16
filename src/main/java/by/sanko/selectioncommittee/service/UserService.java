package by.sanko.selectioncommittee.service;

import by.sanko.selectioncommittee.entity.AuthorizationData;
import by.sanko.selectioncommittee.entity.RegistrationData;
import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.exception.ServiceException;

public interface UserService {

    public boolean registerUser(RegistrationData data) throws ServiceException;

    public User authorizeUser(AuthorizationData data) throws ServiceException;
}
