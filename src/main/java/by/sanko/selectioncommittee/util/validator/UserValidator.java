package by.sanko.selectioncommittee.util.validator;

public class UserValidator {
    private static UserValidator instance = new UserValidator();

    private UserValidator() {

    }

    public static UserValidator getInstance() {
        return instance;
    }

    public boolean validateLogin(String login){
        if(login == null){
            return false;
        }
        if(login.length() < 4){
            return false;
        }
        return true;
    }

    public boolean validatePassword(String password){
        if(password == null){
            return false;
        }
        if(password.length() < 8){
            return false;
        }
        return true;
    }
}
