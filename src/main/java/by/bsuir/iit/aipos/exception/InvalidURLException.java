package by.bsuir.iit.aipos.exception;

public class InvalidURLException extends ServiceException {

    private static final long serialVersionUID = 7926440534026166151L;

    public InvalidURLException() {
    }

    public InvalidURLException(String message) {
        super(message);
    }

    public InvalidURLException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidURLException(Throwable cause) {
        super(cause);
    }
}
