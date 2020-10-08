package by.sanko.selectioncommittee.service.impl;

import by.sanko.selectioncommittee.dao.DaoFactory;
import by.sanko.selectioncommittee.dao.UserDao;
import by.sanko.selectioncommittee.entity.AuthorizationData;
import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.exception.DaoException;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.exception.UserNotFoundException;
import by.sanko.selectioncommittee.service.UserService;
import by.sanko.selectioncommittee.service.validator.UserValidator;

import java.util.List;
import java.util.StringTokenizer;

public class UserServiceImpl implements UserService {

    //TODO Create new class for registration data and solve 'new' problem
    @Override
    public boolean registerUser(String data) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        UserValidator  validator = UserValidator.getInstance();
        StringTokenizer tokenizer = new StringTokenizer(data);
        tokenizer.nextToken();
        tokenizer.nextToken();
        tokenizer.nextToken();
        String login = tokenizer.nextToken();
        String password = tokenizer.nextToken();
        if(!validator.validateLogin(login) || !validator.validatePassword(password)){
            throw new UserNotFoundException("Login and password not validated");
        }
        String newPassword = encryptPassword(login, password);
        data = data.replaceAll(password,newPassword);
        boolean answer = false;
        try {
            answer = userDao.registration(data);
        } catch (DaoException e) {
            throw new ServiceException("Exception while registrate user",e);
        }
        return answer;
    }

    @Override
    public User authorizeUser(AuthorizationData data) throws ServiceException, UserNotFoundException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        UserValidator  validator = UserValidator.getInstance();
        User user = null;
        if(!validator.validateLogin(data.getLogin()) || !validator.validatePassword(data.getPassword())){
          throw new UserNotFoundException("Login and password not validated");
        }
        data.setPassword(encryptPassword(data.getLogin(),data.getPassword()));
        try {
            user = userDao.authorization(data);
        } catch (DaoException e) {
            throw new ServiceException("Exception while authorizing user",e);
        }
        if(user == null){
            throw new UserNotFoundException("Incorrect username or password");
        }
        return user;
    }

    @Override
    public String encryptPassword(String login, String password) throws ServiceException {
        StringBuilder builder = new StringBuilder();
        int loginIndex = 0;
        int passwordIndex = 0;
        while(loginIndex <= login.length() || passwordIndex <= password.length()){
            char loginChar = encryptChar(login.charAt(loginIndex));
            char passwordChar = encryptChar(password.charAt(passwordIndex));
            builder.append(loginChar).append(passwordChar);
            loginIndex++;
            passwordIndex++;
        }

        String tmp = "";
        int tmpIndex = 0;
        if(login.length() < password.length()){
            tmp = password;
            tmpIndex = passwordIndex;
        }else{
            tmp = login;
            tmpIndex = loginIndex;
        }

        for(int i = tmpIndex; i <= tmp.length(); i++){
            char tmpChar = encryptChar(tmp.charAt(tmpIndex));
            builder.append(tmpChar);
        }
        return builder.toString();
    }

    private char encryptChar(char input){
        int answer = (int)input * 2 + 1;
        char output = (char)answer;
        return output;
    }

    private char decryptChar(char input){
        int answer = ((int)input - 1) / 2;
        char output = (char)answer;
        return output;
    }
}
