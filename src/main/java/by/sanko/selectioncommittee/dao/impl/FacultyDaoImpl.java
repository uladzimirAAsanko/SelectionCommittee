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
import java.util.Map;

public class FacultyDaoImpl implements FacultyDao {
    private static final String ALL_FACULTIES = "SELECT * FROM faculty;";
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
}
