package by.sanko.selectioncommittee.exception.ServiceImpl;

import by.sanko.selectioncommittee.exception.ServiceException;

public class ImpracticableActionException  extends ServiceException {
    public ImpracticableActionException() {
        super();
    }

    public ImpracticableActionException(String message) {
        super(message);
    }

    public ImpracticableActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImpracticableActionException(Throwable cause) {
        super(cause);
    }
}
