package by.sanko.selectioncommittee.exception.DaoImpl;

import by.sanko.selectioncommittee.exception.DaoException;

public class UserIsNotActiveException extends DaoException {
    public UserIsNotActiveException() {
        super();
    }

    public UserIsNotActiveException(String message) {
        super(message);
    }

    public UserIsNotActiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserIsNotActiveException(Throwable cause) {
        super(cause);
    }
}
