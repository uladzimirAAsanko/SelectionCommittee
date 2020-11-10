package by.sanko.selectioncommittee.dao.impl;

import by.sanko.selectioncommittee.dao.EnrolleeDao;
import by.sanko.selectioncommittee.dao.pool.ConnectionPool;
import by.sanko.selectioncommittee.entity.Exam;
import by.sanko.selectioncommittee.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrolleeDaoImpl implements EnrolleeDao {
    private static final String ADD_ENROLLEE = "INSERT abiturient_info(users_idusers, certificate,additional_info) VALUES(?,?,?);";
    private static final  String ADD_RESULT_OF_EXAM = "INSERT result_of_exam(result, exam_id_exam, abiturient_info_users_idusers) VALUES(?,?,?);";
    private static final String GET_ALL_RESULTS = "SELECT * FROM result_of_exam WHERE abiturient_info_users_idusers = ?";
    private static final String FIND_RESULT_BY_EXAM = "SELECT * FROM result_of_exam WHERE abiturient_info_users_idusers = ? AND exam_id_exam = ?";

    @Override
    public boolean registration(int idUser, int certificate, String additionalInfo) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        Connection connection = instance.getConnection();
        boolean isEnrolleeAdded = false;
        try(PreparedStatement statement = connection.prepareStatement(ADD_ENROLLEE)){
            statement.setInt(1,idUser);
            statement.setInt(2,certificate);
            statement.setString(3,additionalInfo);
            isEnrolleeAdded = statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException("Exception while adding enrollee",exception);
        }
        return isEnrolleeAdded;
    }

    @Override
    public boolean addExam(int userID,Exam exam, int result) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        Connection connection = instance.getConnection();
        boolean isExamAdded = false;
        if(isResultAlreadyAdded(userID,exam.getIndex())){
            return false;
        }
        try(PreparedStatement statement = connection.prepareStatement(ADD_RESULT_OF_EXAM)){
            statement.setInt(1,result);
            statement.setInt(2,exam.getIndex());
            statement.setInt(3,userID);
            isExamAdded = statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException("Exception while adding exam",exception);
        }
        return isExamAdded;
    }

    @Override
    public List<String> getAllExams(int userID) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        Connection connection = instance.getConnection();
        List<String> result = new ArrayList<>();
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(GET_ALL_RESULTS);
            statement.setInt(1,userID);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                int resultOfExam = resultSet.getInt(1);
                int examID = resultSet.getInt(2);
                StringBuilder res = new StringBuilder();
                res.append(Exam.findExamByIndex(examID)).append("  ").append(resultOfExam);
                result.add(res.toString());
            }
        } catch (SQLException exception) {
            throw new DaoException("Exception while getting all faculties",exception);
        }
        return result;
    }

    @Override
    public boolean isResultAlreadyAdded(int userID, int indexOfExam) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        Connection connection = instance.getConnection();
        PreparedStatement statement = null;
        boolean isExamAdded = false;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(FIND_RESULT_BY_EXAM);
            statement.setInt(1,userID);
            statement.setInt(2,indexOfExam);
            resultSet = statement.executeQuery();
            isExamAdded = resultSet.next();
        } catch (SQLException exception) {
            throw new DaoException("Exception while checking is result added",exception);
        }
        return isExamAdded;
    }
}
