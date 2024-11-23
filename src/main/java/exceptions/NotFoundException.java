package exceptions;

public class NotFoundException extends ApiException{
    public NotFoundException(String message) {
        super(Error.NOT_FOUND, message, 404);
    }

    @Override
    public void logException() {
        super.logException();
    }
}
