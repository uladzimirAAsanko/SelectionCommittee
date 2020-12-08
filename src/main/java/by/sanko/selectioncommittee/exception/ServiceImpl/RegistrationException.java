package by.sanko.selectioncommittee.exception.ServiceImpl;

import by.sanko.selectioncommittee.exception.ServiceException;

public class RegistrationException extends ServiceException {
    public RegistrationException() {
        super();
    }

    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistrationException(Throwable cause) {
        super(cause);
    }
}
