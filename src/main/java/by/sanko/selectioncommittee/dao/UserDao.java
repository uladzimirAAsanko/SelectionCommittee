package by.sanko.selectioncommittee.dao;

import by.sanko.selectioncommittee.entity.AuthorizationData;
import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.exception.DaoException;

public interface UserDao {

    public User authorization(AuthorizationData data) throws DaoException;

    public boolean registration(String data) throws DaoException;

    public boolean findUserByLogin(String data) throws DaoException;
}
