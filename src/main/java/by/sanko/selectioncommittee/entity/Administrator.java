package by.sanko.selectioncommittee.entity;

public class Administrator extends User{
    public Administrator(int userID, String firstName, String lastName, String fathersName, String login, String password, String email) {
        super(userID, firstName, lastName, fathersName, login, password, email, UsersRole.ADMINISTRATOR);
    }
}
