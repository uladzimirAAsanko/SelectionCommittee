package by.sanko.selectioncommittee.service;

import by.sanko.selectioncommittee.entity.AuthorizationData;
import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    public boolean registerUser(String data) throws ServiceException;

    public User authorizeUser(AuthorizationData data) throws ServiceException, UserNotFoundException;

    public String encryptPassword(String login,String password) throws ServiceException;
}
