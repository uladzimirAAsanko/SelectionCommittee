package by.sanko.selectioncommittee.service.impl;

import by.sanko.selectioncommittee.dao.DaoFactory;
import by.sanko.selectioncommittee.dao.UserDao;
import by.sanko.selectioncommittee.entity.AuthorizationData;
import by.sanko.selectioncommittee.entity.RegistrationData;
import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.entity.UserStatus;
import by.sanko.selectioncommittee.exception.*;
import by.sanko.selectioncommittee.exception.DaoImpl.LoginIsBusyException;
import by.sanko.selectioncommittee.exception.ServiceImpl.NotValidDataException;
import by.sanko.selectioncommittee.exception.ServiceImpl.RegistrationException;
import by.sanko.selectioncommittee.service.AuthenticationService;
import by.sanko.selectioncommittee.service.ServiceFactory;
import by.sanko.selectioncommittee.service.UserService;
import by.sanko.selectioncommittee.util.mail.LinkGenerator;
import by.sanko.selectioncommittee.util.mail.MessageCreator;
import by.sanko.selectioncommittee.util.security.BCryptHash;
import by.sanko.selectioncommittee.util.validator.UserValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserServiceImpl implements UserService {
    private static final String ANSWER_OF_MAP_ALL_GOOD = "Password is successfully changed";
    private static final String ANSWER_OF_MAP_PASSWORD_IS_NOT_VALID = "New password is not valid";
    private static final String ANSWER_OF_MAP_PASSWORDS_ARE_SAME = "Old and new passwords are same";
    private static final String ANSWER_OF_MAP_DATA_IS_NOT_VALID = "Invalid data";
    private static final String ANSWER_OF_MAP_PASSWORD_IS_NOT_CORRECT = "Old password is not correct";
    private static final String USER_DEFAULT_PICUTRE = "uploadDir/defaultAvatar.jpg";
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @Override
    public boolean registerUser(RegistrationData data) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        UserValidator  validator = UserValidator.getInstance();

        if(!validator.validateLogin(data.getLogin()) || !validator.validatePassword(data.getPassword())){
            throw new NotValidDataException("Not valid login or password");
        }
        boolean answer = false;
        try {
            if(userDao.isEmailRegistered(data.getEmail())){
                throw new NotValidDataException("Email is already register");
            }
            if(userDao.isUserRegistered(data.getLogin())){
                throw new NotValidDataException("Login is already register");
            }
            String newPassword = BCryptHash.hashPassword( data.getPassword());
            data.setPassword(newPassword);
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
                userDao.changeUserStatus(UserStatus.ACTIVE,user.getUserID());
            }
            if(user != null){
                if(user.getAvatarDir() == null || !user.getAvatarDir().isEmpty()){
                    user.setAvatarDir(USER_DEFAULT_PICUTRE);
                }
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
        }catch (DaoException e) {
            throw new ServiceException("Exception while getting user by ID",e);
        }
        return user;
    }

    @Override
    public boolean addPhotoToUser(int id, String photoDir) throws ServiceException {
        boolean isAdded = false;
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            isAdded = userDao.addPhotoToUser(id,photoDir);
        }catch (DaoException e){
            throw new ServiceException("Exception while updating user avatar",e);
        }
        return isAdded;
    }

    @Override
    public String getUserPhoto(int id) throws ServiceException {
        String answer = "";
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try{
            answer = userDao.getUserPhoto(id);
        }catch (DaoException e){
            throw new ServiceException("Exception while getting user avatar",e);
        }
        return answer;
    }

    @Override
    public boolean updateUserInfo(String firstName, String lastName, String fathersName, int userID) throws ServiceException {
        boolean isUpdated = false;
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            isUpdated = userDao.updateUserInfo(firstName,lastName,fathersName,userID);
        } catch (DaoException e) {
            throw new ServiceException("Exception while updating user information",e);
        }
        return isUpdated;
    }

    @Override
    public String updateUserPassword(String currentPassword, String newPassword, String userLogin) throws ServiceException {
        if(currentPassword.equals(newPassword)){
            return ANSWER_OF_MAP_PASSWORDS_ARE_SAME;
        }
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        UserValidator  validator = UserValidator.getInstance();
        if(!validator.validatePassword(newPassword)){
            return ANSWER_OF_MAP_PASSWORD_IS_NOT_VALID;
        }
        String answer = "";
        try {
            String hashedPassword = userDao.getHashedPasswordByLogin(userLogin);
            if(BCryptHash.checkPassword(currentPassword,hashedPassword)){
                String hashedNewPassword = BCryptHash.hashPassword(newPassword);
                if(userDao.changeUserPassword(hashedNewPassword,userLogin)){
                    userDao.changeUserStatus(UserStatus.CHANGED_PASSWORD, userLogin);
                    answer = ANSWER_OF_MAP_ALL_GOOD;
                }else{
                    answer = ANSWER_OF_MAP_DATA_IS_NOT_VALID;
                }
            }else{
                answer = ANSWER_OF_MAP_PASSWORD_IS_NOT_CORRECT;
            }
        } catch (DaoException | IllegalArgumentException e) {
            throw new ServiceException("Exception while authorizing user",e);
        }
        return answer;
    }

    @Override
    public boolean changeUserPassword(String data) throws ServiceException {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(data);
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        User user = null;
        boolean isChanged = false;
        try {
            if (data.endsWith("@gmail.com")) {
                user = userDao.getUserByEmail(data);
            } else {
                user = userDao.getUserByLogin(data);
            }
            if(user != null){
                String email = user.getEmail();
                String firstName = user.getFirstName();
                String lastName = user.getLastName();
                String fathersName = user.getFathersName();
                AuthenticationService authenticationService = ServiceFactory.getInstance().getAuthenticationService();
                String token = authenticationService.createChangingPassToken(user);
                String link = LinkGenerator.generateChangingPasswordLink(token);
                if(MessageCreator.writeChangePasswordMessage(email,firstName,lastName,fathersName,link)){
                        isChanged = true;
                }
            }
        }catch (DaoException e){
            throw new ServiceException("Exception while changing user password", e);
        }
        return isChanged;
    }

    @Override
    public String setUserPassword(String password, int userID) throws ServiceException {
        UserValidator  validator = UserValidator.getInstance();
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        if(!validator.validatePassword(password)){
            return ANSWER_OF_MAP_PASSWORD_IS_NOT_VALID;
        }
        String answer = "";
        try{
            String hashedPassword = BCryptHash.hashPassword(password);
            String login = userDao.getUserByID(userID).getLogin();
            if(userDao.changeUserPassword(hashedPassword,login) && userDao.changeUserStatus(UserStatus.CHANGED_PASSWORD,userID)){

                answer = ANSWER_OF_MAP_ALL_GOOD;
            }else{
                answer = ANSWER_OF_MAP_PASSWORD_IS_NOT_CORRECT;
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception while changing user password", e);
        }

        return answer;
    }
}
