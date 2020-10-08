package by.sanko.selectioncommittee.service.validator;

public class UserValidator {
    private static UserValidator instance;

    private UserValidator() {

    }

    public static UserValidator getInstance() {
        if (instance == null) {
            instance = new UserValidator();
        }
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
