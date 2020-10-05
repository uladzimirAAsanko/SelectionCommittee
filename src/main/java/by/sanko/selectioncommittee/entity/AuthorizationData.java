package by.sanko.selectioncommittee.entity;

public class AuthorizationData {
    String login;
    String password;

    public AuthorizationData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }
}
