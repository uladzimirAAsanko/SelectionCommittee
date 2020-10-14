package by.sanko.selectioncommittee.entity;


import java.util.Objects;
//TODO MakeAllObjectsSerializable
public class User {
    protected int userID;
    protected String firstName;
    protected String lastName;
    protected String fathersName;
    protected String login;
    protected String password;
    protected String email;
    final UsersRole role;

    public User(int userID, String firstName, String lastName, String fathersName, String login, String password, String email, UsersRole role) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fathersName = fathersName;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public UsersRole getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userID == user.userID &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                fathersName.equals(user.fathersName) &&
                login.equals(user.login) &&
                password.equals(user.password) &&
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
        result = 31 * result + password.hashCode();
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
