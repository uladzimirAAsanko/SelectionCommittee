package by.sanko.selectioncommittee.entity;

import java.io.Serializable;

public class AuthorizationData implements Serializable {
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

    public void setPassword(String password) {
        this.password = password;
    }
}
