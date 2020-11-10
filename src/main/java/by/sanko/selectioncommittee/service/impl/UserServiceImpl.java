package by.sanko.selectioncommittee.service.impl;

import by.sanko.selectioncommittee.dao.DaoFactory;
import by.sanko.selectioncommittee.dao.UserDao;
import by.sanko.selectioncommittee.entity.AuthorizationData;
import by.sanko.selectioncommittee.entity.RegistrationData;
import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.exception.*;
import by.sanko.selectioncommittee.service.UserService;
import by.sanko.selectioncommittee.util.security.BCryptHash;
import by.sanko.selectioncommittee.util.validator.UserValidator;



public class UserServiceImpl implements UserService {
    @Override
    public boolean registerUser(RegistrationData data) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        UserValidator  validator = UserValidator.getInstance();

        if(!validator.validateLogin(data.getLogin()) || !validator.validatePassword(data.getPassword())){
            return false;
        }
        String newPassword = BCryptHash.hashPassword( data.getPassword());
        data.setPassword(newPassword);
        boolean answer = false;
        try {
            answer = userDao.registration(data);
        }catch (LoginIsBusyException e){
            throw new RegistrationException(e.getMessage());
        }catch (DaoException e) {
            throw new ServiceException("Exception while registrate user",e);
        }
        return answer;
    }

    @Override
    public User authorizeUser(AuthorizationData data) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        UserValidator  validator = UserValidator.getInstance();
        User user = null;
        if(!validator.validateLogin(data.getLogin()) || !validator.validatePassword(data.getPassword())){
          return null;
        }
        try {
            String hashedPassword = userDao.getHashedPasswordByLogin(data.getLogin());
            if(BCryptHash.checkPassword(data.getPassword(),hashedPassword)){
                user = userDao.authorization(data);
            }
        } catch (DaoException | IllegalArgumentException e) {
            throw new ServiceException("Exception while authorizing user",e);
        }
        return user;
    }

    @Override
    public User getUserByID(int id) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        User user = null;
        try {
            user = userDao.getUserByID(id);
        } catch (DaoException e) {
            throw new ServiceException("Exception while getting user by ID",e);
        }
        return user;
    }
}
