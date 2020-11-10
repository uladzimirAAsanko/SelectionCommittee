package by.sanko.selectioncommittee.dao.impl;

import by.sanko.selectioncommittee.dao.FacultyDao;
import by.sanko.selectioncommittee.dao.pool.ConnectionPool;
import by.sanko.selectioncommittee.entity.Faculty;
import by.sanko.selectioncommittee.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacultyDaoImpl implements FacultyDao {
    private static final String ALL_FACULTIES = "SELECT * FROM faculty;";
    private static final String SELECT_CODE_BY_CODE = "SELECT * FROM admin_codes WHERE code=?;";
    private static final String SELECT_FACULTY_BY_ID = "SELECT * FROM faculty WHERE id_faculty = ?;";
    private static final String SELECT_FACULTY_BY_USER_ID = "SELECT * FROM admin_info WHERE users_idusers = ?;";
    private static final String ADD_ADMIN_CODE = "INSERT admin_codes(code, faculty_info_faculty_id_faculty) VALUES (?,?)";
    private static final String ADD_ADMIN_INFO = "INSERT admin_info(users_idusers,faculty_id) VALUES (?,?);";
    private static final String DELETE_ADMIN_CODE = "DELETE  FROM admin_codes WHERE mydb.admin_codes.code = ?;";

    @Override
    public List getAllSitesOfFaculties() throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        Connection connection = instance.getConnection();
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        ArrayList<Faculty> result = new ArrayList<>();
        try{
            statement = connection.prepareStatement(ALL_FACULTIES);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString(2);
                String site = resultSet.getString(3);
                int id = resultSet.getInt(1);
                Faculty faculty = new Faculty(id,name,site);
                result.add(faculty);
            }
        } catch (SQLException exception) {
            throw new DaoException("Exception while getting all faculties",exception);
        }
        return result;
    }

    @Override
    public boolean registerAdmin(int userID,String adminCode) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        Connection connection = instance.getConnection();
        int facultyID = -1;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean isRegister = false;
        try {
            statement = connection.prepareStatement(SELECT_CODE_BY_CODE);
            statement.setString(1,adminCode);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                facultyID = resultSet.getInt(2);
                statement = connection.prepareStatement(ADD_ADMIN_INFO);
                statement.setInt(1,userID);
                statement.setInt(2,facultyID);
                isRegister =  statement.executeUpdate() > 0;
            }
        } catch (SQLException exception) {
            throw new DaoException("Exception while finding admin code",exception);
        }
        return isRegister;
    }

    @Override
    public Faculty getFacultyById(int facultyID) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        Connection connection = instance.getConnection();
        Faculty faculty = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(SELECT_FACULTY_BY_ID);
            statement.setInt(1,facultyID);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                String name = resultSet.getString(2);
                String site = resultSet.getString(3);
                faculty = new Faculty(facultyID,name,site);
            }
        } catch (SQLException exception) {
            throw new DaoException("Exception while finding faculty by id",exception);
        }
        return faculty;
    }

    @Override
    public boolean addAdminCode(int facultyID, String code) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        Connection connection = instance.getConnection();
        PreparedStatement statement = null;
        boolean isAdded = false;
        try {
            statement = connection.prepareStatement(ADD_ADMIN_CODE);
            statement.setInt(2,facultyID);
            statement.setString(1,code);
            isAdded =  statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException("Exception while adding admin code",exception);
        }
        return isAdded;
    }

    @Override
    public boolean deleteAdminCode(String code) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        Connection connection = instance.getConnection();
        PreparedStatement statement = null;
        boolean isDeleted = false;

        try {
            statement = connection.prepareStatement(DELETE_ADMIN_CODE);
            statement.setString(1,code);
            isDeleted = statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException("Exception while deleting admin code",exception);
        }
        return isDeleted;
    }

    @Override
    public int getFacultyIdByUserID(int userID) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        Connection connection = instance.getConnection();
        PreparedStatement statement = null;
        int facultyID = -1;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(SELECT_FACULTY_BY_USER_ID);
            statement.setInt(1,userID);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                facultyID = resultSet.getInt(2);
            }
        } catch (SQLException exception) {
            throw new DaoException("Exception while getting faculty id",exception);
        }
        return facultyID;
    }
}
