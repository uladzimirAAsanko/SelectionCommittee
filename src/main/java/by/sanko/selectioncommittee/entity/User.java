package by.sanko.selectioncommittee.entity;


import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    protected int userID;
    protected String firstName;
    protected String lastName;
    protected String fathersName;
    protected String login;
    protected String email;
    protected String avatarDir;
    protected UserStatus status;

    final UsersRole role;

    public User(int userID, String firstName, String lastName, String fathersName, String login, String email, UsersRole role,String avatarDir,UserStatus status) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fathersName = fathersName;
        this.login = login;
        this.email = email;
        this.role = role;
        this.avatarDir = avatarDir;
        this.status = status;
    }

    public void setAvatarDir(String avatarDir) {
        this.avatarDir = avatarDir;
    }

    public UserStatus getStatus() {
        return status;
    }

    public int getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFathersName() {
        return fathersName;
    }

    public String getLogin() {
        return login;
    }


    public String getEmail() {
        return email;
    }

    public UsersRole getRole() {
        return role;
    }

    public String getAvatarDir(){return avatarDir;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return userID == user.userID &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                fathersName.equals(user.fathersName) &&
                login.equals(user.login) &&
                Objects.equals(email, user.email) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + userID;
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + fathersName.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + email.hashCode();

        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("User{");
        stringBuilder.append("userID=").append(userID);
        stringBuilder.append(", firstName='").append(firstName).append('\'');
        stringBuilder.append(", lastName='").append(lastName).append('\'');
        stringBuilder.append(", fathersName='").append(fathersName).append('\'');
        stringBuilder.append(", login'").append(login).append('\'');
        stringBuilder.append(", role'").append(role).append('\'').append("}");;
        return stringBuilder.toString();
    }
}
