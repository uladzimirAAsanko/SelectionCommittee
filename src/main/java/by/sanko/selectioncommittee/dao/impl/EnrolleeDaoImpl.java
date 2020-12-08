package by.sanko.selectioncommittee.dao.impl;

import by.sanko.selectioncommittee.dao.EnrolleeDao;
import by.sanko.selectioncommittee.dao.pool.ConnectionPool;
import by.sanko.selectioncommittee.entity.*;
import by.sanko.selectioncommittee.exception.DaoException;
import by.sanko.selectioncommittee.exception.ServiceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnrolleeDaoImpl implements EnrolleeDao {
    private static final String ADD_ENROLLEE = "INSERT abiturient_info(users_idusers, certificate,additional_info) VALUES(?,?,?);";
    private static final  String ADD_RESULT_OF_EXAM = "INSERT result_of_exam(result, exam_id_exam, abiturient_info_users_idusers) VALUES(?,?,?);";
    private static final String GET_ALL_RESULTS = "SELECT * FROM result_of_exam WHERE abiturient_info_users_idusers = ?";
    private static final String GET_ENROLLE = "SELECT * FROM abiturient_info JOIN users u on u.idusers = abiturient_info.users_idusers WHERE users_idusers = ?;";
    private static final String FIND_RESULT_BY_EXAM = "SELECT * FROM result_of_exam WHERE abiturient_info_users_idusers = ? AND exam_id_exam = ?";
    private static final String FIND_ALL_FACULTIES = "SELECT * FROM faculty;";
    private static final String FIND_ALL_FACULTIES_WITH_THIS_EXAM = "SELECT * FROM mydb.faculty_has_exam WHERE exam_id_exam=?;";
    private static final String ADD_ABITURIENT_TO_STATEMENT = "INSERT statement_has_abiturient(statement_id_statement, abiturient_id_abiturient) VALUES (?,?);";
    private static final String DELETE_ABITURIENT_FROM_STATEMENT = "DELETE FROM statement_has_abiturient WHERE  abiturient_id_abiturient=? AND statement_id_statement=?;";
    private static final String DELETE_EXAM_FROM_ABITURIENT = "DELETE FROM result_of_exam WHERE abiturient_info_users_idusers=? AND exam_id_exam=?;";
    private static final String FIND_STATEMENT_BY_ABITURIENT_ID = "SELECT * FROM statement_has_abiturient WHERE abiturient_id_abiturient=?;";

    @Override
    public Enrollee getEnrolleeByID(int enrolleeID) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        ResultSet resultSet = null;
        Enrollee enrollee = null;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ENROLLE)){
            statement.setInt(1,enrolleeID);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                int certificate = resultSet.getInt(2);
                String addInfo = resultSet.getString(3);
                String firstName = resultSet.getString(5);
                String lastName = resultSet.getString(6);
                String fathersName = resultSet.getString(7);
                String avatar = resultSet.getString(12);
                String login = resultSet.getString(8);
                String email = resultSet.getString(10);
                UserStatus status = UserStatus.values()[resultSet.getInt(13)];
                enrollee = new Enrollee(enrolleeID,firstName,lastName,fathersName,login,email,avatar,certificate,addInfo,status);
            }
        }catch (SQLException e){
            throw new DaoException("Exception while getting enrolle by id");
        }
        return enrollee;
    }

    @Override
    public int getCertificateOfEnrollee(int enrolleeID) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        ResultSet resultSet = null;
        Enrollee enrollee = null;
        int certificate = -1;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ENROLLE)){
            statement.setInt(1,enrolleeID);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                 certificate = resultSet.getInt(2);
            }
        }catch (SQLException e){
            throw new DaoException("Exception while getting enrolle by id");
        }
        return certificate;
    }

    @Override
    public boolean registration(int idUser, int certificate, String additionalInfo) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        boolean isEnrolleeAdded = false;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_ENROLLEE)){
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
        boolean isExamAdded = false;
        try(Connection connection = instance.getConnection();
                PreparedStatement statement = connection.prepareStatement(ADD_RESULT_OF_EXAM)){
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
    public boolean removeExam(int userID, Exam exam) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        boolean isExamRemoved = false;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_EXAM_FROM_ABITURIENT)){
            statement.setInt(1,userID);
            statement.setInt(2,exam.getIndex());
            isExamRemoved = statement.executeUpdate() > 0;
        }catch (SQLException exception) {
            throw new DaoException("Exception while removing exam",exception);
        }
        return isExamRemoved;
    }

    @Override
    public HashMap<Exam,Integer> getAllExams(int userID) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        HashMap<Exam,Integer> result = new HashMap<>();
        ResultSet resultSet = null;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_RESULTS)){
            statement.setInt(1,userID);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                int resultOfExam = resultSet.getInt(1);
                int examID = resultSet.getInt(2);
                Exam exam = Exam.findExamByIndex(examID);
                result.put(exam,resultOfExam);
            }
        } catch (SQLException exception) {
            throw new DaoException("Exception while getting all faculties",exception);
        }
        return result;
    }

    @Override
    public boolean isResultAlreadyAdded(int userID, int indexOfExam) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        boolean isExamAdded = false;
        ResultSet resultSet = null;
        try (Connection connection = instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_RESULT_BY_EXAM)){
            statement.setInt(1,userID);
            statement.setInt(2,indexOfExam);
            resultSet = statement.executeQuery();
            isExamAdded = resultSet.next();
        } catch (SQLException exception) {
            throw new DaoException("Exception while checking is result added",exception);
        }
        return isExamAdded;
    }

    @Override
    public List<Faculty> getAllFaculties() throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        List<Faculty> faculties = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_FACULTIES)){
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                int facultyID = resultSet.getInt(1);
                String facultyName = resultSet.getString(2);
                String facultySite = resultSet.getString(3);
                String facultyAvatar = resultSet.getString(4);
                faculties.add(new Faculty(facultyID,facultyName,facultySite,facultyAvatar));
            }
        }catch (SQLException e){
            throw new DaoException("Exception while getting all faculties",e);
        }
        return faculties;
    }

    @Override
    public List<Faculty> getFacultiesWithExam(Exam exam) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        List<Faculty> faculties = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_FACULTIES_WITH_THIS_EXAM)){
            statement.setInt(1,exam.getIndex());
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                int facultyID = resultSet.getInt(1);
                String facultyName = resultSet.getString(2);
                String facultySite = resultSet.getString(3);
                String facultyAvatar = resultSet.getString(4);
                faculties.add(new Faculty(facultyID,facultyName,facultySite,facultyAvatar));
            }
        }catch (SQLException e){
            throw new DaoException("Exception while getting faculties by exam",e);
        }
        return faculties;
    }

    @Override
    public boolean signUpToStatement(int userID, int statementID) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        boolean isSignedUp = false;
        try (Connection connection = instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_ABITURIENT_TO_STATEMENT)){
            statement.setInt(1,statementID);
            statement.setInt(2,userID);
            isSignedUp = statement.executeUpdate() > 0;
        }catch (SQLException e){
            throw new DaoException("Exception while adding user to statement",e);
        }
        return isSignedUp;
    }

    @Override
    public boolean removeUserFromStatement(int userID, int statementID) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        boolean isRemoved = false;
        try (Connection connection = instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ABITURIENT_FROM_STATEMENT)){
            statement.setInt(1,userID);
            statement.setInt(2,statementID);
            isRemoved = statement.executeUpdate() > 0;
        }catch (SQLException e){
            throw new DaoException("Exception while removing user from statement",e);
        }
        return isRemoved;
    }

    @Override
    public int getStatementOfUserAttached(int userID) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        int statementID = -1;
        ResultSet resultSet = null;
        try (Connection connection = instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_STATEMENT_BY_ABITURIENT_ID)){
            statement.setInt(1,userID);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                statementID = resultSet.getInt(2);
            }
        }catch (SQLException e){
            throw new DaoException("Exception while getting statement by user",e);
        }

        return statementID;
    }

    @Override
    public boolean isUserRegisterToStatement(int userID) throws DaoException {
        boolean isRegister = false;
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        ResultSet resultSet = null;
        try (Connection connection = instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_STATEMENT_BY_ABITURIENT_ID)){
            statement.setInt(1,userID);
            resultSet = statement.executeQuery();
            isRegister = resultSet.next();
        }catch (SQLException e){
            throw new DaoException("Exception while getting statement by user",e);
        }
        return isRegister;
    }
}
