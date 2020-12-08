package by.sanko.selectioncommittee.exception.DaoImpl;

import by.sanko.selectioncommittee.exception.DaoException;

public class LoginIsBusyException extends DaoException {
    public LoginIsBusyException() {
        super();
    }

    public LoginIsBusyException(String message) {
        super(message);
    }

    public LoginIsBusyException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginIsBusyException(Throwable cause) {
        super(cause);
    }
}
