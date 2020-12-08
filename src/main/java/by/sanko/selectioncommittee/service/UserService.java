package by.sanko.selectioncommittee.service;

import by.sanko.selectioncommittee.entity.AuthorizationData;
import by.sanko.selectioncommittee.entity.RegistrationData;
import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.exception.ServiceException;

public interface UserService {

    boolean registerUser(RegistrationData data) throws ServiceException;

    User authorizeUser(AuthorizationData data) throws ServiceException;

    User getUserByID(int id) throws ServiceException;

    boolean addPhotoToUser(int id, String photoDir) throws ServiceException;

    String getUserPhoto(int id) throws ServiceException;

    boolean updateUserInfo(String firstName, String lastName, String fathersName, int userID) throws ServiceException;

    String updateUserPassword(String currentPassword, String newPassword, String userLogin) throws ServiceException;

    boolean changeUserPassword(String data) throws ServiceException;

    String setUserPassword(String password, int userID) throws ServiceException;
}
