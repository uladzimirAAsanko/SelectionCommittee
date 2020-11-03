package by.sanko.selectioncommittee.entity;

import java.io.Serializable;

public class Administrator extends User implements Serializable {
    public Administrator(int userID, String firstName, String lastName, String fathersName, String login, String email) {
        super(userID, firstName, lastName, fathersName, login, email, UsersRole.ADMINISTRATOR);
    }
}
