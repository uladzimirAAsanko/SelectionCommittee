package by.sanko.selectioncommittee.entity;

import java.io.Serializable;

public class RegistrationData implements Serializable {
    private String firstName;
    private String lastName;
    private String fathersName;
    private String login;
    private String password;
    private String email;
    private int role;

    public RegistrationData(){}

    public RegistrationData(String firstName, String lastName, String fathersName,
                            String login, String password, String email, int role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fathersName = fathersName;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public void setPassword(String password){this.password = password;}
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

    public int getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationData that = (RegistrationData) o;
        return getRole() == that.getRole() &&
                getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                getFathersName().equals(that.getFathersName()) &&
                getLogin().equals(that.getLogin()) &&
                getPassword().equals(that.getPassword()) &&
                getEmail().equals(that.getEmail());
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 31 + firstName.hashCode();
        result = result * 31 + lastName.hashCode();
        result = result * 31 + fathersName.hashCode();
        result = result * 31 + login.hashCode();
        result = result * 31 + password.hashCode();
        result = result * 31 + role;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RegistrationData");
        builder.append("firstName='").append(firstName).append('\'');
        builder.append("lastName='").append(lastName).append('\'');
        builder.append("fathersName='").append(fathersName).append('\'');
        builder.append("login='").append(login).append('\'');
        builder.append("password='").append(password).append('\'');
        builder.append("email='").append(email).append('\'');
        builder.append("role='").append(role).append('}');
        return builder.toString();
    }
}
