package by.sanko.selectioncommittee.dao.impl;

import by.sanko.selectioncommittee.dao.UserDao;
import by.sanko.selectioncommittee.dao.pool.ConnectionPool;
import by.sanko.selectioncommittee.entity.AuthorizationData;
import by.sanko.selectioncommittee.entity.RegistrationData;
import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.entity.UsersRole;
import by.sanko.selectioncommittee.exception.DaoException;
import by.sanko.selectioncommittee.exception.LoginIsBusyException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private static final String SELECT_USER_BY_LOGIN = "SELECT users.first_name, users.last_name, users.fathers_name, users.login,users.password,users.email,roles.id_role,users.idusers FROM users JOIN roles ON roles.id_role=users.id_role WHERE login=?";
    private static final String SELECT_USER_BY_EMAIL = "SELECT users.first_name, users.last_name, users.fathers_name, users.login,users.password,users.email,roles.id_role,users.idusers FROM users JOIN roles ON roles.id_role=users.id_role WHERE email=?";
    private static final  String ADD_USER = "INSERT users(first_name, last_name, fathers_name, login, password, email, id_role) VALUES(?,?,?,?,?,?,?);";
    private static final String  SELECT_PASSWORD_BY_LOGIN = "SELECT users.password FROM users WHERE login=?;";

    @Override
    public User authorization(AuthorizationData data) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        Connection connection = instance.getConnection();
        ResultSet resultSet = null;
        User user = null;
        try(PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_LOGIN)){
            statement.setString(1, data.getLogin());
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                String login = data.getLogin();
                int id = resultSet.getInt(8);
                String firstName = resultSet.getString(1);
                String lastName = resultSet.getString(2);
                String fathersName = resultSet.getString(3);
                String password = resultSet.getString(5);
                String email = resultSet.getString(6);
                UsersRole role = UsersRole.values()[resultSet.getInt(7)];
                user = new User(id,firstName,lastName,fathersName,login,password,email,role);
            }
        }catch (SQLException exception){
            throw new DaoException("Exception while authorization",exception);
        }
        return user;
    }

    @Override
    public boolean registration(RegistrationData data) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_USER);){
            statement.setString(1,data.getFirstName());
            statement.setString(2,data.getLastName());
            statement.setString(3,data.getFathersName());
            statement.setString(4,data.getLogin());
            statement.setString(5,data.getPassword());
            statement.setString(6,data.getEmail());
            statement.setInt(7,data.getRole());
            if(findUserByLogin(data.getLogin())){
                throw new LoginIsBusyException("Login is Busy");
            }
            if(findUserEmail(data.getEmail())){
                throw new LoginIsBusyException("Email is Busy");
            }
            statement.execute();
        }catch (SQLException e){
            throw new DaoException("Error while registrarion",e);
        }
        return findUserByLogin(data.getLogin());
    }

    @Override
    public boolean findUserByLogin(String data) throws DaoException {
        return findUser(data, SELECT_USER_BY_LOGIN);
    }

    @Override
    public boolean findUserEmail(String data) throws DaoException {
        return findUser(data, SELECT_USER_BY_EMAIL);
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

    private boolean findUser(String data, String selectUserByEmail) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        Connection connection = instance.getConnection();
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(selectUserByEmail);
            statement.setString(1, data);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        }catch (SQLException exception){
            throw new DaoException("Exception while authorization",exception);
        }
    }

}
