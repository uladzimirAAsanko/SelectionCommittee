package by.sanko.selectioncommittee.dao.impl;

import by.sanko.selectioncommittee.dao.UserDao;
import by.sanko.selectioncommittee.dao.pool.ConnectionPool;
import by.sanko.selectioncommittee.entity.AuthorizationData;
import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.entity.UsersRole;
import by.sanko.selectioncommittee.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

public class UserDaoImpl implements UserDao {
    private static final String SELECT_USER_BY_LOGIN = "SELECT users.first_name, users.last_name, users.fathers_name, users.login,users.password,users.email,roles.role_name,users.idusers FROM users JOIN roles ON roles.id_role=users.id_role WHERE login=?";
    private static final  String ADD_USER = "INSERT users(first_name, last_name, fathers_name, login, password, email, id_role) VALUES(?,?,?,?,?,?);";


    @Override
    public User authorization(AuthorizationData data) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        Connection connection = instance.getConnection();
        ResultSet resultSet = null;
        User user = null;
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_USER_BY_LOGIN);
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
                UsersRole role = UsersRole.valueOf(resultSet.getString(7).toUpperCase());
                user = new User(id,firstName,lastName,fathersName,login,password,email,role);
            }
        }catch (SQLException exception){
            throw new DaoException("Exception while authorization",exception);
        }
        return user;
    }

    @Override
    public boolean registration(String data) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        ResultSet resultSet = null;
        StringTokenizer tokenizer = new StringTokenizer(data);
        String login = "";
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_USER);){
            for(int i = 1; i <= 6; i++){
                if(i == 4){
                    login = tokenizer.nextToken();
                    statement.setString(i,login);
                }else{
                    statement.setString(i,tokenizer.nextToken());
                }
            }
            int role = Integer.parseInt(tokenizer.nextToken());
            statement.setInt(7,role);
            resultSet = statement.executeQuery();
        }catch (SQLException e){
            throw new DaoException("Error while registrarion",e);
        }
        return findUserByLogin(login);
    }

    @Override
    public boolean findUserByLogin(String data) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        Connection connection = instance.getConnection();
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_USER_BY_LOGIN);
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
