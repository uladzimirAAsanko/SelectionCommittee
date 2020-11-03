package by.sanko.selectioncommittee.dao;

import by.sanko.selectioncommittee.entity.AuthorizationData;
import by.sanko.selectioncommittee.entity.RegistrationData;
import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.exception.DaoException;

public interface UserDao {

    User authorization(AuthorizationData data) throws DaoException;

    boolean registration(RegistrationData data) throws DaoException;

    boolean findUserByLogin(String data) throws DaoException;

    boolean findUserEmail(String data) throws DaoException;

    String getHashedPasswordByLogin(String login) throws DaoException;

    User getUserByID(int id) throws DaoException;
}
