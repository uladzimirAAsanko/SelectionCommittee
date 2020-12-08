package by.sanko.selectioncommittee.dao;

import by.sanko.selectioncommittee.entity.AuthorizationData;
import by.sanko.selectioncommittee.entity.RegistrationData;
import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.entity.UserStatus;
import by.sanko.selectioncommittee.exception.DaoException;

public interface UserDao {

    User authorization(AuthorizationData data) throws DaoException;

    boolean registration(RegistrationData data) throws DaoException;

    boolean isUserRegistered(String login) throws DaoException;

    boolean isEmailRegistered(String email) throws DaoException;

    String getHashedPasswordByLogin(String login) throws DaoException;

    User getUserByID(int id) throws DaoException;

    boolean addPhotoToUser(int id, String photoDir) throws DaoException;

    String getUserPhoto(int id) throws DaoException;

    boolean updateUserInfo(String firstName, String lastName, String fathersName, int userID) throws DaoException;

    boolean changeUserPassword(String password,String userLogin) throws DaoException;

    User getUserByLogin(String login) throws DaoException;

    User getUserByEmail(String email) throws DaoException;

    boolean changeUserStatus(UserStatus status, int userID) throws DaoException;

    boolean changeUserStatus(UserStatus status, String login) throws DaoException;


}
