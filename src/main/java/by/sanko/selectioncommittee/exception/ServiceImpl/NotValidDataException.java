package by.sanko.selectioncommittee.exception.ServiceImpl;

import by.sanko.selectioncommittee.exception.ServiceException;

public class NotValidDataException extends ServiceException {
    public NotValidDataException() {
        super();
    }

    public NotValidDataException(String message) {
        super(message);
    }

    public NotValidDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidDataException(Throwable cause) {
        super(cause);
    }
}
