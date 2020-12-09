package by.sanko.selectioncommittee.dao.impl;

import by.sanko.selectioncommittee.dao.FacultyDao;
import by.sanko.selectioncommittee.dao.pool.ConnectionPool;
import by.sanko.selectioncommittee.entity.Enrollee;
import by.sanko.selectioncommittee.entity.Exam;
import by.sanko.selectioncommittee.entity.Faculty;
import by.sanko.selectioncommittee.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FacultyDaoImpl implements FacultyDao {
    private static final String ALL_FACULTIES = "SELECT * FROM faculty;";
    private static final String SELECT_CODE_BY_CODE = "SELECT * FROM admin_codes WHERE code=?;";
    private static final String SELECT_FACULTY_BY_ID = "SELECT * FROM faculty WHERE id_faculty = ?;";
    private static final String SELECT_FACULTY_BY_NAME = "SELECT * FROM faculty WHERE faculty_name = ?;";
    private static final String SELECT_FACULTY_BY_USER_ID = "SELECT * FROM admin_info WHERE users_idusers = ?;";
    private static final String ADD_ADMIN_CODE = "INSERT admin_codes(code, faculty_info_faculty_id_faculty) VALUES (?,?)";
    private static final String ADD_FACULTY = "INSERT faculty( faculty_name, site) VALUES (?,?);";
    private static final String ADD_ADMIN_INFO = "INSERT admin_info(users_idusers,faculty_id) VALUES (?,?);";
    private static final String ADD_EXAM_TO_FACULTY = "INSERT faculty_has_exam(faculty_id_faculty, exam_id_exam, minimal_score) VALUES (?,?,?);";
    private static final String ADD_STATEMENT_TO_FACULTY = "INSERT mydb.statement(number_of_max_students, created_at, expired_at, faculty_id_faculty, admin_info_users_idusers) VALUES (?,?,?,?,?);";
    private static final String DELETE_ADMIN_CODE = "DELETE  FROM admin_codes WHERE mydb.admin_codes.code = ?;";
    private static final String GET_EXAMS_OF_FACULTY = "SELECT * FROM faculty_has_exam WHERE faculty_id_faculty = ?;";
    private static final String GET_USERS_FROM_STATEMENT = "SELECT * FROM statement_has_abiturient WHERE statement_id_statement =?;";
    private static final String GET_STATEMENT_OF_FACULTY = "SELECT * FROM mydb.statement WHERE faculty_id_faculty = ?;";
    private static final String GET_NEEDED_EXAM_OF_FACULTY = "SELECT * FROM faculty_has_exam WHERE faculty_id_faculty = ? AND exam_id_exam=?;";
    private static final String DELETE_EXAM_IN_FACULTY = "DELETE FROM faculty_has_exam WHERE exam_id_exam =? AND  faculty_id_faculty =?;";
    private static final String DELETE_USER_IN_STATEMENT = "DELETE FROM statement_has_abiturient WHERE statement_id_statement =? AND  abiturient_id_abiturient =?;";
    private static final String DELETE_STATEMENT_IN_FACULTY = "DELETE FROM mydb.statement WHERE admin_info_users_idusers =? AND  faculty_id_faculty =?;";
    private static final String UPDATE_STATMENT_CLOSE_DATE =  "UPDATE mydb.statement SET expired_at=?, number_of_max_students=? WHERE faculty_id_faculty = ? AND admin_info_users_idusers=?;";

    @Override
    public List getAllSitesOfFaculties() throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        ResultSet resultSet = null;
        ArrayList<Faculty> result = new ArrayList<>();
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(ALL_FACULTIES)){
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString(2);
                String site = resultSet.getString(3);
                String avatar = resultSet.getString(4);
                int id = resultSet.getInt(1);
                Faculty faculty = new Faculty(id,name,site,avatar);
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
        int facultyID = -1;
        ResultSet resultSet = null;
        boolean isRegister = false;
        PreparedStatement statement = null;
        try(Connection connection = instance.getConnection()) {
            statement = connection.prepareStatement(SELECT_CODE_BY_CODE);
            statement.setString(1,adminCode);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                facultyID = resultSet.getInt(2);
                statement = connection.prepareStatement(ADD_ADMIN_INFO);
                statement.setInt(1,userID);
                statement.setInt(2,facultyID);
                isRegister =  statement.executeQuery().next();
            }
        } catch (SQLException exception) {
            throw new DaoException("Exception while finding admin code",exception);
        }finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException ignored) {
            }
        }
        return isRegister;
    }

    @Override
    public Faculty getFacultyById(int facultyID) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        Faculty faculty = null;
        ResultSet resultSet = null;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_FACULTY_BY_ID)) {
            statement.setInt(1,facultyID);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                String name = resultSet.getString(2);
                String site = resultSet.getString(3);
                String avatar = resultSet.getString(4);
                faculty = new Faculty(facultyID,name,site,avatar);
            }
        } catch (SQLException exception) {
            throw new DaoException("Exception while finding faculty by id",exception);
        }
        return faculty;
    }

    @Override
    public boolean addAdminCode(int facultyID, String code) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        boolean isAdded = false;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_ADMIN_CODE)) {
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
        boolean isDeleted = false;
        try (Connection connection = instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ADMIN_CODE)){
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
        int facultyID = -1;
        ResultSet resultSet = null;

        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_FACULTY_BY_USER_ID)) {
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

    @Override
    public boolean addExamToFaculty(Exam exam, int facultyID, int minimalScore) throws DaoException {
        boolean isAdded = false;
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_EXAM_TO_FACULTY)){
            statement.setInt(1,facultyID);
            statement.setInt(2,exam.getIndex());
            statement.setInt(3,minimalScore);
            isAdded = statement.executeUpdate() > 0;
        }catch (SQLException e){
            throw new DaoException("Exception while adding exam to faculty",e);
        }
        return isAdded;
    }

    @Override
    public int getNumberOfExamsOfFaculty(int facultyID) throws DaoException {
        int numberOfExams = 0;
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        ResultSet resultSet = null;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_EXAMS_OF_FACULTY)){
            statement.setInt(1,facultyID);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                numberOfExams++;
            }
        }catch (SQLException e){
            throw new DaoException("Exception while getting number of exams to faculty",e);
        }
        return numberOfExams;
    }

    @Override
    public boolean isFacultyHasExam(Exam exam, int facultyID) throws DaoException {
        boolean isHas = false;
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_NEEDED_EXAM_OF_FACULTY)){
            statement.setInt(1,facultyID);
            statement.setInt(2,exam.getIndex());
            isHas = statement.executeQuery().next();
        }catch (SQLException e){
            throw new DaoException("Exception while checking exams in faculty",e);
        }
        return isHas;
    }

    @Override
    public HashMap<Exam, Integer> getAllExams(int facultyID) throws DaoException {
        HashMap<Exam, Integer> map = new HashMap<>();
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        ResultSet resultSet = null;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_EXAMS_OF_FACULTY)){
            statement.setInt(1,facultyID);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                int minimalScore = resultSet.getInt(4);
                Exam exam = Exam.findExamByIndex(resultSet.getInt(3));
                map.put(exam,minimalScore);
            }
        }catch (SQLException e){
            throw new DaoException("Exception while getting all exams in faculty",e);
        }
        return map;
    }

    @Override
    public boolean deleteExam(Exam exam, int facultyID) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        boolean isDeleted = false;
        try(Connection connection = instance.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_EXAM_IN_FACULTY)){
            statement.setInt(1,exam.getIndex());
            statement.setInt(2,facultyID);
            isDeleted = statement.executeUpdate() > 0;
        }catch (SQLException e){
            throw new DaoException("Exception while deleting exams in faculty",e);
        }
        return isDeleted;
    }

    @Override
    public boolean addStatementOfFaculty(int facultyID, int adminID, Date createdAt, Date expiredAt, int numberOfMaxStudents) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        boolean isAdded = false;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_STATEMENT_TO_FACULTY)){
            statement.setInt(1,numberOfMaxStudents);
            statement.setDate(2,createdAt);
            statement.setDate(3,expiredAt);
            statement.setInt(4,facultyID);
            statement.setInt(5,adminID);
            isAdded = statement.executeUpdate() > 0;
        }catch (SQLException e){
            throw new DaoException("Exception while adding statement to faculty",e);
        }
        return isAdded;
    }

    @Override
    public boolean isFacultyHasStatement(int facultyID) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        boolean isHas = false;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_STATEMENT_OF_FACULTY)){
            statement.setInt(1,facultyID);
            isHas = statement.executeQuery().next();
        }catch (SQLException e){
            throw new DaoException("Exception while checking statement of faculty",e);
        }
        return isHas;
    }

    @Override
    public boolean updateStatement(int numberOfMaxStudents, Date closeAt, int facultyID, int adminID) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        boolean isClosed = false;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_STATMENT_CLOSE_DATE)) {
            statement.setDate(1,closeAt);
            statement.setInt(2,numberOfMaxStudents);
            statement.setInt(3,facultyID);
            statement.setInt(4,adminID);
            isClosed = statement.executeUpdate() > 0;;
        }catch (SQLException e){
            throw new DaoException("Exception while updating statement of faculty",e);
        }
        return isClosed;
    }

    @Override
    public boolean deleteStatement(int facultyID, int adminID) throws DaoException {
        boolean isDeleted = false;
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_STATEMENT_IN_FACULTY)) {
            statement.setInt(1,adminID);
            statement.setInt(2,facultyID);
            isDeleted = statement.executeUpdate() > 0;
        }catch (SQLException e){
            throw new DaoException("Exception while updating statement of faculty",e);
        }
        return isDeleted;
    }

    @Override
    public int getNumberOfMaxStudentsInStatement(int facultyID) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        int numberOfStudents = 0;
        ResultSet resultSet = null;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_STATEMENT_OF_FACULTY)){
            statement.setInt(1,facultyID);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                numberOfStudents = resultSet.getInt(2);
            }
        }catch (SQLException e){
            throw new DaoException("Exception while getting statement of faculty",e);
        }
        return numberOfStudents;
    }

    @Override
    public Date getExpiredDateOfStatement(int facultyID) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        Date expiredDate = null;
        ResultSet resultSet = null;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_STATEMENT_OF_FACULTY)){
            statement.setInt(1,facultyID);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                expiredDate = resultSet.getDate(4);
            }
        }catch (SQLException e){
            throw new DaoException("Exception while getting statement of faculty",e);
        }
        return expiredDate;
    }

    @Override
    public int getStatementIDByFacultyID(int facultyID) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        int statementID = -1;
        ResultSet resultSet = null;
        try(Connection connection = instance.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_STATEMENT_OF_FACULTY)){
            statement.setInt(1,facultyID);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                statementID = resultSet.getInt(1);
            }
        }catch (SQLException e){
            throw new DaoException("Exception while getting statementID of faculty",e);
        }
        return statementID;
    }

    @Override
    public boolean deleteUserFromStatement(int userId, int statementID) throws DaoException {
        boolean isDeleted = false;
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_USER_IN_STATEMENT)){
            statement.setInt(1,statementID);
            statement.setInt(2,userId);
            isDeleted = statement.executeUpdate() > 0;
        }catch (SQLException e){
            throw new DaoException("Exception while deleting abiturient from statement",e);
        }
        return isDeleted;
    }

    @Override
    public List<Integer> getAllUsersIDFromStatement(int statementID) throws DaoException {
        List<Integer> allUsers = new ArrayList<>();
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        ResultSet resultSet = null;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_USERS_FROM_STATEMENT)){
            statement.setInt(1,statementID);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                allUsers.add(resultSet.getInt(3));
            }
        }catch (SQLException e){
            throw new DaoException("Exception while getting abiturients IDs from statement",e);
        }
        return allUsers;
    }

    @Override
    public int getMinimalScoreOfThisExamByFaculty(Exam exam, int facultyID) throws DaoException {
        int minimalScore = -1;
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        ResultSet resultSet = null;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_NEEDED_EXAM_OF_FACULTY)){
            statement.setInt(1,facultyID);
            statement.setInt(2,exam.getIndex());
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                minimalScore = resultSet.getInt(4);
            }
        }catch (SQLException e){
            throw new DaoException("Exception while getting minimal score",e);
        }
        return minimalScore;
    }

    @Override
    public boolean addFaculty(String facultyName, String facultySite) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        boolean isAdded = false;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_FACULTY)){
            statement.setString(1,facultyName);
            statement.setString(2,facultySite);
            isAdded = statement.executeUpdate() > 0;
        }catch (SQLException e){
            throw new DaoException("Exception while adding new faculty",e);
        }
        return isAdded;
    }

    @Override
    public Faculty getFacultyByName(String facultyName) throws DaoException {
        ConnectionPool instance = ConnectionPool.getINSTANCE();
        Faculty faculty = null;
        ResultSet resultSet = null;
        try(Connection connection = instance.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_FACULTY_BY_NAME)) {
            statement.setString(1,facultyName);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                Integer facultyID = resultSet.getInt(1);
                String site = resultSet.getString(3);
                String avatar = resultSet.getString(4);
                faculty = new Faculty(facultyID,facultyName,site,avatar);
            }
        } catch (SQLException exception) {
            throw new DaoException("Exception while finding faculty by id",exception);
        }
        return faculty;
    }
}
