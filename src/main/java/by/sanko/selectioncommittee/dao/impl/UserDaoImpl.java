package by.sanko.selectioncommittee.dao.impl;

import by.sanko.selectioncommittee.dao.UserDao;
import by.sanko.selectioncommittee.dao.pool.ConnectionPool;
import by.sanko.selectioncommittee.entity.*;
import by.sanko.selectioncommittee.exception.DaoException;
import by.sanko.selectioncommittee.exception.DaoImpl.LoginIsBusyException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private static UserDaoImpl userDao;
    private static final String SELECT_USER_BY_LOGIN = "SELECT users.first_name, users.last_name, users.fathers_name, users.login,users.email,roles.id_role,users.idusers,users.avatar_picture,users.id_status FROM users JOIN roles ON roles.id_role=users.id_role WHERE login=?;";
    private static final String SELECT_USER_BY_EMAIL = "SELECT users.first_name, users.last_name, users.fathers_name, users.login,users.email,roles.id_role,users.idusers,users.avatar_picture,users.id_status FROM users JOIN roles ON roles.id_role=users.id_role WHERE email=?";
    private static final String ADD_USER = "INSERT users(first_name, last_name, fathers_name, login, password, email, id_role, id_status) VALUES(?,?,?,?,?,?,?,?);";
    private static final String SELECT_PASSWORD_BY_LOGIN = "SELECT users.password FROM users WHERE login=?;";
    private static final String SELECT_USER_BY_ID = "SELECT users.first_name, users.last_name, users.fathers_name, users.login,users.email,roles.id_role,users.idusers,users.avatar_picture,users.id_status FROM users JOIN roles ON roles.id_role=users.id_role WHERE idusers=?";
    private static final String UPDATE_PHOTO_OF_USER = "UPDATE users SET mydb.users.avatar_picture = ? WHERE idusers = ?;";
    private static final String GET_PHOTO_OF_USER = "SELECT avatar_picture FROM users WHERE idusers = ?;";
    private static final String UPDATE_USER_INFO = "UPDATE users SET first_name=?, last_name = ?, fathers_name = ? WHERE idusers = ?";
    private static final String UPDATE_USER_STATUS = "UPDATE users SET id_status=? WHERE idusers = ?";
    private static final String UPDATE_USER_STATUS_BY_LOGIN = "UPDATE users SET id_status=? WHERE login = ?";
    private static final String CHANGE_USER_PHOTO = "UPDATE users SET password=? WHERE login = ?;";
    private static final String CHANGE_USER_ROLE = "UPDATE users SET id_role=? WHERE idusers = ?;";

    public static UserDaoImpl getInstance() {
        if (userDao == null) {
            userDao = new UserDaoImpl();
        }

        return userDao;
    }

    @Override
    public User authorization(AuthorizationData data) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        ResultSet resultSet = null;
        User user = null;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_LOGIN)){
            statement.setString(1, data.getLogin());
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                String login = data.getLogin();
                int id = resultSet.getInt(7);
                String firstName = resultSet.getString(1);
                String lastName = resultSet.getString(2);
                String fathersName = resultSet.getString(3);
                String email = resultSet.getString(5);
                String photo = resultSet.getString(8);
                UsersRole role = UsersRole.values()[resultSet.getInt(6)];
                UserStatus status = UserStatus.values()[resultSet.getInt(9)];
                user = new User(id,firstName,lastName,fathersName,login,email, role,photo,status);
            }
        }catch (SQLException exception){
            throw new DaoException("Exception while authorization",exception);
        }
        return user;
    }

    @Override
    public boolean registration(RegistrationData data) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        boolean isUserRegistered = false;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_USER);){
            statement.setString(1,data.getFirstName());
            statement.setString(2,data.getLastName());
            statement.setString(3,data.getFathersName());
            statement.setString(4,data.getLogin());
            statement.setString(5,data.getPassword());
            statement.setString(6,data.getEmail());
            statement.setInt(7,data.getRole());
            statement.setInt(8,1);
            isUserRegistered = statement.executeUpdate() > 0;;
        }catch (SQLException e){
            throw new DaoException("Error while registrarion",e);
        }
        return isUserRegistered;
    }

    @Override
    public boolean isUserRegistered(String login) throws DaoException {
        return findUser(login, SELECT_USER_BY_LOGIN);
    }

    @Override
    public boolean isEmailRegistered(String email) throws DaoException {
        return findUser(email, SELECT_USER_BY_EMAIL);
    }

    @Override
    public String getHashedPasswordByLogin(String login) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        String hashedPassword = "";
        ResultSet resultSet = null;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_PASSWORD_BY_LOGIN);){
            statement.setString(1,login);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                hashedPassword = resultSet.getString(1);
            }
        } catch (SQLException exception) {
            throw new DaoException("Error while getting password by login",exception);
        }
        return hashedPassword;
    }

    @Override
    public User getUserByID(int id) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        ResultSet resultSet = null;
        User user = null;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID)){
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            user = getUser(resultSet);
        }catch (SQLException exception){
            throw new DaoException("Exception while authorization",exception);
        }
        return user;
    }

    @Override
    public boolean addPhotoToUser(int id, String photoDir) throws DaoException {
        boolean isAdded = false;
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_PHOTO_OF_USER)){
            statement.setInt(2,id);
            statement.setString(1,photoDir);
            isAdded = statement.executeUpdate() > 0;
        }catch (SQLException e){
            throw new DaoException("Exception while updating photo of user",e);
        }
        return isAdded;
    }

    @Override
    public String getUserPhoto(int id) throws DaoException {
        String userPhoto = "";
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        ResultSet resultSet = null;
        try (Connection connection = instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PHOTO_OF_USER)){
            statement.setInt(1,id);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                userPhoto = resultSet.getString(1);
            }
        }catch (SQLException e){
            throw new DaoException("Exception while getting user avatar",e);
        }
        return userPhoto;
    }

    @Override
    public boolean updateUserInfo(String firstName, String lastName, String fathersName, int userID) throws DaoException {
        boolean isUpdated = false;
        User user = getUserByID(userID);
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        try (Connection connection = instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_INFO)) {
            if(firstName == null || firstName.isEmpty()){
                firstName = user.getFirstName();
            }
            if(lastName == null || lastName.isEmpty()){
                lastName = user.getFirstName();
            }
            if(fathersName == null || fathersName.isEmpty()){
                fathersName = user.getFirstName();
            }
            statement.setString(1,firstName);
            statement.setString(2,lastName);
            statement.setString(3,fathersName);
            statement.setInt(4,userID);
            isUpdated = statement.executeUpdate() > 0;
        }catch (SQLException e){
            throw new DaoException("Exception while updating user information",e);
        }

        return isUpdated;
    }

    @Override
    public boolean changeUserPassword(String password,String userLogin) throws DaoException {
        boolean isUpdated = false;
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        try (Connection connection = instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHANGE_USER_PHOTO)) {
            statement.setString(1,password);
            statement.setString(2,userLogin);
            isUpdated = statement.executeUpdate() > 0;
        }catch (SQLException e){
            throw new DaoException("Exception while changing user password",e);
        }
        return isUpdated;
    }

    @Override
    public User getUserByLogin(String login) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        ResultSet resultSet = null;
        User user = null;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_LOGIN)){
            statement.setString(1,login);
            resultSet = statement.executeQuery();
            user = getUser(resultSet);
        }catch (SQLException e){
            throw new DaoException("Exception while getting user by login",e);
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        ResultSet resultSet = null;
        User user = null;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_EMAIL)){
            statement.setString(1,email);
            resultSet = statement.executeQuery();
            user = getUser(resultSet);
        }catch (SQLException e){
            throw new DaoException("Exception while getting user by login",e);
        }
        return user;
    }

    @Override
    public boolean changeUserStatus(UserStatus status, int userID) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        boolean isChanged = false;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER_STATUS)){
            statement.setInt(2,userID);
            statement.setInt(1,status.ordinal());
            isChanged = statement.executeUpdate() > 0;
        }catch (SQLException e){
            throw new DaoException("Exception while updating userStatus",e);
        }
        return isChanged;
    }

    @Override
    public boolean changeUserStatus(UserStatus status, String login) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        boolean isChanged = false;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER_STATUS_BY_LOGIN)){
            statement.setString(2,login);
            statement.setInt(1,status.ordinal());
            isChanged = statement.executeUpdate() > 0;
        }catch (SQLException e){
            throw new DaoException("Exception while updating userStatus",e);
        }
        return isChanged;
    }

    @Override
    public boolean changeUserRole(UsersRole role, int userID) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        boolean isChanged = false;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(CHANGE_USER_ROLE)){
            statement.setInt(2,userID);
            statement.setInt(1,role.ordinal());
            isChanged = statement.executeUpdate() > 0;
        }catch (SQLException e){
            throw new DaoException("Exception while updating userStatus",e);
        }
        return isChanged;
    }


    private boolean findUser(String data, String selectUserByEmail) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        ResultSet resultSet = null;
        boolean isFinded = false;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(selectUserByEmail)){
            statement.setString(1, data);
            resultSet = statement.executeQuery();
            isFinded = resultSet.next();
        }catch (SQLException exception){
            throw new DaoException("Exception while authorization",exception);
        }
        return isFinded;
    }

    private User getUser(ResultSet resultSet)throws SQLException, DaoException{
        User user = null;
        while(resultSet.next()){
            String login = resultSet.getString(4);
            String firstName = resultSet.getString(1);
            String lastName = resultSet.getString(2);
            String fathersName = resultSet.getString(3);
            String email = resultSet.getString(5);
            UsersRole role = UsersRole.values()[resultSet.getInt(6)];
            int id = resultSet.getInt(7);
            String photo = resultSet.getString(8);
            UserStatus status = UserStatus.values()[resultSet.getInt(9)];
            user = new User(id,firstName,lastName,fathersName,login,email,role,photo,status);
        }
        return user;
    }

}
