package by.sanko.selectioncommittee.entity;

import java.io.Serializable;

public class Administrator extends User implements Serializable {
    //TODO make this class looks good
    private int facultyID;

    public int getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(int facultyID) {
        this.facultyID = facultyID;
    }

    public Administrator(int userID, String firstName, String lastName, String fathersName, String login, String email) {
        super(userID, firstName, lastName, fathersName, login, email, UsersRole.ADMINISTRATOR);
    }
}
